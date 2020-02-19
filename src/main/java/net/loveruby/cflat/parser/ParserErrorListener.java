package net.loveruby.cflat.parser;


import net.loveruby.cflat.parser.gen.CbcParser;
import org.antlr.v4.runtime.*;
import net.loveruby.cflat.parser.Parser;

import java.util.Collections;
import java.util.List;

public class ParserErrorListener extends BaseErrorListener  {
    Parser parser;
    public ParserErrorListener(Parser p) {
        parser  = p;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e)
    {
        String sourceName = recognizer.getInputStream().getSourceName();
        if (!sourceName.isEmpty()) {
            sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
        }
        List<String> ruleStacks = ((CbcParser) recognizer).getRuleInvocationStack();
        Collections.reverse(ruleStacks);

        // e.printStackTrace();
        System.err.println("[语法错误] 规则栈: " + ruleStacks);
        String message = "line: " + line + ", position: " + charPositionInLine + ", invalid symbol: " + offendingSymbol + ".\nmsg: " + msg;
        // System.err.println(message);
        parser.setParserErrmsg(message);
    }
}