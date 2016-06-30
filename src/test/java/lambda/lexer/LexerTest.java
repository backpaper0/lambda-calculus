package lambda.lexer;

import static lambda.Tests.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Iterator;

import org.junit.Test;

public class LexerTest {

    @Test
    public void test_var() throws Exception {
        Iterator<Token> lexer = Lexer.tokens("x".chars()).iterator();
        assertThat(lexer.next()).matches(name('x')::is);
        assertThat(lexer.hasNext()).isFalse();
    }

    @Test
    public void test_app() throws Exception {
        Iterator<Token> lexer = Lexer.tokens("xy".chars()).iterator();
        assertThat(lexer.next()).matches(name('x')::is);
        assertThat(lexer.next()).matches(name('y')::is);
        assertThat(lexer.hasNext()).isFalse();
    }

    @Test
    public void test_abs() throws Exception {
        Iterator<Token> lexer = Lexer.tokens("λx.x".chars()).iterator();
        assertThat(lexer.next()).matches(lambda()::is);
        assertThat(lexer.next()).matches(name('x')::is);
        assertThat(lexer.next()).matches(period()::is);
        assertThat(lexer.next()).matches(name('x')::is);
        assertThat(lexer.hasNext()).isFalse();
    }

    @Test
    public void test_church_number_with_space() throws Exception {
        Iterator<Token> lexer = Lexer.tokens(" λ f x . f ( f x ) ".chars()).iterator();
        assertThat(lexer.next()).matches(lambda()::is);
        assertThat(lexer.next()).matches(name('f')::is);
        assertThat(lexer.next()).matches(name('x')::is);
        assertThat(lexer.next()).matches(period()::is);
        assertThat(lexer.next()).matches(name('f')::is);
        assertThat(lexer.next()).matches(parenl()::is);
        assertThat(lexer.next()).matches(name('f')::is);
        assertThat(lexer.next()).matches(name('x')::is);
        assertThat(lexer.next()).matches(parenr()::is);
        assertThat(lexer.hasNext()).isFalse();
    }

    @Test
    public void test_unknown_character() throws Exception {
        Iterator<Token> lexer = Lexer.tokens("0".chars()).iterator();
        assertThatThrownBy(lexer::next);
    }
}
