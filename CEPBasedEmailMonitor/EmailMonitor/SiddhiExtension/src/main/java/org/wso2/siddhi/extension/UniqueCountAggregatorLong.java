package org.wso2.siddhi.extension;

import org.antlr.stringtemplate.language.ArrayWrappedInList;
import org.wso2.siddhi.core.query.selector.attribute.handler.OutputAttributeAggregator;
import org.wso2.siddhi.query.api.definition.Attribute;

import java.util.ArrayList;
import java.util.List;


public class UniqueCountAggregatorLong implements OutputAttributeAggregator {
    private static final long serialVersionUID = 1358997438272544590L;
    private long uniqueCount = 0;
    private List<Long> valuesCounted = new ArrayList<Long>();

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.LONG;
    }


    @Override
    public Object processAdd(Object obj) {

        if (obj instanceof Long) {

            long value = (Long)obj;
            if(!valuesCounted.contains(value)){
                  valuesCounted.add(value);
                  uniqueCount++;
            }
        }
        return uniqueCount;
    }


    @Override
    public Object processRemove(Object obj) {

        if (obj instanceof Long) {

            long value = (Long)obj;
            valuesCounted.remove(value);
            uniqueCount--;
        }
        return uniqueCount;
    }


    @Override
    public OutputAttributeAggregator newInstance() {
        return new UniqueCountAggregatorLong();
    }


    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
