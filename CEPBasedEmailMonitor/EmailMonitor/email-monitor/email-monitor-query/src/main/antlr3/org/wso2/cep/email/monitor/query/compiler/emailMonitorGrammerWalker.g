tree grammar emailMonitorGrammerWalker;

options {
    backtrack=true;
    tokenVocab=emailMonitor;
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
}
 
 
 
@header {
package org.wso2.cep.email.monitor.query.compiler;

    import org.wso2.cep.email.monitor.query.api.Query;
    import org.wso2.cep.email.monitor.query.api.actions.Action;
    import org.wso2.cep.email.monitor.query.api.actions.AddLabel;
     import org.wso2.cep.email.monitor.query.api.actions.SendMail;

  import org.wso2.cep.email.monitor.query.api.attribute.EmailAddress;
  import org.wso2.cep.email.monitor.query.api.attribute.Label;

  import org.wso2.cep.email.monitor.query.api.attribute.set.EmailAddressSet;
  import org.wso2.cep.email.monitor.query.api.attribute.set.LabelSet;

  import org.wso2.cep.email.monitor.query.api.expressions.CompareVal;
  import org.wso2.cep.email.monitor.query.api.expressions.TimeExpr;

  import org.wso2.cep.email.monitor.query.api.utils.Constants;

import org.wso2.cep.email.monitor.query.api.operators.Operator;
import org.wso2.cep.email.monitor.query.api.operators.EqualOP;
import org.wso2.cep.email.monitor.query.api.operators.GreaterThanOP;
import org.wso2.cep.email.monitor.query.api.operators.GreaterThanOREqualOP;
import org.wso2.cep.email.monitor.query.api.operators.LessThanOP;
import org.wso2.cep.email.monitor.query.api.operators.LessThanOREqualOP;
import org.wso2.cep.email.monitor.query.api.operators.NOTEqualOP;


  import org.wso2.cep.email.monitor.query.api.conditions.AndCondition;
  import org.wso2.cep.email.monitor.query.api.conditions.Condition;
  import org.wso2.cep.email.monitor.query.api.conditions.ConditionAttribute;
  import org.wso2.cep.email.monitor.query.api.conditions.FrequencyCondition;
  import org.wso2.cep.email.monitor.query.api.conditions.FromCondition;
  import org.wso2.cep.email.monitor.query.api.conditions.LabelToCondition;
  import org.wso2.cep.email.monitor.query.api.conditions.LabelCondition;
  import org.wso2.cep.email.monitor.query.api.conditions.LabelFromToCondition;
  import org.wso2.cep.email.monitor.query.api.conditions.ORCondition;
  import org.wso2.cep.email.monitor.query.api.conditions.ToCondition;
}


prog returns [Query query]
@init{
             $query = new Query();
}
:^(EMAIL_PRO conditions{
$query.setConditionAttribute($conditions.condition);}action{
$query.setAction($action.act);})
;



conditions returns [ConditionAttribute condition]
@init{
$condition=null;
}
: frequencyCondition{$condition = $frequencyCondition.freqCond;}
    | labelFromToCondition{$condition=new AndCondition();
            $condition.setLeft($labelFromToCondition.labelFromTo);

      } 'and' frequencyCondition{
    $condition.setRight($frequencyCondition.freqCond);

}
    |labelFromToCondition{$condition=new ORCondition();

           $condition.setLeft($labelFromToCondition.labelFromTo);
         } 'or' frequencyCondition{
                   $condition.setRight($frequencyCondition.freqCond);

               }
    | labelFromToCondition{$condition= $labelFromToCondition.labelFromTo;}
;



