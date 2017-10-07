parser grammar Netscreen_vpn;

import Netscreen_common;

options {
   tokenVocab = NetscreenLexer;
}

s_ike
:
   IKE
   (
      sike_gateway
      | sike_null
      | sike_proposal
   )
;

sike_gateway
:
   GATEWAY gw = variable
   (
      sikegw_address
      | sikegw_cert
      | sikegw_nat
   )
;

sikegw_address
:
   ADDRESS address = IP_ADDRESS
   (
      ID id = variable
   )?
   (
      AGGR
      | MAIN
   )?
   (
      OUTGOING_INTERFACE outiface = interface_name
   )?
   (
      PRESHARE preshare = variable
   )?
   (
      PROPOSAL p1 = variable
      (
         p2 = variable
      )?
   )?
;

sikegw_cert
:
   CERT PEER_CA ALL
;

sikegw_nat
:
   NAT_TRAVERSAL
   (
      KEEPALIVE_FREQUENCY kfreq = DEC
      | UDP_CHECKSUM
   )?
;

sike_proposal
:
   (
      P1_PROPOSAL
      | P2_PROPOSAL
   )
   name = variable
   (
      PRESHARE
      | RSA
   )?
   (
      GROUP1
      | GROUP2
      | GROUP5
      | GROUP14
   )
   ESP?
   (
      AES128
      | DES
      | THREEDES
   )
   (
      MD5
      | SHA_1
   )
   (
      SECOND second = DEC
   )
;

sike_null
:
   (
      DOS_PROTECTION
      | IKEID_ENUMERATION
      | IKEV2
      | POLICY_CHECKING
      | RESPOND_BAD_SPI
   ) (~NEWLINE)*
;


s_vpn
:
   VPN name = variable
   (
      svpn_bind
      | svpn_gateway
      | svpn_null
      | svpn_proxy
   )
;

svpn_bind
:
   (
      (
         ID id = HEX
         BIND INTERFACE iface = interface_name
      )
      |
      (
         BIND ZONE zone = variable
      )
   )
;

svpn_gateway
:
   GATEWAY name = variable
   (
      NO_REPLAY
      | REPLAY
   )
   (
      TUNNEL IDLETIME idletime = DEC
   )
   (
      PROPOSAL p1 = variable
      (
         p2 = variable
      )?
   )
;

svpn_proxy
:
   PROXY_ID
   (
      CHECK
      | 
      (
         LOCAL_IP localip = IP_PREFIX
         REMOTE_IP remoteip = IP_PREFIX
         service = variable
      )
   )
;

svpn_null
:
   (
      DSCP_MARK
      | MONITOR
   ) (~NEWLINE)*
;


