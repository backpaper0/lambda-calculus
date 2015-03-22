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

    @Test
    public void testAlias() throws Exception {
        Lexer lexer = new Lexer(new StringReader("@ONE = \\fx . fx"));
        assertThat(lexer.next(), is(new Token(TokenType.DEF, "@")));
        assertThat(lexer.next(), is(new Token(TokenType.ALIAS, "ONE")));
        assertThat(lexer.next(), is(new Token(TokenType.EQUAL, "=")));
        assertThat(lexer.next(), is(new Token(TokenType.LAMBDA, "\\")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "f")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "x")));
        assertThat(lexer.next(), is(new Token(TokenType.PERIOD, ".")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "f")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "x")));
        assertThat(lexer.next(), is(new Token(TokenType.EOF, "<EOF>")));
    }

    @Test
    public void testAliasInRight() throws Exception {
        Lexer lexer = new Lexer(new StringReader("@TWO = \\fx . f(ONEfx)"));
        assertThat(lexer.next(), is(new Token(TokenType.DEF, "@")));
        assertThat(lexer.next(), is(new Token(TokenType.ALIAS, "TWO")));
        assertThat(lexer.next(), is(new Token(TokenType.EQUAL, "=")));
        assertThat(lexer.next(), is(new Token(TokenType.LAMBDA, "\\")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "f")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "x")));
        assertThat(lexer.next(), is(new Token(TokenType.PERIOD, ".")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "f")));
        assertThat(lexer.next(), is(new Token(TokenType.L_PAREN, "(")));
        assertThat(lexer.next(), is(new Token(TokenType.ALIAS, "ONE")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "f")));
        assertThat(lexer.next(), is(new Token(TokenType.ID, "x")));
        assertThat(lexer.next(), is(new Token(TokenType.R_PAREN, ")")));
        assertThat(lexer.next(), is(new Token(TokenType.EOF, "<EOF>")));
    }
}
