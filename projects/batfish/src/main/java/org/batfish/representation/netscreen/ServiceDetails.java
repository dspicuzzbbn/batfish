package org.batfish.representation.netscreen;

import java.io.Serializable;

public class ServiceDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  // set service "SSH" timeout 600 
  
  // initial
  // set service "smtps" protocol tcp src-port 0-65535 dst-port 465-465 
  
  // additive
  
  // set service "ESP/AH" protocol 50 src-port 0-65535 dst-port 0-65535
  // set service "ESP/AH" + 51 src-port 0-65535 dst-port 0-65535
  
  // protocol number
  // icmp: 1
  // tcp: 6
  // udp: 17
  private int _protocol;
  
  // src-port
  private final int _srcPortStart;
  private final int _srcPortEnd;
  
  // dst-port
  private final int _dstPortStart;
  private final int _dstPortEnd;
  
  
  public ServiceDetails(int srcPortStart, int srcPortEnd, int dstPortStart, int dstPortEnd) {
    _srcPortStart = srcPortStart;
    _srcPortEnd = srcPortEnd;
    _dstPortStart = dstPortStart;
    _dstPortEnd = dstPortEnd;
  }

  public int getProtocol() {
    return _protocol;
  }

  public int getSrcPortStart() {
    return _srcPortStart;
  }

  public int getSrcPortEnd() {
    return _srcPortEnd;
  }

  public int getDstPortStart() {
    return _dstPortStart;
  }

  public int getDstPortEnd() {
    return _dstPortEnd;
  }

  public void setProtocol(int protocol) {
    _protocol = protocol;
  }
}
