parser grammar Netscreen_common;

options {
   tokenVocab = NetscreenLexer;
}

interface_name
:
   (
      DOUBLE_QUOTE QUOTED_TEXT DOUBLE_QUOTE
   )
   |
   (
      ~NEWLINE
      (
         (
            FORWARD_SLASH
            | PERIOD
         ) DEC
      )*   
   )
;

port_range
:
   DEC DASH DEC
;

variable
:
   (
      (
         DOUBLE_QUOTE QUOTED_TEXT DOUBLE_QUOTE
      )
      | ~NEWLINE
   )
;
