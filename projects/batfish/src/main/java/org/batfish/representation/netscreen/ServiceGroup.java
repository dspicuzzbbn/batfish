package org.batfish.representation.netscreen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// set group service "Denied" add "SUN-RPC-SADMIND"
public class ServiceGroup implements Serializable {

  private static final long serialVersionUID = 1L;
  
  final String _name;
  
  private final List<String> _services;
  
  public ServiceGroup(String name) {
    _name = name;
    _services = new ArrayList<>();
  }
  
  public void addService(String svc) {
    _services.add(svc);
  }
  
  public List<String> getServices() {
    return _services;
  }
}
