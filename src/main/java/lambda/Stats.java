package lambda;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lambda.util.Equals;

public class Stats implements Node {

    public final List<Node> nodes;

    public Stats(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public <P, R> R accept(NodeVisitor<P, R> visitor, P p) {
        return visitor.visit(this, p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes);
    }

    @Override
    public boolean equals(Object obj) {
        return Equals.of(Stats.class).getter(a -> a.nodes).equals(this, obj);
    }

    @Override
    public String toString() {
        return nodes.stream()
                    .map(Node::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
    }
}
