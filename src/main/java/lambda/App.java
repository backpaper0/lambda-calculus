package lambda;

import java.util.Objects;

import lambda.util.Equals;

public class App implements Expr {

    public final Expr left;

    public final Expr right;

    public App(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public <P, R> R accept(NodeVisitor<P, R> visitor, P p) {
        return visitor.visit(this, p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object obj) {
        return Equals.of(App.class)
                     .getter(a -> a.left)
                     .getter(a -> a.right)
                     .equals(this, obj);
    }

    @Override
    public String toString() {
        return String.format("(%1$s %2$s)", left, right);
    }
}
