package org.batfish.representation.netscreen;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class AccessList implements Serializable {

  private static final long serialVersionUID = 1L;

  // has an access list
  // set access-list 20
  // set access-list 20 permit default-route 5
  // set access-list 20 permit ip 1.2.3.4/24 10
  
  // this only shows up inside vrouter configs
  final private Map<Integer, String> _rules;
  
  public AccessList() {
    _rules = new TreeMap<>();
  }
  
  public void addRule(int index, String name) {
    
  }

  public Map<Integer, String> getRules() {
    return _rules;
  }  
  
}
