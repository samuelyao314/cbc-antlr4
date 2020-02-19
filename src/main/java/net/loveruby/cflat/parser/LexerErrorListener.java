package net.loveruby.cflat.parser;

import net.loveruby.cflat.parser.Parser;
import net.loveruby.cflat.parser.gen.CbcLexer;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.Interval;

public class LexerErrorListener  extends BaseErrorListener {
    Parser parser;
    public LexerErrorListener(Parser p) {
        parser  = p;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e)
    {
        CbcLexer lexer = (CbcLexer)recognizer;

        // 摘自org.antlr.v4.runtime.Lexer.notifyListeners
        String image = lexer._input.getText(Interval.of(lexer._tokenStartCharIndex, lexer._input.index()));
        String errWord = lexer.getErrorDisplay(image);
        String message = "[词法错误] 行:" + line  + ", 列: " +charPositionInLine + ", 错误词: " + errWord;
        // System.err.println(message);
        parser.setParserErrmsg(message);
    }
}
