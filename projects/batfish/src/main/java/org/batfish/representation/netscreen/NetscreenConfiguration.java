package org.batfish.representation.netscreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import org.batfish.common.VendorConversionException;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.ConfigurationFormat;
import org.batfish.datamodel.LineAction;
import org.batfish.vendor.VendorConfiguration;

public class NetscreenConfiguration extends VendorConfiguration {

  /** */
  private static final long serialVersionUID = 1L;

  private transient Configuration _c;

  private String _hostname;
  
  private final Map<String, Map<String, Address>> _addresses;
 
  private final Map<String, AddressGroup> _addressGroups;
  
  private final Map<String, Interface> _interfaces;
  
  private final Map<Integer, Policy> _policies;
  
  private final Map<String, Service> _services;
  
  private final Map<String, ServiceGroup> _serviceGroups;
  
  private final Map<String, VRouter> _vrouters;
  
  private final Map<String, Zone> _zones;
  
  // zones

  private ConfigurationFormat _vendor;
  
  public NetscreenConfiguration() {
    super();
    
    _addressGroups = new TreeMap<>();
    _addresses = new TreeMap<>();
    _interfaces = new TreeMap<>();
    _policies = new TreeMap<>();
    _services = new TreeMap<>();
    _serviceGroups = new TreeMap<>();
    _vrouters = new TreeMap<>();
    _zones = new TreeMap<>();
    
    // setup any defaults here
    getVRouter("trust-vr");
    getVRouter("untrust-vr");
  }
  
  public AddressGroup getAddressGroup(String groupName) {
    AddressGroup sg = _addressGroups.get(groupName);
    if (sg == null) {
      sg = new AddressGroup(groupName);
      _addressGroups.put(groupName, sg);
    }
    return sg;
  }
  
  public Map<String, Address> getAddressZone(String zone) {
    Map<String, Address> azone = _addresses.get(zone);
    if (azone == null) {
      azone = new TreeMap<>();
      _addresses.put(zone, azone);
    }
    return azone;
  }

  @Override
  public String getHostname() {
    return _hostname;
  }
  
  public Interface getInterface(String name) {
    Interface iface = _interfaces.get(name);
    if (iface == null) {
      iface = new Interface(name);
      _interfaces.put(name,  iface);
    }
    return iface;
  }
  
  public Policy getPolicy(int id) {
    Policy policy = _policies.get(id);
    if (policy == null) {
      policy = new Policy(id);
      _policies.put(id, policy);
    }
    return policy;
  }
  
  public Service getService(String name) {
    Service service = _services.get(name);
    if (service == null) {
      service = new Service(name);
      _services.put(name, service);
    }
    return service;
  }
  
  public ServiceGroup getServiceGroup(String groupName) {
    ServiceGroup sg = _serviceGroups.get(groupName);
    if (sg == null) {
      sg = new ServiceGroup(groupName);
      _serviceGroups.put(groupName, sg);
    }
    return sg;
  }
  
  public VRouter getVRouter(String name) {
    VRouter vrouter = _vrouters.get(name);
    if (vrouter == null) {
      vrouter = new VRouter(name);
      _vrouters.put(name, vrouter);
    }
    return vrouter;
  }
  
  public Zone getZone(String name) {
    Zone zone = _zones.get(name);
    if (zone == null) {
      zone = new Zone(name);
      _zones.put(name, zone);
    }
    return zone;
  }
  
  public Map<String, Zone> getZones() {
    return _zones;
  }

  @Override
  public SortedSet<String> getRoles() {
    throw new UnsupportedOperationException("no implementation for generated method");
    // TODO Auto-generated method stub
  }

  @Override
  public Set<String> getUnimplementedFeatures() {
    throw new UnsupportedOperationException("no implementation for generated method");
    // TODO Auto-generated method stub
  }

  public ConfigurationFormat getVendor() {
    return _vendor;
  }

  @Override
  public void setHostname(String hostname) {
    _hostname = hostname;
  }

  @Override
  public void setRoles(SortedSet<String> roles) {
    throw new UnsupportedOperationException("no implementation for generated method");
    // TODO Auto-generated method stub
  }

  @Override
  public void setVendor(ConfigurationFormat format) {
    _vendor = format;
  }

  @Override
  public Configuration toVendorIndependentConfiguration() throws VendorConversionException {
    _c = new Configuration(_hostname);
    _c.setDefaultCrossZoneAction(LineAction.ACCEPT);
    _c.setDefaultInboundAction(LineAction.REJECT);
    
    NavigableMap<String, org.batfish.datamodel.Interface> interfaces = new TreeMap<>();
    for (Interface iface: _interfaces.values()) {
      org.batfish.datamodel.Interface viface = new org.batfish.datamodel.Interface(iface.getName());
      interfaces.put(iface.getName(), viface);
    }
    
    _c.setInterfaces(interfaces);
    
    return _c;
  }
}
