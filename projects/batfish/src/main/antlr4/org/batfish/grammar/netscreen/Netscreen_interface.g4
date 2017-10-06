parser grammar Netscreen_interface;

import Netscreen_common;

options {
   tokenVocab = NetscreenLexer;
}


s_interface
:
   INTERFACE name = interface_name
   (
      si_bandwidth
      | si_disable
      | si_dip
      | si_ext
      | si_gateway
      | si_ip
      | si_ipv6
      | si_manageip
      | si_mip
      | si_mtu
      | si_nat
      | si_null
      | si_protocol
      | si_route
      | si_tunnel
      | si_zone
   )
;

si_bandwidth
:
   BANDWIDTH 
   EGRESS MBW embw = DEC 
   INGRESS MBW imbw = DEC
;

si_disable
:
   DISABLE
;

si_dip
:
   DIP dip_id = DEC startaddr = IP_ADDRESS endaddr = IP_ADDRESS
;

si_ext
:
   EXT IP addr = IP_ADDRESS mask = IP_ADDRESS DIP DEC addr2 = IP_ADDRESS mask2 = IP_ADDRESS
;

si_gateway
:
   GATEWAY ip = IP_ADDRESS
;

si_mtu
:
   MTU mtu = DEC
;

si_ip
:
   IP siip_options?
;

siip_ip
:
   ip = IP_ADDRESS mask = IP_ADDRESS
   (
      SECONDARY
   )?
;

siip_options
:
   (
      siip_ip
      | IP_PREFIX
      | MANAGEABLE
      | UNNUMBERED
   )
   (
      siip_tunnel
   )?
;

siip_tunnel
:
   INTERFACE iface = interface_name
;

si_ipv6
:
   IPV6
   (
      si6_mode
      | si6_nd
      | si6_ra
      | DISABLE
      | ENABLE
      | IP? IPV6_PREFIX
   )?
;

si_manageip
:
   MANAGE_IP address = IP_ADDRESS
;

si_mip
:
   MIP address = IP_ADDRESS
   HOST host = IP_ADDRESS
   NETMASK mask = IP_ADDRESS
   VR variable
;

si_nat
:
    NAT
;

si_null
:
   (
      BYPASS_ICMPV6_MLD
      | BYPASS_ICMPV6_MRD
      | BYPASS_ICMPV6_MSP
      | BYPASS_ICMPV6_NDP
      | BYPASS_ICMPV6_SND
      | BYPASS_IPV6_OTHERS_IPSEC
      | BYPASS_NON_IP
      | BYPASS_OTHERS_IPSEC
      | DHCP
      | DHCP6
      | MANAGE
      | WEBAUTH
      | WEBAUTH_IP
   ) (~NEWLINE)*
;

si_protocol
:
   PROTOCOL
   (
      sip_igmp
      | sip_ospf
      | sip_pim
   )
;

si_route
:
   ROUTE
;

si_tunnel
:
   TUNNEL ENCAP cap = variable
;

si_zone
:
   (
      TAG tag = DEC
   )?
   ZONE zone = variable
;

sip_igmp
:
   IGMP
   (
      ENABLE
      | ROUTER
      | ACCEPT HOSTS hosts = DEC
      | ACCEPT GROUPS groups = DEC
   )   
;

sip_ospf
:
   OSPF
   (
      AREA addr = IP_ADDRESS
      | AUTHENTICATION (~NEWLINE)*
      | COST cost = DEC
      | ENABLE 
   )
;

sip_pim
:
   PIM
   (
      BOOT_STRAP_BORDER
      | ENABLE
      | NEIGHBOR_POLICY pol = DEC
      
   )?
;

si6_mode
:
   MODE name = variable
;

si6_nd
:
   ND
   (
      ( BASE_REACHABLE_TIME base_reachable_time = DEC )
      | ( DAD_COUNT dad_count = DEC )
      | NUD
      | ( PROBE_TIME probe_time = DEC )
      | ( RETRANSMIT_TIME retransmit_time = DEC )
   )
;

si6_ra
:
   RA
   (
      LINK_ADDRESS
      | MANAGED
      | OTHER
      | ( PREFERENCE variable )
      | TRANSMIT
   )
;