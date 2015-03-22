package lambda;

public interface NodeVisitor<P, R> {

    R visit(Var var, P p);

    R visit(Abs abs, P p);

    R visit(App app, P p);

    R visit(Def def, P p);

    R visit(Alias alias, P p);

    R visit(Stats stats, P p);
}
