package org.batfish.representation.netscreen;

import java.io.Serializable;

// set address "Trust" "172.23.23.24/28" 172.23.23.24 255.255.255.240
public class Address implements Serializable {

  private static final long serialVersionUID = 1L;
  
  final private String _name;
  
  private String _ip;
  private String _mask;
  
  private String _ipv6;
  
  private String _fqdn;
  
  private String _description;
  
  public Address(String name) {
    _name = name;
  }
  
  public String getName() {
    return _name;
  }

  public String getIp() {
    return _ip;
  }

  public String getMask() {
    return _mask;
  }

  public String getDescription() {
    return _description;
  }

  public void setDescription(String description) {
    _description = description;
  }

  public String getIpv6() {
    return _ipv6;
  }

  public void setIpv6(String ipv6) {
    _ipv6 = ipv6;
  }

  public String getFqdn() {
    return _fqdn;
  }

  public void setFqdn(String fqdn) {
    _fqdn = fqdn;
  }

  public void setIp(String ip) {
    _ip = ip;
  }

  public void setMask(String mask) {
    _mask = mask;
  }  
}
