package org.batfish.representation.netscreen;

import java.io.Serializable;
import java.util.List;

public class Zone implements Serializable {
  
  private static final long serialVersionUID = 1L;

  // three default zones: trust / untrust / dmz
  // .. no?
  
  // zones are different in transparent mode?
  // .. v1-trust, v1-untrust, v1-dmz
  
  private final String _name;
  
  // filter types? tear-drop, syn-flood, ping-death... etc
  List<String> _screens;
  
  private boolean _block;
  
  private boolean _tcpRst;
  
  private String _vrouter;
  
  // not sure what this is
  private Integer _id;
  
  
  public Zone(String name) {
    _name = name;
    
    // TODO: defaults?
  }



  public String getName() {
    return _name;
  }
  
  public boolean getTcpRst() {
    return _tcpRst;
  }
  
  public void setTcpRst(boolean tcpRst) {
    _tcpRst = tcpRst;
  }
  
  public boolean getBlock() {
    return _block;
  }
  
  public void setBlock(boolean block) {
    _block = block;
  }



  public String getVrouter() {
    return _vrouter;
  }



  public void setVrouter(String vrouter) {
    _vrouter = vrouter;
  }



  public Integer getId() {
    return _id;
  }



  public void setId(Integer id) {
    _id = id;
  }
}
