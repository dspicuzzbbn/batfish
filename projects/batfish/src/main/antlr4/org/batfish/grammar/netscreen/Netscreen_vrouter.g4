parser grammar Netscreen_vrouter;

import Netscreen_common;

options {
   tokenVocab = NetscreenLexer;
}

s_vrouter
:
   VROUTER variable 
   (
      SHARABLE
      | ( NEWLINE sv_statement )* NEWLINE EXIT
   )
;

sv_statement
:
   (
      SET
      | UNSET
   )
   (
      sv_accesslist
      | sv_mroute
      | sv_options
      | sv_protocol
      | sv_route
      | sv_routemap
      | sv_routerid
   )
;

sv_accesslist
:
   ACCESS_LIST id = DEC
   (
      PERMIT
      (
        DEFAULT_ROUTE
        | IP ip = IP_PREFIX
      )
      idx = DEC
   )?
;

sv_mroute
:
   MROUTE MAX_ENTRIES me = DEC
;

sv_options
:
   ADD_DEFAULT_ROUTE
   | AUTO_ROUTE_EXPORT
;

sv_protocol
:
   PROTOCOL name = variable
   (
      ( NEWLINE svp_statement )*
   )
   NEWLINE EXIT
;

sv_redistribute
:
   REDISTRIBUTE ROUTE_MAP rm = variable PROTOCOL proto = variable
;

sv_route
:
   ROUTE
   (
      ipv4 = IP_PREFIX
      | ipv6 = IPV6_PREFIX
   )
   (
      INTERFACE
      (
         NULL 
         | name = interface_name
      )
   )?
   (
      VROUTER vrouter = variable
   )?
   (
      GATEWAY
      (
         gw_ipv4 = IP_ADDRESS
         | gw_ipv6 = IPV6_ADDRESS
      )
   )?
   (
      PREFERENCE pref = DEC
   )?
   (
      METRIC m = DEC
   )?
   (
      DESCRIPTION desc = variable
   )?
;

sv_routemap
:
   ROUTE_MAP NAME name = variable 
   (
      PERMIT index = DEC
      (
         ( NEWLINE svr_match )?
      )
      
      NEWLINE EXIT
   )?
;

sv_routerid
:
   ROUTER_ID name = variable
;

svp_statement
:
   (
      SET
      | UNSET
   )
   (
      ENABLE
      | svp_null
   )
;

svp_null
:
   (
      ADVERTISE_DEF_ROUTE
      | MAX_NEIGHBOR_COUNT
      | NEIGHBOR
      | REDISTRIBUTE
      | TRUSTED_NEIGHBORS
   )
   ~NEWLINE*
;

svr_match
:
   SET MATCH IP match = DEC
;
