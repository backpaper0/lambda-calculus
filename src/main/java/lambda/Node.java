package lambda;

public interface Node {

    <P, R> R accept(NodeVisitor<P, R> visitor, P p);
}