labelFromToCondition returns [ConditionAttribute labelFromTo]
  @init{
      $labelFromTo=null;
  }
    : fromCondition{$labelFromTo= $fromCondition.fromCond;}

    | labelToCondition{$labelFromTo= new AndCondition();
         $labelFromTo.setLeft($labelToCondition.lbToCondition);
     } 'and'  fromCondition{
          $labelFromTo.setRight($fromCondition.fromCond);

     }
    | labelToCondition{$labelFromTo= new ORCondition();
                             $labelFromTo.setLeft($labelToCondition.lbToCondition);
                         } 'or' ^fromCondition{
                                                                $labelFromTo.setRight($fromCondition.fromCond);

                                                           }
    | labelToCondition{$labelFromTo= $labelToCondition.lbToCondition;}
    ;




    
 labelToCondition returns [ConditionAttribute lbToCondition]
 @init{
    $lbToCondition = null;
 }
     : labelCondition{$lbToCondition = $labelCondition.lbCondition;}
     | toCondition{
           $lbToCondition = new AndCondition();
           $lbToCondition.setLeft($toCondition.to);

     } 'and'labelCondition{
           $lbToCondition.setRight($labelCondition.lbCondition);
     }
     | toCondition {
                              $lbToCondition = new ORCondition();
                              $lbToCondition.setLeft($toCondition.to);

                        } 'or' labelCondition{
                              $lbToCondition.setRight($labelCondition.lbCondition);
                        }
     | toCondition{$lbToCondition=$toCondition.to;}
     ;




    
action returns [Action act]
    : ^(LBL stringVal{$act = new AddLabel($stringVal.val);})
    | 'send' 'mail' {$act = new SendMail();} '(' 'to' ':' emailAddr{$act.setEmailAddress($emailAddr.email);} 'subject' ':' stringVal{$act.setSubject($stringVal.val);} ('body' ':' stringValBody{$act.setBody($stringValBody.body);})?  ')'
    ;


stringValBody returns[String body]

     :stringVal{$body = $stringVal.val;}
     ;


toCondition returns [ConditionAttribute to]
@init{
$to= new ToCondition();
}
    :   'to' ':'	'('emailAddrSet{$to.setEmailAddressSet($emailAddrSet.emailSet);} ')';




labelCondition returns [ConditionAttribute lbCondition]
@init{
   $lbCondition=new LabelCondition();
}
 	: 'label' ':' '(' labelSet{$lbCondition.setLabelSet($labelSet.lbSet);} ')'
 	;




 fromCondition returns [ConditionAttribute fromCond]
 @init{
    $fromCond= new FromCondition();
 }
      :	'from' ':' '('emailAddrSet{$fromCond.setEmailAddressSet($emailAddrSet.emailSet);} ')'
      	;




 frequencyCondition returns [ConditionAttribute freqCond]
 @init{
     $freqCond= new FrequencyCondition();
     Operator operator=null;
     TimeExpr timeEx=null;
 }
 	:	 ^(FREQ_COND ('thread'{$freqCond.setType(Constants.THREAD);})?  compareOperation{
 	   operator= $compareOperation.oper;
 	   timeEx= new TimeExpr();
 	   operator.setLeft(timeEx);
 	$freqCond.setOperator(operator);
 	} intVal{operator.setRight(new CompareVal(Integer.parseInt($intVal.val)));} ('/d'{ timeEx.setDay(1);})? ('/h'{timeEx.setHour(1);})? )
 	|   'count' compareOperation{$freqCond.setType(Constants.COUNT);
                                 	   operator= $compareOperation.oper;

                                 	    	$freqCond.setOperator(operator);}
                                 	    	intVal{operator.setRight(new CompareVal(Integer.parseInt($intVal.val)));}
 	;




 timeExpr returns [TimeExpr timeEx]
 @init{
     $timeEx= new TimeExpr();
 }
 	: ^(TIME_EXP (yearValue{$timeEx.setYear(Integer.parseInt($yearValue.year));})? (monthValue{$timeEx.setMonth(Integer.parseInt($monthValue.month));})? (weekValue{$timeEx.setWeek(Integer.parseInt($weekValue.week));})? (dayValue{$timeEx.setDay(Integer.parseInt($dayValue.day));})? (hourValue{$timeEx.setHour(Integer.parseInt($hourValue.hour));})? (minuteValue{$timeEx.setMiniute(Integer.parseInt($minuteValue.minute));})? (secondValue{$timeEx.setSecond(Integer.parseInt($secondValue.second));})? (milliSecondValue{$timeEx.setMilisecond(Integer.parseInt($milliSecondValue.milisecond));})?  )
 	;
    



