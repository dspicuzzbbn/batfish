package org.batfish.grammar.netscreen;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.batfish.common.Warnings;
import org.batfish.grammar.ControlPlaneExtractor;
import org.batfish.grammar.netscreen.NetscreenParser.Interface_nameContext;
import org.batfish.grammar.netscreen.NetscreenParser.S_addressContext;
import org.batfish.grammar.netscreen.NetscreenParser.S_groupContext;
import org.batfish.grammar.netscreen.NetscreenParser.S_hostnameContext;
import org.batfish.grammar.netscreen.NetscreenParser.S_interfaceContext;
import org.batfish.grammar.netscreen.NetscreenParser.S_policyContext;
import org.batfish.grammar.netscreen.NetscreenParser.S_serviceContext;
import org.batfish.grammar.netscreen.NetscreenParser.S_vrouterContext;
import org.batfish.grammar.netscreen.NetscreenParser.S_zoneContext;
import org.batfish.grammar.netscreen.NetscreenParser.StatementContext;
import org.batfish.grammar.netscreen.NetscreenParser.VariableContext;
import org.batfish.representation.netscreen.AccessList;
import org.batfish.representation.netscreen.Address;
import org.batfish.representation.netscreen.AddressGroup;
import org.batfish.representation.netscreen.Interface;
import org.batfish.representation.netscreen.NetscreenConfiguration;
import org.batfish.representation.netscreen.Policy;
import org.batfish.representation.netscreen.Policy.PolicyTarget;
import org.batfish.representation.netscreen.VRouterRoute;
import org.batfish.representation.netscreen.VRouterRouteMap;
import org.batfish.representation.netscreen.Service;
import org.batfish.representation.netscreen.ServiceDetails;
import org.batfish.representation.netscreen.ServiceGroup;
import org.batfish.representation.netscreen.VRouter;
import org.batfish.representation.netscreen.VRouterProtocol;
import org.batfish.representation.netscreen.Zone;
import org.batfish.representation.netscreen.Interface.InterfaceMode;
import org.batfish.vendor.VendorConfiguration;

