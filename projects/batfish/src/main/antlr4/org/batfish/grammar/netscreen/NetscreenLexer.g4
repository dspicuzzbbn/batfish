lexer grammar NetscreenLexer;

options {
   superClass = 'org.batfish.grammar.BatfishLexer';
}

@members {
boolean enableIP_ADDRESS = true;
boolean enableIPV6_ADDRESS = true;

@Override
public String printStateVariables() {
   StringBuilder sb = new StringBuilder();
   sb.append("enableIP_ADDRESS: " + enableIP_ADDRESS + "\n");
   sb.append("enableIPV6_ADDRESS: " + enableIPV6_ADDRESS + "\n");
   return sb.toString();
}

}

tokens {
   QUOTED_TEXT
}

// Netscreen Keywords

ACCEPT
:
   'accept'
;

ACCESS_LIST
:
   'access-list'
;

ACCOUNTING
:
   'accounting'
;

ADD
:
   'add'
;

ADD_DEFAULT_ROUTE
:
   'add-default-route'
;

ADDRESS
:
   'address'
;

ADMIN
:
   'admin'
;

ADVERTISE_DEF_ROUTE
:
   'advertise-def-route'
;

ALG
:
   'alg'
;

APPLICATION
:
   'application'
;

AREA
:
   'area'
;

ARP
:
   'arp'
;

ATTACK
:
   'attack'
;

AUTH
:
   'auth'
;

AUTHENTICATION
:
   'authentication'
;

AUTH_SERVER
:
   'auth-server'
;

AUTO_ROUTE_EXPORT
:
   'auto-route-export'
;

BANDWIDTH
:
   'bandwidth'
;

BASE_REACHABLE_TIME
:
   'base-reachable-time'
;

BLOCK
:
   'block'
;

BOOT_STRAP_BORDER
:
   'boot-strap-border'
;

BYPASS_ICMPV6_MLD
:
   'bypass-icmpv6-mld'
;

BYPASS_ICMPV6_MRD
:
   'bypass-icmpv6-mrd'
;

BYPASS_ICMPV6_MSP
:
   'bypass-icmpv6-msp'
;

BYPASS_ICMPV6_NDP
:
   'bypass-icmpv6-ndp'
;

BYPASS_ICMPV6_SND
:
   'bypass-icmpv6-snd'
;

BYPASS_IPV6_OTHERS_IPSEC
:
   'bypass-ipv6-others-ipsec'
;

BYPASS_NON_IP
:
   'bypass-non-ip'
;

BYPASS_OTHERS_IPSEC
:
   'bypass-others-ipsec'
;

CACHE
:
   'cache'
;

CATEGORY
:
   'category'
;

CLOCK
:
   'clock'
;

COMMENT
:
   'comment'
;

CONFIG
:
   'config'
;

CONSOLE
:
   'console'
;

COST
:
   'cost'
;

CRYPTO_POLICY
:
   'crypto-policy'
;

DAD_COUNT
:
   'dad-count'
;

DBUF
:
   'dbuf'
;

DENY_MESSAGE
:
   'deny-message'
;

DESCRIPTION
:
   'description'
;

DEFAULT
:
   'default'
;

DEFAULT_ROUTE
:
   'default-route'
;

DENY
:
   'deny'
;

DHCP
:
   'dhcp'
;

DHCP6
:
   'dhcp6'
;

DHCPV6
:
   'dhcpv6'
;

DIP
:
   'dip'
;

DIP_ID
:
   'dip-id'
;

DISABLE
:
   'disable'
;

DOMAIN
:
   'domain'
;

DNS
:
   'dns'
;

DST
:
   'dst'
;

DST_ADDRESS
:
   'dst-address'
;

DST_PORT
:
   'dst-port'
;

EGRESS
:
   'egress'
;

ENABLE
:
   'enable'
;

ENCAP
:
   'encap'
;

EXIT
:
   'exit'
;

EXT
:
   'ext'
;


FLOW
:
   'flow'
;

FORMAT
:
   'format'
;

FROM
:
   'from'
;

