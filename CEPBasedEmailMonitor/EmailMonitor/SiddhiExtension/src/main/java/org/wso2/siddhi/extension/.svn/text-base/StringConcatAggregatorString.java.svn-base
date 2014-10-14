package org.wso2.siddhi.extension;

import org.wso2.siddhi.core.query.selector.attribute.handler.OutputAttributeAggregator;
import org.wso2.siddhi.query.api.definition.Attribute.Type;

public class StringConcatAggregatorString implements OutputAttributeAggregator {
    private static final long serialVersionUID = 1358667438272544590L;
    private String aggregatedStringVlaue = "";

    @Override
    public Type getReturnType() {
        return Type.STRING;
    }


    @Override
    public Object processAdd(Object obj) {

        if (obj instanceof String) {

            String sender = (String) obj;
            aggregatedStringVlaue = aggregatedStringVlaue +sender;
        }
        return aggregatedStringVlaue;
    }


    @Override
    public Object processRemove(Object obj) {

        if (obj instanceof String) {

            String sender = (String)obj;
            aggregatedStringVlaue = aggregatedStringVlaue.replace(sender, "");
        }
        return aggregatedStringVlaue;
    }


    @Override
    public OutputAttributeAggregator newInstance() {
        return new StringConcatAggregatorString();
    }


    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}