public class NetscreenControlPlaneExtractor extends NetscreenParserBaseListener
  implements ControlPlaneExtractor {

  private NetscreenConfiguration _configuration;

  private NetscreenCombinedParser _parser;

  private String _text;

  private final Set<String> _unimplementedFeatures;

  private Warnings _w;

  public NetscreenControlPlaneExtractor(String fileText, NetscreenCombinedParser mrvParser,
      Warnings warnings) {
    _text = fileText;
    _parser = mrvParser;
    _w = warnings;
    _unimplementedFeatures = new TreeSet<>();
  }
  
  @Override
  public Set<String> getUnimplementedFeatures() {
    return _unimplementedFeatures;
  }

  @Override
  public VendorConfiguration getVendorConfiguration() {
    return _configuration;
  }

  @Override
  public void processParseTree(ParserRuleContext tree) {
    _configuration = new NetscreenConfiguration();
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(this, tree);
  }
  
  //
  // Helpers
  //
  
  private static String getVariableText(VariableContext ctx) {
    return (ctx.QUOTED_TEXT() != null)
      ? ctx.QUOTED_TEXT().getText()
      : ctx.getText();
  }
  
  private static String getInterfaceName(Interface_nameContext ctx) {
    return (ctx.QUOTED_TEXT() != null)
      ? ctx.QUOTED_TEXT().getText()
      : ctx.getText();
  }
  
  private static Integer getDec(TerminalNode dec) {
    return Integer.valueOf(dec.getText());
  }
  
  private static Integer getDec(Token dec) {
    return Integer.valueOf(dec.getText());
  }
  
  // helper function to make processing easier
  private static <T> boolean ifPresent(T node, Consumer<T> fn) {
    if (node != null) {
      fn.accept(node);
      return true;
    }
    return false;
  }
  
  
  private boolean _stmt_set = false;
  
  @Override
  public void enterStatement(StatementContext ctx) {
    if (ctx.SET() != null) {
      _stmt_set = true;
    } else if (ctx.UNSET() != null) {
      _stmt_set = false;
    } else {
      throw new RuntimeException("Internal error: set/unset not set");
    }
  }
  
  @Override
  public void exitS_address(S_addressContext ctx) {
    Map<String, Address> azone = _configuration.getAddressZone(getVariableText(ctx.zoneName));
    
    Address addr = new Address(getVariableText(ctx.addrname));
    
    if (ctx.address != null) {
      addr.setIp(ctx.address.getText());
      addr.setMask(ctx.netmask.getText());
    }
    
    if (ctx.ipv6_prefix != null) {
      addr.setIpv6(ctx.ipv6_prefix.getText());
    }
    
    ifPresent(ctx.sa_fqdn(), fqdn -> {
      addr.setFqdn(fqdn.getText());
    });
    
    if (ctx.description != null) {
      addr.setDescription(getVariableText(ctx.description));
    }
    
    azone.put(addr.getName(), addr);
  }
  
  @Override
  public void exitS_group(S_groupContext ctx) {
    ifPresent(ctx.sg_address(), addr -> {
      AddressGroup ag = _configuration.getAddressGroup(getVariableText(addr.zonename));
      if (addr.address != null) {
        ag.addAddress(getVariableText(addr.address));
      }
    });
    
    ifPresent(ctx.sg_service(), svc -> {
      ServiceGroup sg = _configuration.getServiceGroup(getVariableText(svc.name));
      if (svc.address != null) {
        sg.addService(getVariableText(svc.address));
      }
    });
  }
  
  @Override
  public void exitS_hostname(S_hostnameContext ctx) {
    String hostname = getVariableText(ctx.variable());
    _configuration.setHostname(hostname);
  }
  
  @Override
  public void exitS_interface(S_interfaceContext ctx) {
    
    String if_name = getInterfaceName(ctx.name);
    
    Interface iface = _configuration.getInterface(if_name);
    
    if (ctx.si_disable() != null) {
      iface.setEnable(false);
    }
    
    ifPresent(ctx.si_ip(), si_ip -> {
      
      boolean hasOptions = ifPresent(si_ip.siip_options(), siip_options -> {
        if (siip_options.MANAGEABLE() != null) {
          iface.setManageable(_stmt_set);
        }
        
        if (siip_options.IP_PREFIX() != null) {
          iface.setIpv4Address(siip_options.IP_PREFIX().getText());
        }
        
        if (siip_options.UNNUMBERED() != null) {
          iface.setIpv4Address(null);
        }
      });
      
      if (!hasOptions) {
        if (!_stmt_set) {
          // ip can be unset
          iface.setIpv4Address(null);
        }
      }
    });
    
    if (ctx.si_nat() != null) {
      iface.setMode(InterfaceMode.NAT);
    }
    
    if (ctx.si_route() != null) {
      iface.setMode(InterfaceMode.ROUTE);
    }
    
    ifPresent(ctx.si_zone(), si_zone -> {
      if (si_zone.tag != null) {
        iface.setTag(getDec(si_zone.tag));
      }
      
      iface.setZone(getVariableText(si_zone.zone));
    });
    
    // TODO: ipv6
  }
  
  @Override
  public void exitS_policy(S_policyContext ctx) {
    int policyId = getDec(ctx.id);
    
    Policy policy = _configuration.getPolicy(policyId);
    
    if (ctx.DISABLE() != null) {
      policy.setEnabled(false);
    }
    
    // single-line version
    ifPresent(ctx.sp_config(), cfg -> {
      policy.setFromZone(getVariableText(cfg.fromzone));
      policy.setToZone(getVariableText(cfg.tozone));
      policy.addSrcAddress(getVariableText(cfg.srcaddress));
      policy.addDstAddress(getVariableText(cfg.dstaddress));
      policy.addService(getVariableText(cfg.service));
      
      ifPresent(cfg.spc_nat(), unused -> {
        todo(unused, "policy nat rules");
      });
      
      if (cfg.DENY() != null) {
        policy.setAllowed(PolicyTarget.DENY);
      } else if (cfg.PERMIT() != null) {
        policy.setAllowed(PolicyTarget.PERMIT);
      }
      
      if (cfg.LOG() != null) {
        policy.setLog(true);
      }
      
      ifPresent(cfg.spc_traffic(), unused -> {
        todo(unused, "policy traffic shaping rules");
      });
    });
    
    // block version
    ifPresent(ctx.sp_statements(), stmts -> {
      stmts.sp_statement().forEach(stmt -> {
        ifPresent(stmt.sp_srcaddr(), src -> {
          policy.addSrcAddress(getVariableText(src.variable()));
        });
        
        ifPresent(stmt.sp_dstaddr(), dst -> {
          policy.addDstAddress(getVariableText(dst.variable()));
        });
        
        ifPresent(stmt.sp_service(), svc -> {
          policy.addService(getVariableText(svc.variable()));
        });
      });
    });
  }
  
  @Override
  public void exitS_service(S_serviceContext ctx) {
    Service service = _configuration.getService(getVariableText(ctx.name));
    
    ifPresent(ctx.ss_details(), details -> {
      
      int srcPortStart = Integer.valueOf(details.src_ports.start.getText());
      int srcPortEnd = Integer.valueOf(details.src_ports.stop.getText());
      
      int dstPortStart = Integer.valueOf(details.src_ports.start.getText());
      int dstPortEnd = Integer.valueOf(details.src_ports.stop.getText());
      
      ServiceDetails sd = new ServiceDetails(srcPortStart, srcPortEnd, dstPortStart, dstPortEnd);
      
      ifPresent(details.proto, proto -> { 
        if (proto.ICMP() != null) {
          sd.setProtocol(1);
        } else if (proto.TCP() != null) {
          sd.setProtocol(6);
        } else if (proto.UDP() != null) {
          sd.setProtocol(17);
        } else if (proto.IGMP() != null) {
          sd.setProtocol(88);
        }
      });
      
      if (details.protoNum != null) {
        sd.setProtocol(Integer.valueOf(details.protoNum.getText()));
      }
      
      service.getDetails().add(sd);
    });
    
    ifPresent(ctx.ss_timeout(), timeout -> {
      service.setTimeout(Integer.valueOf(timeout.timeout.getText()));
    });
    
  }
  
  @Override
  public void exitS_vrouter(S_vrouterContext ctx) {
    VRouter vrouter = _configuration.getVRouter(getVariableText(ctx.variable()));
    
    if (ctx.SHARABLE() != null) {
      vrouter.setSharable(true);
    }
    
    ifPresent(ctx.sv_statement(), stmts -> {
      stmts.forEach(stmt -> {
        boolean set = stmt.SET() != null;
        
        ifPresent(stmt.sv_accesslist(), sv_accesslist -> {
          if (sv_accesslist.PERMIT() != null) {
            int id = getDec(sv_accesslist.id);
            int index = getDec(sv_accesslist.idx);
            
            AccessList acl = vrouter.getAccessList(id);
            if (sv_accesslist.DEFAULT_ROUTE() != null) {
              acl.addRule(index, "default-route");
            } else {
              acl.addRule(index, sv_accesslist.ip.getText());
            }
          }
        });
        
        ifPresent(stmt.sv_options(), sv_options -> {
          if (sv_options.ADD_DEFAULT_ROUTE() != null) {
            vrouter.setAddDefaultRoute(set);
          } else if (sv_options.AUTO_ROUTE_EXPORT() != null) {
            vrouter.setAutoRouteExport(set);
          }
        });
        
        ifPresent(stmt.sv_protocol(), sv_protocol -> {
          VRouterProtocol proto = vrouter.getProtocol(getVariableText(sv_protocol.name));
          
          sv_protocol.svp_statement().forEach(sv_stmt -> {
            boolean svset = sv_stmt.SET() != null;
            
            if (sv_stmt.ENABLE() != null) {
              proto.setEnable(svset);
            }
          });
        });
        
        ifPresent(stmt.sv_route(), sv_route -> {
          VRouterRoute route = new VRouterRoute();
          
          if (sv_route.ipv4 != null) {
            route.setIpv4(sv_route.ipv4.getText());
          } else {
            route.setIpv6(sv_route.ipv6.getText());
          }
          
          if (sv_route.name != null) {
            route.setInterface(getInterfaceName(sv_route.name));
          }
          
          if (sv_route.vrouter != null) {
            route.setVrouter(getVariableText(sv_route.vrouter));
          }
          
          if (sv_route.gw_ipv4 != null) {
            route.setGatewayIpv4(sv_route.gw_ipv4.getText());
          } else if (sv_route.gw_ipv6 != null) {
            route.setGatewayIpv6(sv_route.gw_ipv6.getText());
          }
          
          if (sv_route.m != null) {
            route.setMetric(getDec(sv_route.m));
          }
          
          if (sv_route.pref != null) {
            route.setPreference(getDec(sv_route.pref));
          }
          
          if (sv_route.desc != null) {
            route.setDescription(getVariableText(sv_route.desc));
          }
          
          vrouter.addRoute(route);
        });
        
        ifPresent(stmt.sv_routemap(), sv_routemap -> {
          VRouterRouteMap rm = vrouter.getRouteMap(getVariableText(sv_routemap.name));
          ifPresent(sv_routemap.svr_match(), svr_match -> {
            rm.setMatchIp(getDec(svr_match.match));
          });
        });
        
        ifPresent(stmt.sv_routerid(), sv_routerid -> {
          vrouter.setRouterId(getVariableText(sv_routerid.variable()));
        });
      });
    });
  }
  
  @Override
  public void exitS_zone(S_zoneContext ctx) {
    
    ifPresent(ctx.sz_id(), sz_id -> {
      Zone zone = _configuration.getZone(getVariableText(sz_id.name));
      zone.setId(getDec(sz_id.id));
    });
    
    ifPresent(ctx.sz_name(), sz_name -> {
      Zone zone = _configuration.getZone(getVariableText(sz_name.name));
      if (sz_name.BLOCK() != null) {
        zone.setBlock(_stmt_set);
      }
      
      if (sz_name.TCP_RST() != null) {
        zone.setTcpRst(_stmt_set);
      }
      
      if (sz_name.sname != null) {
        zone.setVrouter(getVariableText(sz_name.sname));
      }
      
      if (sz_name.vname != null) {
        zone.setVrouter(getVariableText(sz_name.vname));
      }
    });
  }
  
  
  
  @SuppressWarnings("unused")
  private void todo(ParserRuleContext ctx, String feature) {
    _w.todo(ctx, feature, _parser, _text);
    _unimplementedFeatures.add("Netscreen: " + feature);
  }
}
