package net.loveruby.cflat.parser;


import org.antlr.v4.runtime.*;

public class ThrowingErrorListener  extends BaseErrorListener  {
    Parser parser;
    public ThrowingErrorListener(Parser p) {
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

        e.printStackTrace();
        String message = sourceName+"line "+line+":"+charPositionInLine+" "+msg;
        // System.err.println(sourceName+"line "+line+":"+charPositionInLine+" "+ msg);
        parser.setParserErrmsg(msg);
    }
}