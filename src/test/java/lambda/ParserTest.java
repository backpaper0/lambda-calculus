package lambda;

import static lambda.test.Nodes.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import org.junit.Test;

public class ParserTest {

    @Test
    public void testVar() throws Exception {
        Var actual = parser("x").var();
        Var expected = var("x");
        assertThat(actual, is(expected));
    }

    @Test
    public void testAbs() throws Exception {
        Abs actual = parser("\\x.x").abs();
        Abs expected = abs("x", "x");
        assertThat(actual, is(expected));
    }

    @Test
    public void testAbsVars() throws Exception {
        Abs actual = parser("\\xyz.x").abs();
        Abs expected = abs("x", abs("y", abs("z", "x")));
        assertThat(actual, is(expected));
    }

    @Test
    public void testApp() throws Exception {
        App actual = parser("xy").app();
        App expected = app("x", "y");
        assertThat(actual, is(expected));
    }

    @Test
    public void testAppLeftCombined() throws Exception {
        App actual = parser("abcd").app();
        App expected = app(app(app("a", "b"), "c"), "d");
        assertThat(actual, is(expected));
    }

    @Test
    public void testParen() throws Exception {
        Node actual = parser("(\\x.x)").paren();
        Node expected = abs("x", "x");
        assertThat(actual, is(expected));
    }

    @Test
    public void testParse() throws Exception {
        Node actual = parser("(\\f.(\\x.(f(fx))))").parse();
        Node expected = abs("f", abs("x", app("f", app("f", "x"))));
        assertThat(actual, is(expected));
    }

    @Test
    public void testParseAppAbs() throws Exception {
        Node actual = parser("(\\x . x) y").parse();
        Node expected = app(abs("x", "x"), "y");
        assertThat(actual, is(expected));
    }

    //λf. (λx. f (λy. x x y)) (λx. f (λy. x x y))
    @Test
    public void testParseZCombinator() throws Exception {
        Node actual = parser("\\f. (\\x. f (\\y. x x y)) (\\x. f (\\y. x x y))")
                .parse();
        Var f = var("f");
        Var x = var("x");
        Var y = var("y");
        Node node = abs(x, app(f, (abs(y, app(x, x, y)))));
        Node expected = abs(f, app(node, node));
        assertThat(actual, is(expected));
    }

    private static Parser parser(String source) throws IOException,
            ParseException {
        return new Parser(new Lexer(new StringReader(source)));
    }
}
