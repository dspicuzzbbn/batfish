package org.batfish.representation.netscreen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


// set group address "Trust"
// set group address "Trust" "SAIDA_FULL" add "172.23.23.24/28"
public class AddressGroup implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private String _name;
  
  private final List<String> _addresses;
  
  public AddressGroup(String name) {
    _name = name;
    _addresses = new ArrayList<>();
  }

  public String getName() {
    return _name;
  }

  public void setName(String name) {
    _name = name;
  }

  public List<String> getAddresses() {
    return _addresses;
  }
  
  public void addAddress(String address) {
    _addresses.add(address);
  }
}
