package lambda.lexer;

public enum TokenType {
    LAMBDA, NAME, PERIOD, PAREN_L, PAREN_R, EOF;

    public static TokenType of(char c) {
        switch (c) {
        case 'λ':
            return TokenType.LAMBDA;
        case '.':
            return TokenType.PERIOD;
        case '(':
            return TokenType.PAREN_L;
        case ')':
            return TokenType.PAREN_R;
        default:
            if ('a' <= c && c <= 'z') {
                return TokenType.NAME;
            }
            //FIXME
            throw new RuntimeException(String.format("Unknown character '%s'", c));
        }
    }
}
