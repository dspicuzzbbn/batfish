parser grammar Netscreen_policy;

import Netscreen_common;

options {
   tokenVocab = NetscreenLexer;
}

s_policy
:
   POLICY ID id = DEC
   (
      DISABLE
      | sp_application
      | sp_config
      | sp_statements
   )
;

sp_application
:
   APPLICATION name = variable
;

sp_config
:
   ( 
      NAME name = variable
   )?
   (
      FROM fromzone = variable
      TO tozone = variable
      srcaddress = variable
      dstaddress = variable
      service = variable
      ( spc_nat )?
      (
         spc_tunnel
         | DENY
         | PERMIT
      )
      WEBAUTH?
      LOG?
      spc_traffic?
   )
;

sp_statements
:
   ( NEWLINE sp_statement )* NEWLINE EXIT
;

sp_statement
:
   SET
   (
      sp_dstaddr
      | sp_null
      | sp_service
      | sp_srcaddr
   )
;

sp_dstaddr
:
   DST_ADDRESS name = variable
;

sp_null
:
   (
      LOG
   ) (~NEWLINE)*
;

sp_service
:
   SERVICE name = variable
;

sp_srcaddr
:
   SRC_ADDRESS name = variable
;

spc_nat
:
   NAT
   (
      DST
      | SRC
   )
   (
      DIP_ID dip_id = DEC
      | IP ip = IP_ADDRESS
   )?
;

spc_traffic
:
   TRAFFIC
   (PRIORITY priority = DEC)?
   (GBW gbw = DEC)?
   (
      (MBW mbw = DEC)
      | (PBW pbw = DEC)
   )?
;

spc_tunnel
:
   TUNNEL
   VPN vpnName = variable
   (
      ID id = HEX
   )?
   (
      PAIR_POLICY pp = DEC
   )?
;


