package lambda;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;

public class Lexer {

    private static final char EOF = (char) -1;

    private final Reader in;

    private char c;

    public Lexer(Reader in) {
        this.in = in;
        consume();
    }

    private void consume() {
        try {
            c = (char) in.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Token next() {
        while (c != EOF) {
            if (c == ' ' || c == '\r' || c == '\n' || c == '\t') {
                consume();
            } else {
                switch (c) {
                case '\\':
                    consume();
                    return new Token(TokenType.LAMBDA, "\\");
                case '.':
                    consume();
                    return new Token(TokenType.PERIOD, ".");
                case '(':
                    consume();
                    return new Token(TokenType.L_PAREN, "(");
                case ')':
                    consume();
                    return new Token(TokenType.R_PAREN, ")");
                default:
                    if ('a' <= c && c <= 'z') {
                        char name = c;
                        consume();
                        return new Token(TokenType.ID, String.valueOf(name));
                    }
                    //TODO ParseExceptionの詳細
                    throw new ParseException();
                }
            }
        }
        return new Token(TokenType.EOF, "<EOF>");
    }
}
