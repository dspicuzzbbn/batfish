package org.batfish.representation.netscreen;

import java.io.Serializable;

public class IkeGateway implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private final String _name;
  
  private String _address;
  
  private String _id;

  private String _outgoingInterface;
  
  private String _preshareKey;
  
  private String _p1Proposal;
  
  private String _p2Proposal;
  
  // nat traversal options
  private Integer _keepAliveFrequency;
  
  private boolean _udpChecksum;
  
  
  public IkeGateway(String name) {
    _name = name; 
  }

  public String getName() {
    return _name;
  }

  public String getAddress() {
    return _address;
  }

  public void setAddress(String address) {
    _address = address;
  }

  public String getId() {
    return _id;
  }

  public void setId(String id) {
    _id = id;
  }

  public String getOutgoingInterface() {
    return _outgoingInterface;
  }

  public void setOutgoingInterface(String outgoingInterface) {
    _outgoingInterface = outgoingInterface;
  }

  public String getPreshareKey() {
    return _preshareKey;
  }

  public void setPreshareKey(String preshareKey) {
    _preshareKey = preshareKey;
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

  public Integer getKeepAliveFrequency() {
    return _keepAliveFrequency;
  }

  public void setKeepAliveFrequency(Integer keepAliveFrequency) {
    _keepAliveFrequency = keepAliveFrequency;
  }

  public boolean getUdpChecksum() {
    return _udpChecksum;
  }

  public void setUdpChecksum(boolean udpChecksum) {
    _udpChecksum = udpChecksum;
  }

}
