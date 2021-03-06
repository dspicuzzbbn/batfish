package org.batfish.smt;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Solver;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Instruments the network model with additional information that is useful for checking other
 * properties. This information can be things like reachability, path length, load, etc.
 *
 * @author Ryan Beckett
 */
public class PropertyAdder {

  private EncoderSlice _encoderSlice;

  public PropertyAdder(EncoderSlice encoderSlice) {
    _encoderSlice = encoderSlice;
  }

  /*
   * Ensure that all expressions in a list are equal
   */
  public static BoolExpr allEqual(Context ctx, List<Expr> exprs) {
    BoolExpr acc = ctx.mkBool(true);
    if (exprs.size() > 1) {
      for (int i = 0; i < exprs.size() - 1; i++) {
        Expr x = exprs.get(i);
        Expr y = exprs.get(i + 1);
        acc = ctx.mkAnd(acc, ctx.mkEq(x, y));
      }
    }
    return acc;
  }

  /*
   * Initialize reachability and id variables an add the constraints
   * that all ids are at least 0 or higher, and reachable means non-zero.
   */
  private void initializeReachabilityVars(
      EncoderSlice slice,
      Context ctx,
      Solver solver,
      Map<String, BoolExpr> reachableVars,
      Map<String, ArithExpr> idVars) {
    String sliceName = slice.getSliceName();
    ArithExpr zero = ctx.mkInt(0);
    _encoderSlice
        .getGraph()
        .getConfigurations()
        .forEach(
            (r, conf) -> {
              int id = _encoderSlice.getEncoder().getId();
              String s1 = id + "_" + sliceName + "_reachable-id_" + r;
              String s2 = id + "_" + sliceName + "_reachable_" + r;
              ArithExpr idVar = ctx.mkIntConst(s1);
              BoolExpr var = ctx.mkBoolConst(s2);

              idVars.put(r, idVar);
              reachableVars.put(r, var);
              _encoderSlice.getAllVariables().put(idVar.toString(), idVar);
              _encoderSlice.getAllVariables().put(var.toString(), var);

              solver.add(ctx.mkEq(var, ctx.mkGt(idVar, zero)));
              solver.add(ctx.mkGe(idVar, zero));
            });
  }

  /*
   * Generates constraints for reachability through some neighbor.
   * If a router forwards to some neighbor with id label > 0, then the id of this router is
   * greater than that of all next hops. This prevents considering loops and also means that
   * we interpret the router as reachable. If there is no such neighbor, then this router is
   * not reachable and we set the id to 0.
   */
  private BoolExpr recursiveReachability(
      Context ctx,
      EncoderSlice slice,
      List<GraphEdge> edges,
      Map<String, ArithExpr> idVars,
      String router,
      ArithExpr id) {
    ArithExpr zero = ctx.mkInt(0);
    BoolExpr hasRecursiveRoute = ctx.mkFalse();
    BoolExpr largerIds = ctx.mkTrue();
    for (GraphEdge edge : edges) {
      if (!edge.isAbstract()) {
        BoolExpr fwd = _encoderSlice.getForwardsAcross().get(router, edge);
        if (edge.getPeer() != null) {
          ArithExpr peerId = idVars.get(edge.getPeer());
          BoolExpr peerReachable = ctx.mkGt(peerId, zero);
          BoolExpr sendToReachable = ctx.mkAnd(fwd, peerReachable);
          hasRecursiveRoute = ctx.mkOr(hasRecursiveRoute, sendToReachable);
          BoolExpr increasingId = ctx.mkImplies(sendToReachable, ctx.mkGt(id, peerId));
          largerIds = ctx.mkAnd(largerIds, increasingId);
        }
      }
    }
    return slice.mkIf(hasRecursiveRoute, largerIds, ctx.mkEq(id, zero));
  }

  /*
   * Add reachability information to the network for a destination edge.
   * Each router will have a boolean variable determining if it can reach
   * the destination. A router is reachable if it has some neighbor that
   * is also reachable.
   */
  public Map<String, BoolExpr> instrumentReachability(Set<GraphEdge> ges) {
    Context ctx = _encoderSlice.getCtx();
    Solver solver = _encoderSlice.getSolver();
    EncoderSlice slice = _encoderSlice;
    Map<String, BoolExpr> reachableVars = new HashMap<>();
    Map<String, ArithExpr> idVars = new HashMap<>();

    initializeReachabilityVars(slice, ctx, solver, reachableVars, idVars);
    _encoderSlice
        .getGraph()
        .getEdgeMap()
        .forEach(
            (router, edges) -> {
              ArithExpr id = idVars.get(router);
              // Add the base case, reachable if we forward to a directly connected interface
              BoolExpr hasDirectRoute = ctx.mkFalse();
              for (GraphEdge ge : edges) {
                if (!ge.isAbstract() && ges.contains(ge)) {
                  BoolExpr fwdIface = _encoderSlice.getForwardsAcross().get(ge.getRouter(), ge);
                  assert (fwdIface != null);
                  hasDirectRoute = ctx.mkOr(hasDirectRoute, fwdIface);
                }
              }
              // Add the recursive case, where it is reachable through a neighbor
              BoolExpr recursive = recursiveReachability(ctx, slice, edges, idVars, router, id);
              BoolExpr cond = slice.mkIf(hasDirectRoute, ctx.mkEq(id, ctx.mkInt(1)), recursive);
              solver.add(cond);
            });

    return reachableVars;
  }

