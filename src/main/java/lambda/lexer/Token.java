package lambda.lexer;

import java.util.Objects;

public final class Token {

    private final TokenType type;
    private final char c;

    public Token(TokenType type, char c) {
        this.type = Objects.requireNonNull(type);
        this.c = c;
    }

    public boolean is(Token t) {
        return type == t.type && c == t.c;
    }

    @Override
    public String toString() {
        if (type == TokenType.EOF) {
            return type.toString();
        }
        return String.format("%s[%s]", type, c);
    }
}
