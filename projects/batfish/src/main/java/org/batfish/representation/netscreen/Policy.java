package org.batfish.representation.netscreen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Policy implements Serializable {

  public static enum PolicyTarget {
    PERMIT,
    DENY
  }
  
  /*
   * 
   * id ID from FROMZONE to TOZONE SRCADDR DSTADDR SERVICE permit/deny log? traffic?
set policy id 1 from "Trust" to "Untrust"  "SAIDA_FULL" "Any" "ANY" permit log
set policy id 1
exit

Note: the second piece is the actual block, not the first piece

set policy id 2 from "Untrust" to "Trust"  "Any" "MIP(200.200.200.201)" "HTTP" permit log
set policy id 2
set dst-address "Foo"
exit

set policy id 4 disable

set policy id 3 from "Untrust" to "DMZ"  "Any-IPv4" "something" "HTTP" permit log traffic mbw 300000
   */
  
  private static final long serialVersionUID = 1L;
  
  private boolean _enabled;
  
  // id
  private final int _id;
  
  // from zone
  private String _fromZone;
  
  // to zone
  private String _toZone;
  
  // Note: addresses/address group names can be preceded by a "!"
  
  // src-address
  private final List<String> _srcAddressGroup;
  
  // dst-address
  private final List<String> _dstAddressGroup;
  
  // service
  private final List<String> _services;
  
  
  // attack?
  
  // qos-profile?
  
  // permit / deny
  private PolicyTarget _allowed; 
  
  // log
  private boolean _log;
  
  public Policy(int id) {
    _enabled = true;
    _id = id;
    _services = new ArrayList<>();
    _srcAddressGroup = new ArrayList<>();
    _dstAddressGroup = new ArrayList<>();
  }

  public int getId() {
    return _id;
  }

  public String getFromZone() {
    return _fromZone;
  }

  public void setFromZone(String fromZone) {
    _fromZone = fromZone;
  }

  public String getToZone() {
    return _toZone;
  }

  public void setToZone(String toZone) {
    _toZone = toZone;
  }

  public List<String> getSrcAddressGroup() {
    return _srcAddressGroup;
  }

  public List<String> getDstAddressGroup() {
    return _dstAddressGroup;
  }

  public List<String> getServices() {
    return _services;
  }

  public boolean getEnabled() {
    return _enabled;
  }

  public void setEnabled(boolean enabled) {
    _enabled = enabled;
  }
  
  public void addSrcAddress(String addr) {
    _srcAddressGroup.add(addr);
  }
  
  public void addDstAddress(String addr) {
    _dstAddressGroup.add(addr);
  }
  
  public void addService(String service) {
    _services.add(service);
  }

  public PolicyTarget getAllowed() {
    return _allowed;
  }

  public void setAllowed(PolicyTarget allowed) {
    _allowed = allowed;
  }

  public boolean getLog() {
    return _log;
  }

  public void setLog(boolean log) {
    _log = log;
  }
}
