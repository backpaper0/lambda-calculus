package lambda;

import static lambda.test.Nodes.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.StringReader;

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
    public void testAbsWithAlias() throws Exception {
        Abs actual = parser("\\f.HOGE").abs();
        Abs expected = abs("f", alias("HOGE"));
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
    public void testAppWithAliasAtLeft() throws Exception {
        App actual = parser("HOGEx").app();
        App expected = app(alias("HOGE"), var("x"));
        assertThat(actual, is(expected));
    }

    @Test
    public void testAppWithAliasAtRight() throws Exception {
        App actual = parser("xHOGE").app();
        App expected = app(var("x"), alias("HOGE"));
        assertThat(actual, is(expected));
    }

    @Test
    public void testAppWithAliasAtBoth() throws Exception {
        App actual = parser("FOO BAR").app();
        App expected = app(alias("FOO"), alias("BAR"));
        assertThat(actual, is(expected));
    }

    @Test
    public void testParen() throws Exception {
        Node actual = parser("(\\x.x)").paren();
        Node expected = abs("x", "x");
        assertThat(actual, is(expected));
    }

    @Test
    public void testExpr() throws Exception {
        Expr actual = parser("(\\f.(\\x.(f(fx))))").expr();
        Expr expected = abs("f", abs("x", app("f", app("f", "x"))));
        assertThat(actual, is(expected));
    }

    @Test
    public void testExprAppAbs() throws Exception {
        Expr actual = parser("(\\x . x) y").expr();
        Expr expected = app(abs("x", "x"), "y");
        assertThat(actual, is(expected));
    }

    //λf. (λx. f (λy. x x y)) (λx. f (λy. x x y))
    @Test
    public void testExprZCombinator() throws Exception {
        Expr actual = parser("\\f. (\\x. f (\\y. x x y)) (\\x. f (\\y. x x y))").expr();
        Var f = var("f");
        Var x = var("x");
        Var y = var("y");
        Expr node = abs(x, app(f, (abs(y, app(x, x, y)))));
        Expr expected = abs(f, app(node, node));
        assertThat(actual, is(expected));
    }

    @Test
    public void testDef() throws Exception {
        Def actual = parser("@ONE = \\fx . fx").def();
        Def expected = def("ONE", abs("f", abs("x", app("f", "x"))));
        assertThat(actual, is(expected));
    }

    @Test
    public void testParseContainsDef() throws Exception {
        Node actual = parser("@ONE = \\fx . fx @TWO = \\fx . f(ONE f x)").parse();
        Def one = def("ONE", abs("f", abs("x", app("f", "x"))));
        Def two = def("TWO",
                      abs("f",
                          abs("x",
                              app("f", app(alias("ONE"), var("f"), var("x"))))));
        Node expected = stats(one, two);
        assertThat(actual, is(expected));
    }

    private static Parser parser(String source) {
        return new Parser(new Lexer(new StringReader(source)));
    }
}
