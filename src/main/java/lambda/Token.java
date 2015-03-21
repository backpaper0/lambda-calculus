package lambda;

import java.util.Objects;

import lambda.util.Equals;

public class Token {

    public final TokenType type;

    public final String text;

    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }

    public boolean isTypeOf(TokenType type) {
        return this.type == type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, text);
    }

    @Override
    public boolean equals(Object obj) {
        return Equals.of(Token.class).getter(a -> a.type).getter(a -> a.text)
                .equals(this, obj);
    }

    @Override
    public String toString() {
        return String.format("%1$s('%2$s')", type, text);
    }
}
