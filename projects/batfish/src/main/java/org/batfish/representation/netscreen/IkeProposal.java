package org.batfish.representation.netscreen;

import java.io.Serializable;
import org.batfish.datamodel.DiffieHellmanGroup;
import org.batfish.datamodel.EncryptionAlgorithm;
import org.batfish.datamodel.IkeAuthenticationAlgorithm;
import org.batfish.datamodel.IkeAuthenticationMethod;

public class IkeProposal implements Serializable {

  public static enum ProposalType {
    Phase1,
    Phase2
  }
  
  private static final long serialVersionUID = 1L;
  
  final private String _name;
  
  final private ProposalType _proposalType;
  
  private IkeAuthenticationMethod _authenticationMethod;

  private DiffieHellmanGroup _dhGroup;

  private EncryptionAlgorithm _encryptionAlgorithm;

  private IkeAuthenticationAlgorithm _hashAlgorithm;
  
  private Integer _seconds;

  public IkeProposal(String name, ProposalType proposalType) {
    _name = name;
    _proposalType = proposalType;
  }
  
  public IkeAuthenticationMethod getAuthenticationMethod() {
    return _authenticationMethod;
  }

  public DiffieHellmanGroup getDhGroup() {
    return _dhGroup;
  }

  public EncryptionAlgorithm getEncryptionAlgorithm() {
    return _encryptionAlgorithm;
  }

  public IkeAuthenticationAlgorithm getHashAlgorithm() {
    return _hashAlgorithm;
  }
  
  public String getName() {
    return _name;
  }

  public ProposalType getProposalType() {
    return _proposalType;
  }
  
  public Integer getSeconds() {
    return _seconds;
  }
  
  public void setAuthenticationMethod(IkeAuthenticationMethod authenticationMethod) {
    _authenticationMethod = authenticationMethod;
  }

  public void setDhGroup(DiffieHellmanGroup dhGroup) {
    _dhGroup = dhGroup;
  }

  public void setEncryptionAlgorithm(EncryptionAlgorithm encryptionAlgorithm) {
    _encryptionAlgorithm = encryptionAlgorithm;
  }

  public void setHashAlgorithm(IkeAuthenticationAlgorithm hashAlgorithm) {
    _hashAlgorithm = hashAlgorithm;
  }

  public void setSeconds(Integer seconds) {
    _seconds = seconds;
  }
}
