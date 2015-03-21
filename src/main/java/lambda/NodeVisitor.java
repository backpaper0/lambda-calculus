package lambda;

public interface NodeVisitor<P, R> {

    R visit(Var var, P p);

    R visit(Abs abs, P p);

    R visit(App app, P p);
}