  /*
   * Also instruments reachability, but to a destination router
   * rather than a destination port.
   */
  public Map<String, BoolExpr> instrumentReachability(String router) {
    Context ctx = _encoderSlice.getCtx();
    Solver solver = _encoderSlice.getSolver();
    Map<String, BoolExpr> reachableVars = new HashMap<>();
    Map<String, ArithExpr> idVars = new HashMap<>();

    initializeReachabilityVars(_encoderSlice, ctx, solver, reachableVars, idVars);
    ArithExpr baseId = idVars.get(router);
    _encoderSlice.add(ctx.mkEq(baseId, ctx.mkInt(1)));
    _encoderSlice
        .getGraph()
        .getEdgeMap()
        .forEach(
            (r, edges) -> {
              if (!r.equals(router)) {
                ArithExpr id = idVars.get(r);
                BoolExpr cond = recursiveReachability(ctx, _encoderSlice, edges, idVars, r, id);
                solver.add(cond);
              }
            });

    return reachableVars;
  }

  /*
   * Instruments the network with path length information to a
   * destination port corresponding to a graph edge ge.
   * A router has a path of length n if some neighbor has a path
   * with length n-1.
   */
  public Map<String, ArithExpr> instrumentPathLength(Set<GraphEdge> ges) {
    Context ctx = _encoderSlice.getCtx();
    Solver solver = _encoderSlice.getSolver();
    String sliceName = _encoderSlice.getSliceName();

    // Initialize path length variables
    Map<String, ArithExpr> lenVars = new HashMap<>();
    _encoderSlice
        .getGraph()
        .getConfigurations()
        .forEach(
            (router, conf) -> {
              String name =
                  _encoderSlice.getEncoder().getId() + "_" + sliceName + "_path-length_" + router;
              ArithExpr var = ctx.mkIntConst(name);
              lenVars.put(router, var);
              _encoderSlice.getAllVariables().put(var.toString(), var);
            });

    ArithExpr zero = ctx.mkInt(0);
    ArithExpr one = ctx.mkInt(1);
    ArithExpr minusOne = ctx.mkInt(-1);

    // Lower bound for all lengths
    lenVars.forEach((name, var) -> solver.add(ctx.mkGe(var, minusOne)));

    // If no peer has a path, then I don't have a path
    // Otherwise I choose 1 + somePeer value to capture all possible lengths
    _encoderSlice
        .getGraph()
        .getEdgeMap()
        .forEach(
            (router, edges) -> {
              ArithExpr length = lenVars.get(router);

              // If there is a direct route, then we have length 0
              BoolExpr acc = ctx.mkFalse();
              for (GraphEdge ge : edges) {
                if (!ge.isAbstract() && ges.contains(ge)) {
                  BoolExpr fwdIface = _encoderSlice.getForwardsAcross().get(ge.getRouter(), ge);
                  assert (fwdIface != null);
                  acc = ctx.mkOr(acc, fwdIface);
                }
              }

              // Otherwise, we find length recursively
              BoolExpr accNone = ctx.mkTrue();
              BoolExpr accSome = ctx.mkFalse();
              for (GraphEdge edge : edges) {
                if (!edge.isAbstract()) {
                  if (edge.getPeer() != null) {
                    BoolExpr dataFwd = _encoderSlice.getForwardsAcross().get(router, edge);
                    assert (dataFwd != null);
                    ArithExpr peerLen = lenVars.get(edge.getPeer());
                    accNone =
                        ctx.mkAnd(accNone, ctx.mkOr(ctx.mkLt(peerLen, zero), ctx.mkNot(dataFwd)));
                    ArithExpr newVal = ctx.mkAdd(peerLen, one);
                    BoolExpr fwd =
                        ctx.mkAnd(ctx.mkGe(peerLen, zero), dataFwd, ctx.mkEq(length, newVal));
                    accSome = ctx.mkOr(accSome, fwd);
                  }
                }
              }
              BoolExpr cond1 = _encoderSlice.mkIf(accNone, ctx.mkEq(length, minusOne), accSome);
              BoolExpr cond2 = _encoderSlice.mkIf(acc, ctx.mkEq(length, zero), cond1);
              solver.add(cond2);
            });

    return lenVars;
  }

