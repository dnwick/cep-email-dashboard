grammar emailMonitor ;

options {
  language = Java;
  backtrack=true;
  output    = AST;
  ASTLabelType=CommonTree;

}

tokens {
  EMAIL_PRO;
  TIME_EXP;
  FREQ_COND;
  YEAR;
   MONTH;
  WEEK;
  DAY;
  HOUR;
  MIN;
  SEC;
  MILLI_SEC;
  LBL;
  TO;
  ACT;
  ST;
  AND;
}

@header {
package org.wso2.cep.email.monitor.query.compiler;
import java.util.HashMap;
}

@lexer::header {package org.wso2.cep.email.monitor.query.compiler;}

@members {
/** Map variable name to Integer object holding value */
HashMap memory = new HashMap();
}

prog:       conditions    action  ->^(EMAIL_PRO conditions action);
                
conditions:   frequencyCondition
     | labelFromToCondition  'and'  frequencyCondition
     | labelFromToCondition 'or' frequencyCondition
     | labelFromToCondition
     ;



labelFromToCondition
    : fromCondition
    | labelToCondition 'and' fromCondition
    | labelToCondition 'or' fromCondition
    | labelToCondition
    ;
    
    
labelToCondition
    : labelCondition
    | toCondition 'and'labelCondition
    | toCondition 'or' labelCondition
    | toCondition
    ;
    
action 
    :   'add' 'label' stringVal -> ^(LBL stringVal)
    | 'send' 'mail' '(' 'to' ':' emailAddr 'subject' ':' stringVal ('body' ':' stringValBody)?  ')'
    ;

stringValBody
     :stringVal
     ;
    
toCondition 
    :   'to'  ':'	'('emailAddrSet ')';
    
labelCondition
 	: 'label' ':'  '('labelSet')'
 	;
 	
 fromCondition
      :	'from' ':' '('emailAddrSet')'
      	;
      	
 frequencyCondition
 	:	('thread')?  'frequency'  compareOperation intVal ('/d')? ('/h')? -> ^(FREQ_COND 'thread'?  compareOperation intVal ('/d')? ('/h')?)
 	|   'count' compareOperation intVal
 	;
 	
 timeExpr 
 	:(yearValue)? (monthValue)? (weekValue)? (dayValue)? (hourValue)? (minuteValue)? (secondValue)?  (milliSecondValue)?	
 	-> ^(TIME_EXP yearValue? monthValue? weekValue? dayValue? hourValue? minuteValue? secondValue? milliSecondValue?  )
 	;
    

yearValue
	: intVal ( 'years' | 'year')  ->  ^(YEAR intVal)
	;
	

monthValue
	: intVal ( 'months' | 'month')  -> ^(MONTH  intVal)
	;

weekValue
	: intVal ( 'weeks' | 'week')    -> ^(WEEK  intVal)
	;

dayValue
	: intVal ( 'days' | 'day')    -> ^(DAY  intVal)
	;

hourValue
	: intVal ( 'hours' |   'hour' )   -> ^(HOUR  intVal)
	;

minuteValue
	: intVal ( 'minutes' |  'min' | 'minute'  )    -> ^(MIN  intVal)
	;

secondValue
	: intVal ('seconds' | 'second' | 'sec'  )   -> ^(SEC  intVal)
	;

milliSecondValue
	: intVal ( 'milliseconds' |  'millisecond'  )    -> ^(MILLI_SEC  intVal)
	;

labelSet
	: label
	| label 'and' labelSetRec
    	| label 'or' labelSetRec
	;

labelSetRec:labelSet;

subject : stringVal ;

emailBody : stringVal;

  emailAddrSet
    	:  emailAddr
    	| emailAddr 'and' emailAddrSetRec
    	| emailAddr 'or' emailAddrSetRec
    	;


   emailAddrSetRec:emailAddrSet;


compareOperation
	:'=' |'!=' |'<='|'>=' |'<' |'>'  
	;

label 	
   : stringVal
   ;


emailAddr 
   :  stringVal
   ;

stringVal:INT_VAL? ID ;


intVal 	:	INT_VAL;

ID  :  ('a'..'z'|'A'..'Z'|'_')  ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'@'|'.')* ;

STRING_VAL
	:'\'' ( ~('\u0000'..'\u001f' | '\\' | '\''| '\"' ) )* '\'' {setText(getText().substring(1, getText().length()-1));}
	|'"' ( ~('\u0000'..'\u001f' | '\\'  |'\"') )* '"'          {setText(getText().substring(1, getText().length()-1));}
	;	

INT_VAL :   '0'..'9'+ ;

WS  : (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;};

