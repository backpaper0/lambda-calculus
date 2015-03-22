package lambda;

import java.util.Objects;

import lambda.util.Equals;

public class Alias implements Expr {

    public final String name;

    public Alias(String name) {
        this.name = name;
    }

    @Override
    public <P, R> R accept(NodeVisitor<P, R> visitor, P p) {
        return visitor.visit(this, p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        return Equals.of(Alias.class).getter(a -> a.name).equals(this, obj);
    }

    @Override
    public String toString() {
        return name;
    }
}