GATEWAY
:
   'gateway'
;

GBW
:
   'gbw'
;

GROUP
:
   'group'
;

GROUPS
:
   'groups'
;

HOSTS
:
   'hosts'
;

HOST
:
   'host'
;

HOSTNAME
:
   'hostname'
;

ICMP
:
   'icmp'
;

ID
:
   'id'
;

IGMP
:
   'igmp'
;

IKE
:
   'ike'
;

INGRESS
:
   'ingress'
;

INTERFACE
:
   'interface'
;

IP
:
   'ip'
;

IPV6
:
   'ipv6'
;

IPSEC
:
   'ipsec'
;

KEY
:
   'key'
;

LICENSE_KEY
:
   'license-key'
;

LINK_ADDRESS
:
   'link-address'
;

LOG
:
   'log'
;

MAX_NEIGHBOR_COUNT
:
   'max-neighbor-count'
;

MANAGE
:
   'manage'
;

MANAGED
:
   'managed'
;

MANAGE_IP
:
   'manage-ip'
;

MANAGEABLE
:
   'manageable'
;

MATCH
:
   'match'
;

MAX_ENTRIES
:
   'max-entries'
;

MBW
:
   'mbw'
;

METRIC
:
   'metric'
;

METRIC_TYPE
:
   'metric-type'
;

MIP
:
   'mip'
;

MODE
:
   'mode'
;

MROUTE
:
   'mroute'
;

MTU
:
   'mtu'
;

MULTICAST_GROUP_POLICY
:
   'multicast-group-policy'
;

NAME
:
   'name'
;

NAT
:
   'nat'
;

ND
:
   'nd'
;

NEIGHBOR
:
   'neighbor'
;

NEIGHBOR_POLICY
:
   'neighbor-policy'
;

NETMASK
:
   'netmask'
;

NONE
:
   'none'
;

NSMGMT
:
   'nsmgmt'
;

NSRP
:
   'nsrp'
;

NTP
:
   'ntp'
;

NUD
:
   'nud'
;

NULL
:
   'null'
;

OSPF
:
   'ospf'
;

OTHER
:
   'other'
;

PAIR_POLICY
:
   'pair-policy'
;

PASSWORD
:
   'password'
;

PBW
:
   'pbw'
;

PERMIT
:
   'permit'
;

PIM
:
   'pim'
;

PKI
:
   'pki'
;

POLICY
:
   'policy'
;

PORT
:
   'port'
;

PRIORITY
:
   'priority'
;

PREFERENCE
:
   'preference'
;

PROBE_TIME
:
   'probe-time'
;

PROFILE
:
   'profile'
;

PROTOCOL
:
   'protocol'
;

RA
:
   'ra'
;

RADIUS
:
   'radius'
;

REDISTRIBUTE
:
   'redistribute'
;

RETRANSMIT_TIME
:
   'retransmit-time'
;

ROUTE
:
   'route'
;

ROUTE_MAP
:
   'route-map'
;

ROUTER
:
   'router'
;

ROUTER_ID
:
   'router-id'
;

SCHEDULER
:
   'scheduler'
;

SCREEN
:
   'screen'
;

SCP
:
   'scp'
;

SECONDARY
:
   'secondary'
;

SERVER
:
   'server'
;

SERVER_NAME
:
   'server-name'
;

SERVICE
:
   'service'
;

SET
:
   'set'
;

SHARABLE
:
   'sharable'
;

SNMP
:
   'snmp'
;

SNMPV3
:
   'snmpv3'
;

SRC
:
   'src'
;

SRC_ADDRESS
:
   'src-address'
;

SRC_PORT
:
   'src-port'
;

SSH
:
   'ssh'
;

SSL
:
   'ssl'
;

STATIC
:
   'static'
;

SYSLOG
:
   'syslog'
;

TAG
:
   'tag'
;

TCP
:
   'tcp'
;

TCP_RST
:
   'tcp-rst'
;

TELNET
:
   'telnet'
;

TFTP
:
   'tftp'
;

TIMEOUT
:
   'timeout'
;

