package org.batfish.representation.netscreen;

import java.util.NavigableMap;
import java.util.TreeMap;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.LineAction;

public class NetscreenConfigurationConverter {

  public static Configuration convert(NetscreenConfiguration nc) {
    final Configuration _c = new Configuration(nc.getHostname());
    _c.setDefaultCrossZoneAction(LineAction.ACCEPT);
    _c.setDefaultInboundAction(LineAction.REJECT);
    
    // zones
    // -> each zone is associated with a vrouter, and there
    //NavigableMap<String, org.batfish.datamodel.Zone>
    
    
    NavigableMap<String, Interface> interfaces = new TreeMap<>();
    for (Interface iface: _interfaces.values()) {
      org.batfish.datamodel.Interface viface = new org.batfish.datamodel.Interface(iface.getName());
      interfaces.put(iface.getName(), viface);
    }
    
    _c.setInterfaces(interfaces);
    
    // ike gateways
    NavigableMap<String, org.batfish.datamodel.IkeGateway> ikeGateways = _c.getIkeGateways();
    
    auto(_c.getIkeGateways(), ikeGateways -> {
      ikeGateways.put("Hi", null);
    });
    
    return _c;
  }
  
}
