package org.batfish.representation.netscreen;

import java.io.Serializable;

public class Interface implements Serializable {

  private static final long serialVersionUID = 1L;

  public static enum InterfaceMode {
    NAT, ROUTE
  }
  
  public static enum Ipv6Mode {
    NONE, HOST, ROUTER
  }
  
  // set interface "ethernet1" zone "Untrust"
  
  // name
  final private String _name;

  private boolean _enable;
  
  //private boolean _ipv6Enable;
  
  private InterfaceMode _mode;
  
  //private Ipv6Mode _ipv6Mode;
  
  // VLAN tag (seems to require a zone, only applied to subinterfaces)
  private Integer _tag;
  
  // zone
  private String _zone;
  
  // mip: management ip?
  
  // vip? nat only?
  
  // prefix notation
  private String _ipv4Address;
  
  // prefix notation
  //private String _ipv6Address;
  
  private boolean _manageable;
  
  // management ip
  
  
  public Interface(String name) {
    _name = name;
    
    _enable = false;
  }
  
  public InterfaceMode getMode() {
    return _mode;
  }
  
  public String getName() {
    return _name;
  }

  public String getZone() {
    return _zone;
  }
  
  public void setMode(InterfaceMode mode) {
    _mode = mode;
  }

  public void setZone(String zone) {
    _zone = zone;
  }

  public boolean getEnable() {
    return _enable;
  }

  public void setEnable(boolean enable) {
    _enable = enable;
  }

  public String getIpv4Address() {
    return _ipv4Address;
  }

  public void setIpv4Address(String ipv4Address) {
    _ipv4Address = ipv4Address;
  }

  public boolean getManageable() {
    return _manageable;
  }

  public void setManageable(boolean manageable) {
    _manageable = manageable;
  }

  public Integer getTag() {
    return _tag;
  }

  public void setTag(Integer tag) {
    _tag = tag;
  }
}
