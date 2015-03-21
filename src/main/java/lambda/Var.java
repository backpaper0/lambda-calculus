package lambda;

import java.util.Objects;

import lambda.util.Equals;

public class Var implements Node {

    public final String name;

    public Var(String name) {
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
        return Equals.of(Var.class).getter(a -> a.name).equals(this, obj);
    }

    @Override
    public String toString() {
        return name;
    }
}
