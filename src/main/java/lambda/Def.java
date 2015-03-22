package lambda;

import java.util.Objects;

import lambda.util.Equals;

public class Def implements Node {

    public final Alias alias;

    public final Expr expr;

    public Def(Alias alias, Expr expr) {
        this.alias = alias;
        this.expr = expr;
    }

    @Override
    public <P, R> R accept(NodeVisitor<P, R> visitor, P p) {
        return visitor.visit(this, p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, expr);
    }

    @Override
    public boolean equals(Object obj) {
        return Equals.of(Def.class)
                     .getter(a -> a.alias)
                     .getter(a -> a.expr)
                     .equals(this, obj);
    }

    @Override
    public String toString() {
        return String.format("@%1$s = %2$s", alias, expr);
    }
}
