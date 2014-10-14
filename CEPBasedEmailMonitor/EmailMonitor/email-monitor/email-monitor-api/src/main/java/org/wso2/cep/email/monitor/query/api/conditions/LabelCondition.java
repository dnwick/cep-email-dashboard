package org.wso2.cep.email.monitor.query.api.conditions;




public class LabelCondition extends Condition {




    @Override
    public String toString() {
        return "label = " + getLabelSet().toString();
    }

}
