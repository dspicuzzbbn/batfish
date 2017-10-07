package org.batfish.representation.netscreen;

import java.io.Serializable;

public class Vpn implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private final String _name;
  
  private String _ikeGateway;
  
  private Boolean _replay;
  
  private Integer _idleTime;
  
  private String _p1Proposal;
  private String _p2Proposal;
  
  private Integer _bindId;
  private String _bindInterface;
  
  private String _bindZone;
  
  private String _proxyLocalIp;
  private String _proxyRemoteIp;
  private String _proxyService;

  public Vpn(String name) {
    _name = name;
  }

  public String getName() {
    return _name;
  }

  public String getIkeGateway() {
    return _ikeGateway;
  }

  public void setIkeGateway(String ikeGateway) {
    _ikeGateway = ikeGateway;
  }

  public Boolean getReplay() {
    return _replay;
  }

  public void setReplay(Boolean replay) {
    _replay = replay;
  }

  public Integer getIdleTime() {
    return _idleTime;
  }

  public void setIdleTime(Integer idleTime) {
    _idleTime = idleTime;
  }

  public String getP1Proposal() {
    return _p1Proposal;
  }

  public void setP1Proposal(String p1Proposal) {
    _p1Proposal = p1Proposal;
  }

  public String getP2Proposal() {
    return _p2Proposal;
  }

  public void setP2Proposal(String p2Proposal) {
    _p2Proposal = p2Proposal;
  }

  public Integer getBindId() {
    return _bindId;
  }

  public void setBindId(Integer bindId) {
    _bindId = bindId;
  }

  public String getBindInterface() {
    return _bindInterface;
  }

  public void setBindInterface(String bindInterface) {
    _bindInterface = bindInterface;
  }

  public String getBindZone() {
    return _bindZone;
  }

  public void setBindZone(String bindZone) {
    _bindZone = bindZone;
  }

  public String getProxyLocalIp() {
    return _proxyLocalIp;
  }

  public void setProxyLocalIp(String proxyLocalIp) {
    _proxyLocalIp = proxyLocalIp;
  }

  public String getProxyRemoteIp() {
    return _proxyRemoteIp;
  }

  public void setProxyRemoteIp(String proxyRemoteIp) {
    _proxyRemoteIp = proxyRemoteIp;
  }

  public String getProxyService() {
    return _proxyService;
  }

  public void setProxyService(String proxyService) {
    _proxyService = proxyService;
  }
}
