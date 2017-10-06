package org.batfish.representation.netscreen;

import java.io.Serializable;

public class VRouterProtocol implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private boolean _enable;
  
  private final String _name;
  
  // TODO: other settings exist.. 
  // set redistribute route-map "name" protocol static
  // set advertise-def-route metric 1 metric-type 1
  // set neighbor peer-group ...
  
  // rip/ripng settings:
  // set max-neighbor-count 1
  // set trusted-neighbors 10

  public VRouterProtocol(String name) {
    _name = name;
  }

  public boolean getEnable() {
    return _enable;
  }

  public void setEnable(boolean enable) {
    _enable = enable;
  }

  public String getName() {
    return _name;
  }

}
