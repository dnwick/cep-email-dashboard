package org.wso2.cep.email.monitor.query.compiler.internal;


import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.wso2.cep.email.monitor.query.api.Query;
import org.wso2.cep.email.monitor.query.compiler.emailMonitorGrammerWalker;
import org.wso2.cep.email.monitor.query.compiler.emailMonitorLexer;
import org.wso2.cep.email.monitor.query.compiler.emailMonitorParser;
import org.wso2.cep.email.monitor.query.compiler.exeception.EmailMonitorCompilerException;


public class EmailMonitorCompiler {




    public static Query parse(String source) throws EmailMonitorCompilerException {
        try {
            emailMonitorLexer lexer = new emailMonitorLexer();
            lexer.setCharStream(new ANTLRStringStream(source));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            emailMonitorParser parser = new emailMonitorParser(tokens);

            emailMonitorParser.prog_return r = parser.prog();

            CommonTree t = (CommonTree) r.getTree();

            CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
            nodes.setTokenStream(tokens);
            emailMonitorGrammerWalker walker = new emailMonitorGrammerWalker(nodes);
            return walker.prog().query;
        } catch (Throwable e) {
            throw new EmailMonitorCompilerException(e.getMessage(), e);
        }
    }
}