yearValue returns [String year]
	:  ^(YEAR intVal{$year=$intVal.val;})
	;




monthValue returns [String month]
	:  ^(MONTH  intVal{$month=$intVal.val;})
	;



weekValue returns [String week]
	: ^(WEEK  intVal{$week=$intVal.val;})
	;



dayValue returns [String day]
	:  ^(DAY  intVal{$day=$intVal.val;})
	;



hourValue returns [String hour]
	: ^(HOUR  intVal{$hour=$intVal.val;})
	;

minuteValue returns [String minute]
	: ^(MIN  intVal{$minute=$intVal.val;})
	;



secondValue returns[String second]
	:  ^(SEC  intVal{$second=$intVal.val;})
	;



milliSecondValue returns [String milisecond]
	:  ^(MILLI_SEC  intVal{$milisecond=$intVal.val;})
	;



labelSet returns [ConditionAttribute lbSet]
@init{
     $lbSet = null;
}
	: label{
	$lbSet= new LabelSet();
	$lbSet.setConditionAttribute($label.label);}
	| label{$lbSet= new AndCondition();
	$lbSet.setLeft($label.label);
	} 'and' labelSetRec{
	  $lbSet.setRight($labelSetRec.lbSetRec);
	}
    | label{$lbSet= new ORCondition();
           	$lbSet.setLeft($label.label);
           	} 'or' labelSetRec{
           	$lbSet.setRight($labelSetRec.lbSetRec);
           	}
	;

	labelSetRec returns[ConditionAttribute lbSetRec]

	:labelSet{$lbSetRec=$labelSet.lbSet;};


subject returns [String sub]
:stringVal{$sub=$stringVal.val;};

emailBody returns [String body]
:stringVal{$body=$stringVal.val;}
;







  emailAddrSet returns [ConditionAttribute  emailSet]
  @init{
    $emailSet= null;
  }
    	:  emailAddr {$emailSet = new EmailAddressSet();
    	    $emailSet.setConditionAttribute($emailAddr.email);
    	}
    	| emailAddr {
    	$emailSet= new AndCondition();
    	$emailSet.setLeft($emailAddr.email);



    	}'and' emailAddrSetRec{

    	$emailSet.setRight($emailAddrSetRec.emailSetRec);


    	}
    	| emailAddr{
                       	$emailSet= new ORCondition();
                       	$emailSet.setLeft($emailAddr.email);



                       	}'or' emailAddrSetRec{

                       	$emailSet.setRight($emailAddrSetRec.emailSetRec);


                       	}
    	;


    emailAddrSetRec returns[ConditionAttribute  emailSetRec]


    :emailAddrSet{$emailSetRec=$emailAddrSet.emailSet;};



compareOperation returns [Operator oper]
	:'='{$oper= new EqualOP();} |'!=' {$oper= new NOTEqualOP();}|'<='{$oper=new LessThanOREqualOP();}|'>='{$oper= new GreaterThanOREqualOP();} |'<' {$oper= new LessThanOP();} |'>' {$oper= new GreaterThanOP();}
	;



label 	returns [Label label]
   : stringVal {$label = new Label($stringVal.val);}
   ;




emailAddr returns [EmailAddress email]
   :  stringVal{$email= new EmailAddress($stringVal.val);}
   ;



stringVal returns [String val]

:(INT_VAL{$val=$INT_VAL.text;})? (ID{
if($val != null){
$val = $val+$ID.text;
}else{
$val = $ID.text;
}

})
 ;




intVal returns [String val]

	:	INT_VAL{$val = $INT_VAL.text;};