TIMEZONE
:
   'timezone'
;

TO
:
   'to'
;

TRAFFIC
:
   'traffic'
;

TRANSMIT
:
   'transmit'
;

TRUSTED_NEIGHBORS
:
   'trusted-neighbors'
;

TUNNEL
:
   'tunnel'
;

UDP
:
   'udp'
;

UNNUMBERED
:
   'unnumbered'
;

UNSET
:
   'unset'
;

URL
:
   'url'
;

USER_GROUP
:
   'user-group'
;

VPN
:
   'vpn'
;

VR
:
   'vr'
;

VROUTER
:
   'vrouter'
;

WEBAUTH
:
   'webauth'
;

WEBAUTH_IP
:
   'webauth-ip'
;

ZONE
:
   'zone'
;

VARIABLE
:
   (
      'a' .. 'z'
      | 'A' .. 'Z'
   )
   (
      '-'
      | '_'
      | '0' .. '9'
      | 'a' .. 'z'
      | 'A' .. 'Z'
   )*
   (
      'a' .. 'z'
      | 'A' .. 'Z'
      | '0' .. '9'
   )
;


// Other tokens

COLON
:
   ':'
;

DASH
:
   '-'
;

DEC
:
   F_Digit+
;

DOUBLE_QUOTE
:
   '"' -> pushMode ( M_QuotedString )
;

FORWARD_SLASH
:
   '/'
;

HEX
:
   '0x' F_HexDigit+
;

IP_ADDRESS
:
   F_DecByte '.'
   {enableIP_ADDRESS}?

   F_DecByte '.' F_DecByte '.' F_DecByte
;

IP_PREFIX
:
   F_DecByte '.'
   {enableIP_ADDRESS}?

   F_DecByte '.' F_DecByte '.' F_DecByte '/' F_Digit F_Digit?
;

IPV6_ADDRESS
:
   (
      (
         ':'
         {enableIPV6_ADDRESS}?

         ':'
         (
            (
               F_HexDigit+ ':'
            )* F_HexDigit+
         )?
      )
      |
      (
         (
            F_HexDigit+
            {enableIPV6_ADDRESS}?

            ':' ':'?
         )+ F_HexDigit*
      )
   )
   (
      ':' F_DecByte '.' F_DecByte '.' F_DecByte '.' F_DecByte
   )?
;

IPV6_PREFIX
:
   (
      (
         ':'
         {enableIPV6_ADDRESS}?

         ':'
         (
            (
               F_HexDigit+ ':'
            )* F_HexDigit+
         )?
      )
      |
      (
         (
            F_HexDigit+
            {enableIPV6_ADDRESS}?

            ':' ':'?
         )+ F_HexDigit*
      )
   )
   (
      ':' F_DecByte '.' F_DecByte '.' F_DecByte '.' F_DecByte
   )? '/' F_DecByte
;

NEWLINE
:
   F_Newline+
   {
      enableIP_ADDRESS = true;
      enableIPV6_ADDRESS = true;
   }
;

PERIOD
:
   '.'
;

PLUS
:
   '+'
;

STAR
:
   '*'
;

WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

fragment
F_DecByte
:
   (
      F_PositiveDigit F_Digit F_Digit
   )
   |
   (
      F_PositiveDigit F_Digit
   )
   | F_Digit
;

fragment
F_Digit
:
   '0' .. '9'
;

fragment
F_HexDigit
:
   (
      '0' .. '9'
      | 'a' .. 'f'
      | 'A' .. 'F'
   )
;

fragment
F_Newline
:
   [\r\n]+
;

fragment
F_NonNewline
:
   ~[\r\n]
;

fragment
F_PositiveDigit
:
   '1' .. '9'
;

fragment
F_Whitespace
:
   [ \t\u000C]
;

mode M_QuotedString;

M_QuotedString_QUOTED_TEXT
:
   ~'"'+ -> type ( QUOTED_TEXT )
;

M_QuotedString_DOUBLE_QUOTE
:
   '"' -> type ( DOUBLE_QUOTE ) , popMode
;
