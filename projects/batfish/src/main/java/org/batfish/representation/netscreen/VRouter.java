package org.batfish.representation.netscreen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VRouter implements Serializable {

  private static final long serialVersionUID = 1L;

  // Note: two default vrouters: trust-vr / untrust-vr
  
  String _name;
  
  private String _routerId;
  
  // add-default-route
  private boolean _addDefaultRoute;
  
  // auto-route-export
  private boolean _autoRouteExport;
  
  // sharable
  private boolean _sharable;
  
  // has access lists
  private final Map<Integer, AccessList> _accessLists;
  
  // protocol settings
  private final Map<String, VRouterProtocol> _protocols;
  
  // has routes
  private final List<VRouterRoute> _routes;
  
  // has route maps
  private final Map<String, VRouterRouteMap> _routeMaps;
  
  
  // route map?
  // set match ip 20
  // .. this matches anything in an access list?
  
  public VRouter(String name) {
    _name = name;
    
    _accessLists = new TreeMap<>();
    _protocols = new TreeMap<>();
    _routes = new ArrayList<>();
    _routeMaps = new TreeMap<>();
  }


  public boolean getSharable() {
    return _sharable;
  }


  public void setSharable(boolean sharable) {
    _sharable = sharable;
  }


  public boolean getAutoRouteExport() {
    return _autoRouteExport;
  }


  public void setAutoRouteExport(boolean autoRouteExport) {
    _autoRouteExport = autoRouteExport;
  }


  public boolean getAddDefaultRoute() {
    return _addDefaultRoute;
  }


  public void setAddDefaultRoute(boolean addDefaultRoute) {
    _addDefaultRoute = addDefaultRoute;
  }
  
  public void addRoute(VRouterRoute route) {
    _routes.add(route);
  }

  public List<VRouterRoute> getRoutes() {
    return _routes;
  }

  public AccessList getAccessList(int id) {
    AccessList acl = _accessLists.get(id);
    if (acl == null) {
      acl = new AccessList();
      _accessLists.put(id, acl);
    }
    return acl;
  }

  public Map<Integer, AccessList> getAccessLists() {
    return _accessLists;
  }


  public String getRouterId() {
    return _routerId;
  }


  public void setRouterId(String routerId) {
    _routerId = routerId;
  }


  public Map<String, VRouterProtocol> getProtocols() {
    return _protocols;
  }
  
  public VRouterProtocol getProtocol(String name) {
    VRouterProtocol protocol = _protocols.get(name);
    if (protocol == null) {
      protocol = new VRouterProtocol(name);
      _protocols.put(name, protocol);
    }
    return protocol;
  }


  public Map<String, VRouterRouteMap> getRouteMaps() {
    return _routeMaps;
  }
  
  public VRouterRouteMap getRouteMap(String name) {
    VRouterRouteMap rm = _routeMaps.get(name);
    if (rm == null) {
      rm = new VRouterRouteMap(name);
      _routeMaps.put(name, rm);
    }
    return rm;
  }
}
