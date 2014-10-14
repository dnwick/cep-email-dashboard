package org.wso2.cep.email.monitor.query.api.conditions;

import org.wso2.cep.email.monitor.query.api.utils.Constants;

public class FrequencyCondition extends Condition {

    @Override
    public String toString() {
        if (getType().equals(Constants.THREAD)) {
            return "thread frequency per " + getOperator().getLeft().toString()+" " +getOperator().toString() +" "+ getOperator().getRight().toString() ;
        } else {
            return "frequency per " + getOperator().getLeft().toString()+" " +getOperator().toString() +" "+ getOperator().getRight().toString() ;
        }
    }
}
