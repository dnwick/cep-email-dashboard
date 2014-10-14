package org.wso2.siddhi.extension;
import org.wso2.siddhi.core.query.selector.attribute.factory.OutputAttributeAggregatorFactory;
import org.wso2.siddhi.core.query.selector.attribute.handler.OutputAttributeAggregator;
import org.wso2.siddhi.query.api.definition.Attribute.Type;
import org.wso2.siddhi.query.api.extension.annotation.SiddhiExtension;

@SiddhiExtension(namespace = "email", function = "getAll")
public class StringConcatAggregatorFactory implements OutputAttributeAggregatorFactory {
    @Override
    public OutputAttributeAggregator createAttributeAggregator(Type[] types) {
        return new StringConcatAggregatorString();
    }
}