package lambda.test;

import java.util.Arrays;

import lambda.Abs;
import lambda.Alias;
import lambda.App;
import lambda.Def;
import lambda.Expr;
import lambda.Node;
import lambda.Stats;
import lambda.Var;

public class Nodes {

    public static Abs abs(Var var, Expr expr) {
        return new Abs(var, expr);
    }

    public static Abs abs(String var, Expr expr) {
        return abs(var(var), expr);
    }

    public static Abs abs(Var var, String expr) {
        return abs(var, var(expr));
    }

    public static Abs abs(String var, String expr) {
        return abs(var(var), var(expr));
    }

    public static App app(String left, Expr right) {
        return app(var(left), right);
    }

    public static App app(Expr left, String right) {
        return app(left, var(right));
    }

    public static App app(String left, String right) {
        return app(var(left), right);
    }

    public static App app(Expr... nodes) {
        return Arrays.stream(nodes).reduce(App::new).map(App.class::cast).get();
    }

    public static Def def(String alias, Expr expr) {
        return def(alias(alias), expr);
    }

    public static Def def(Alias alias, Expr expr) {
        return new Def(alias, expr);
    }

    public static Var var(String name) {
        return new Var(name);
    }

    public static Alias alias(String name) {
        return new Alias(name);
    }

    public static Stats stats(Node... nodes) {
        return new Stats(Arrays.asList(nodes));
    }
}
