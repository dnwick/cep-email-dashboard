package org.wso2.cep.email.monitor.query.api.conditions;


public class ORCondition extends Condition {



    public ORCondition() {

    }

    public ORCondition(ConditionAttribute left, ConditionAttribute right) {
       setLeft(left);
        setRight(right);
    }


    @Override
    public String toString() {
        return getLeft().toString() + "and" + getRight().toString();
    }

}
