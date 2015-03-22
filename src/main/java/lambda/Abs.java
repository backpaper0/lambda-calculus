package lambda;

import java.util.Objects;

import lambda.util.Equals;

public class Abs implements Expr {

    public final Var var;

    public final Expr expr;

    public Abs(Var var, Expr expr) {
        this.var = var;
        this.expr = expr;
    }

    @Override
    public <P, R> R accept(NodeVisitor<P, R> visitor, P p) {
        return visitor.visit(this, p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(var, expr);
    }

    @Override
    public boolean equals(Object obj) {
        return Equals.of(Abs.class)
                     .getter(a -> a.var)
                     .getter(a -> a.expr)
                     .equals(this, obj);
    }

    @Override
    public String toString() {
        return String.format("(\\%1$s.%2$s)", var, expr);
    }
}
