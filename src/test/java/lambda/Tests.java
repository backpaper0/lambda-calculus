package lambda;

import lambda.lexer.Token;
import lambda.lexer.TokenType;

public class Tests {

    public static Token name(char c) {
        return new Token(TokenType.NAME, c);
    }

    public static Token parenl() {
        return new Token(TokenType.PAREN_L, '(');
    }

    public static Token parenr() {
        return new Token(TokenType.PAREN_R, ')');
    }

    public static Token lambda() {
        return new Token(TokenType.LAMBDA, 'λ');
    }

    public static Token period() {
        return new Token(TokenType.PERIOD, '.');
    }
}
