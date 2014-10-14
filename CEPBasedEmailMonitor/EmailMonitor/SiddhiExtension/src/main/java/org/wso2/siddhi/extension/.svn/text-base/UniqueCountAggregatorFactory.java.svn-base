package org.wso2.siddhi.extension;

import org.wso2.siddhi.core.query.selector.attribute.factory.OutputAttributeAggregatorFactory;
import org.wso2.siddhi.core.query.selector.attribute.handler.OutputAttributeAggregator;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.extension.annotation.SiddhiExtension;


    @SiddhiExtension(namespace = "email", function = "getUniqueCount")
    public class UniqueCountAggregatorFactory implements OutputAttributeAggregatorFactory {
        @Override
        public OutputAttributeAggregator createAttributeAggregator(Attribute.Type[] types) {
            return new UniqueCountAggregatorLong();
        }
    }

