package lambda.lexer;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Lexer {

    public static Stream<Token> tokens(IntStream source) {
        IntPredicate isWhitespace = Character::isWhitespace;
        return source.filter(isWhitespace.negate()).mapToObj(i -> {
            char c = (char) i;
            return new Token(TokenType.of(c), c);
        });
    }
}