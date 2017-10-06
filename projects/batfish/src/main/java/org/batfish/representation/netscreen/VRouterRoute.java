package org.batfish.representation.netscreen;

import java.io.Serializable;

// set route 0.0.0.0/0 interface ethernet0/2 gateway 1.2.3.4
public class VRouterRoute implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private String _ipv4;
  private String _ipv6;
  
  private String _interface;
  
  private String _vrouter;
  
  private String _gateway_ipv4;
  private String _gateway_ipv6;
  
  private String _description;
  
  private Integer _preference;

  private Integer _metric;

  public String getInterface() {
    return _interface;
  }

  public void setInterface(String interface1) {
    _interface = interface1;
  }

  public String getDescription() {
    return _description;
  }

  public void setDescription(String description) {
    _description = description;
  }

  public Integer getPreference() {
    return _preference;
  }

  public void setPreference(Integer preference) {
    _preference = preference;
  }

  public Integer getMetric() {
    return _metric;
  }

  public void setMetric(Integer metric) {
    _metric = metric;
  }

  public String getVrouter() {
    return _vrouter;
  }

  public void setVrouter(String vrouter) {
    _vrouter = vrouter;
  }

  public String getIpv4() {
    return _ipv4;
  }

  public void setIpv4(String ipv4) {
    _ipv4 = ipv4;
  }

  public String getIpv6() {
    return _ipv6;
  }

  public void setIpv6(String ipv6) {
    _ipv6 = ipv6;
  }

  public String getGatewayIpv4() {
    return _gateway_ipv4;
  }

  public void setGatewayIpv4(String gateway_ipv4) {
    _gateway_ipv4 = gateway_ipv4;
  }

  public String getGatewayIpv6() {
    return _gateway_ipv6;
  }

  public void setGatewayIpv6(String gateway_ipv6) {
    _gateway_ipv6 = gateway_ipv6;
  }

}
