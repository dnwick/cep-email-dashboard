package org.wso2.cep.email.monitor.query.api.conditions;


public class AndCondition extends Condition {



    public AndCondition() {

    }

    public AndCondition(ConditionAttribute left, ConditionAttribute right) {
     setRight(right);
     setLeft(left);
    }




    @Override
    public String toString() {
        return "("+getLeft().toString() + "and" + getRight().toString()+")";
    }
}
