package lambda.test;

import java.util.Arrays;

import lambda.Abs;
import lambda.App;
import lambda.Node;
import lambda.Var;

public class Nodes {

    public static Abs abs(Var var, Node expr) {
        return new Abs(var, expr);
    }

    public static Abs abs(String var, Node expr) {
        return abs(var(var), expr);
    }

    public static Abs abs(Var var, String expr) {
        return abs(var, var(expr));
    }

    public static Abs abs(String var, String expr) {
        return abs(var(var), var(expr));
    }

    public static App app(String left, Node right) {
        return app(var(left), right);
    }

    public static App app(Node left, String right) {
        return app(left, var(right));
    }

    public static App app(String left, String right) {
        return app(var(left), right);
    }

    public static App app(Node... nodes) {
        return Arrays.stream(nodes).reduce(App::new).map(App.class::cast).get();
    }

    public static Var var(String name) {
        return new Var(name);
    }
}
