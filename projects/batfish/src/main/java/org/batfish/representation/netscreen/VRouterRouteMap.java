package org.batfish.representation.netscreen;

import java.io.Serializable;

public class VRouterRouteMap implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private final String _name;
  
  private Integer _permit;
  
  // this is probably matching an ACL...
  private Integer _matchIp;
  
  public VRouterRouteMap(String name) {
    _name = name;
  }

  public Integer getMatchIp() {
    return _matchIp;
  }

  public void setMatchIp(Integer matchIp) {
    _matchIp = matchIp;
  }

  public Integer getPermit() {
    return _permit;
  }

  public void setPermit(Integer permit) {
    _permit = permit;
  }

  public String getName() {
    return _name;
  }

}