  /*
   * Instruments the network with load balancing information to destination
   * port for graph edge ge. Each router will split load according to the
   * number of neighbors it actively uses to get to ge.
   */
  public Map<String, ArithExpr> instrumentLoad(GraphEdge ge) {
    Context ctx = _encoderSlice.getCtx();
    Solver solver = _encoderSlice.getSolver();
    String sliceName = _encoderSlice.getSliceName();

    BoolExpr fwdIface = _encoderSlice.getForwardsAcross().get(ge.getRouter(), ge);
    assert (fwdIface != null);

    Map<String, ArithExpr> loadVars = new HashMap<>();
    _encoderSlice
        .getGraph()
        .getConfigurations()
        .forEach(
            (router, conf) -> {
              String name =
                  _encoderSlice.getEncoder().getId() + "_" + sliceName + "_load_" + router;
              ArithExpr var = ctx.mkIntConst(name);
              loadVars.put(router, var);
              _encoderSlice.getAllVariables().put(var.toString(), var);
            });

    // Lower bound for all lengths
    loadVars.forEach(
        (name, var) -> {
          solver.add(ctx.mkGe(var, ctx.mkInt(0)));
        });

    // Root router has load 1 if it uses the interface
    ArithExpr zero = ctx.mkInt(0);
    ArithExpr one = ctx.mkInt(1);
    ArithExpr baseRouterLoad = loadVars.get(ge.getRouter());
    solver.add(ctx.mkImplies(fwdIface, ctx.mkEq(baseRouterLoad, one)));

    _encoderSlice
        .getGraph()
        .getEdgeMap()
        .forEach(
            (router, edges) -> {
              if (!router.equals(ge.getRouter())) {
                ArithExpr var = loadVars.get(router);
                ArithExpr acc = ctx.mkInt(0);
                for (GraphEdge edge : edges) {
                  if (!edge.isAbstract()) {
                    BoolExpr fwd = _encoderSlice.getForwardsAcross().get(router, edge);
                    assert (fwd != null);
                    if (edge.getPeer() != null) {
                      ArithExpr peerLoad = loadVars.get(edge.getPeer());
                      ArithExpr x = (ArithExpr) ctx.mkITE(fwd, peerLoad, zero);
                      acc = ctx.mkAdd(acc, x);
                    }
                  }
                }
                solver.add(ctx.mkEq(var, acc));
              }
            });

    return loadVars;
  }

  /*
   * Instruments the network to check if a router will be part
   * of a routing loop.
   */
  public BoolExpr instrumentLoop(String router) {
    Context ctx = _encoderSlice.getCtx();
    Solver solver = _encoderSlice.getSolver();
    String sliceName = _encoderSlice.getSliceName();

    // Add on-loop variables to track a loop
    Map<String, BoolExpr> onLoop = new HashMap<>();
    _encoderSlice
        .getGraph()
        .getConfigurations()
        .forEach(
            (r, conf) -> {
              String name =
                  _encoderSlice.getEncoder().getId()
                      + "_"
                      + sliceName
                      + "_on-loop_"
                      + router
                      + "_"
                      + r;
              BoolExpr var = ctx.mkBoolConst(name);
              onLoop.put(r, var);
              _encoderSlice.getAllVariables().put(var.toString(), var);
            });

    // Transitive closure for other routers
    _encoderSlice
        .getGraph()
        .getEdgeMap()
        .forEach(
            (r, edges) -> {
              BoolExpr var = onLoop.get(r);
              BoolExpr acc = ctx.mkBool(false);
              for (GraphEdge edge : edges) {
                if (!edge.isAbstract()) {
                  BoolExpr fwd = _encoderSlice.getForwardsAcross().get(r, edge);
                  String peer = edge.getPeer();
                  if (peer != null) {
                    if (peer.equals(router)) {
                      // If next hop is static route router, then on loop
                      acc = ctx.mkOr(acc, fwd);
                    } else {
                      // Otherwise check if next hop also is on the loop
                      BoolExpr peerOnLoop = onLoop.get(peer);
                      acc = ctx.mkOr(acc, ctx.mkAnd(fwd, peerOnLoop));
                    }
                  }
                }
              }
              solver.add(ctx.mkEq(var, acc));
            });

    return onLoop.get(router);
  }
}
