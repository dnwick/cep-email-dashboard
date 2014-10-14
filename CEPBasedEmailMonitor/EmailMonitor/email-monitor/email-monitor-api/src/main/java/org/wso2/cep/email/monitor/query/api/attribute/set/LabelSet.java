package org.wso2.cep.email.monitor.query.api.attribute.set;


import org.wso2.cep.email.monitor.query.api.attribute.Attribute;

public class LabelSet extends Attribute{

    public LabelSet() {
    }

    @Override
    public String toString() {
     return    getConditionAttribute().toString();
    }
}
