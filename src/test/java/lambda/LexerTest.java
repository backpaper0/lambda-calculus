package lambda;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.StringReader;

import org.junit.Test;

public class LexerTest {

    @Test
    public void test() throws Exception {
        Lexer lexer = new Lexer(new StringReader("(\\f.(\\x.(f(fx))))"));
        assertThat(lexer.next(), is(new Token(TokenType.L_PAREN, "(")));
        assertThat(lexer.next(), is(new Token(TokenType.LAMBDA, "\\")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "f")));
        assertThat(lexer.next(), is(new Token(TokenType.PERIOD, ".")));
        assertThat(lexer.next(), is(new Token(TokenType.L_PAREN, "(")));
        assertThat(lexer.next(), is(new Token(TokenType.LAMBDA, "\\")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "x")));
        assertThat(lexer.next(), is(new Token(TokenType.PERIOD, ".")));
        assertThat(lexer.next(), is(new Token(TokenType.L_PAREN, "(")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "f")));
        assertThat(lexer.next(), is(new Token(TokenType.L_PAREN, "(")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "f")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "x")));
        assertThat(lexer.next(), is(new Token(TokenType.R_PAREN, ")")));
        assertThat(lexer.next(), is(new Token(TokenType.R_PAREN, ")")));
        assertThat(lexer.next(), is(new Token(TokenType.R_PAREN, ")")));
        assertThat(lexer.next(), is(new Token(TokenType.R_PAREN, ")")));
        assertThat(lexer.next(), is(new Token(TokenType.EOF, "<EOF>")));
    }
}
