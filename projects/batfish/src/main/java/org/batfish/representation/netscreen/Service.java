package org.batfish.representation.netscreen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Service implements Serializable {

  private static final long serialVersionUID = 1L;

  // set service "SSH" timeout 600 
  
  // initial
  // set service "smtps" protocol tcp src-port 0-65535 dst-port 465-465 
  
  // additive
  
  // set service "ESP/AH" protocol 50 src-port 0-65535 dst-port 0-65535
  // set service "ESP/AH" + 51 src-port 0-65535 dst-port 0-65535
  
  private final String _name;
  
  private final List<ServiceDetails> _details;
  
  // timeout
  private int _timeout;
  
  public Service(String name) {
    _name = name;
    _details = new ArrayList<>();
  }
  
  public int getTimeout() {
    return _timeout;
  }

  public void setTimeout(int timeout) {
    _timeout = timeout;
  }

  public List<ServiceDetails> getDetails() {
    return _details;
  }

  public String getName() {
    return _name;
  }
}
