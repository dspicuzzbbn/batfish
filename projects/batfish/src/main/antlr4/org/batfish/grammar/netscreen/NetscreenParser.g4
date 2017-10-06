parser grammar NetscreenParser;

import Netscreen_common, Netscreen_interface, Netscreen_policy, Netscreen_vrouter;

options {
   superClass = 'org.batfish.grammar.BatfishParser';
   tokenVocab = NetscreenLexer;
}

netscreen_configuration
:
   statement+ EOF
;

protocol
:
   ICMP
   | IGMP
   | TCP
   | UDP
;

statement
:
   (
      (
         (
            SET
            | UNSET
         )
         (
            s_address
            | s_group
            | s_hostname
            | s_interface
            | s_mcast_policy
            | s_null
            | s_policy
            | s_service
            | s_url
            | s_usergroup
            | s_vrouter
            | s_zone
         )
      )
   ) NEWLINE
;

s_address
:
   ADDRESS
   zoneName = variable
   addrname = variable
   (
      address = IP_ADDRESS netmask = IP_ADDRESS
      | ipv6_prefix = IPV6_PREFIX
      | sa_fqdn
   )
   (
      description = variable
   )?
;

sa_fqdn
:
   (
      STAR
      | variable
   )
   (
      PERIOD variable
   )+
;

s_group
:
   GROUP
   (
      sg_address
      | sg_service
   )
   (
      COMMENT variable
   )?
;

s_hostname
:
    HOSTNAME variable
;

s_mcast_policy
:
   MULTICAST_GROUP_POLICY (~NEWLINE)+
;

s_null
:
   (
      CLOCK
      | ADD_DEFAULT_ROUTE
      | ALG
      | ARP
      | ATTACK
      | AUTH
      | AUTH_SERVER
      | AUTO_ROUTE_EXPORT
      | ADMIN
      | CONFIG
      | CONSOLE
      | 
      (
         CRYPTO_POLICY NEWLINE EXIT
      )
      | DBUF
      | DOMAIN
      | DNS
      | FLOW
      | IKE
      | IPSEC
      | KEY
      | LICENSE_KEY
      | LOG
      | NSMGMT
      | NSRP
      | NTP
      | PKI
      | PROTOCOL
      | SCHEDULER
      | SCP
      | SNMP
      | SNMPV3
      | SSH
      | SSL
      | SYSLOG
      | TELNET
      | TFTP
      | USER_GROUP
      | VPN
      | WEBAUTH
   ) (~NEWLINE)*
;


s_service
:
   SERVICE name = variable
   (
      ss_details
      | ss_timeout
   )
;

s_url
:
   URL PROTOCOL name = variable NEWLINE EXIT
;

s_usergroup
:
   USER_GROUP zonename = variable groupname = variable
   (
      ADD address = variable
   )?
;

s_zone
:
   ZONE
   (
      sz_id
      | sz_name
   )
;

sg_address
:
   ADDRESS zonename = variable groupname = variable
   (
      ADD address = variable
   )?
;

sg_service
:
   SERVICE name = variable
   (
      ADD address = variable
   )?
;

ss_details
:
   (
      PROTOCOL
      | PLUS
   )
   (
      proto = protocol
      | protoNum = DEC
   )
   SRC_PORT src_ports = port_range
   DST_PORT dst_ports = port_range
;

ss_timeout
:
   TIMEOUT timeout = DEC
;

su_null
:
   (
      SET
      | UNSET
   )
   (
      CACHE
      | CATEGORY
      | DENY_MESSAGE
      | ENABLE
      | PROFILE
      | USER_GROUP
   )
   ~NEWLINE* NEWLINE
;

sz_id
:
   ID id = DEC name = variable
;

sz_name
:
   name = variable
   (
      BLOCK
      |
      (
         SCREEN sname = variable
      )
      | TCP_RST
      |
      (
         VROUTER vname = variable
      )
   )
;
