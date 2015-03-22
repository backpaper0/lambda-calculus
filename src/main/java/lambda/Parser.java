package lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Parser {

    private final Lexer lexer;

    private Token token;

    private final List<Token> tokens = new ArrayList<>();

    private int index;

    private final List<Integer> indexes = new ArrayList<>();

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        consume();
    }

    private void consume() {
        while (index >= tokens.size()) {
            tokens.add(lexer.next());
        }
        token = tokens.get(index++);
    }

    private void match(TokenType expected) {
        if (token.isTypeOf(expected)) {
            consume();
        } else {
            //TODO ParseExceptionの詳細
            throw new ParseException();
        }
    }

    public Node parse() {
        List<Node> nodes = new ArrayList<>();
        do {
            Def stat = def();
            nodes.add(stat);
        } while (token.isTypeOf(TokenType.DEF));
        match(TokenType.EOF);
        return new Stats(nodes);
    }

    protected Expr paren() {
        match(TokenType.L_PAREN);
        Expr expr = expr();
        match(TokenType.R_PAREN);
        return expr;
    }

    protected Expr expr() {
        return get(this::tryApp,
                   this::tryAbs,
                   this::tryVar,
                   this::tryAlias,
                   this::tryParen);
    }

    protected Var var() {
        Token name = token;
        match(TokenType.ID);
        return new Var(name.text);
    }

    protected Abs abs() {
        match(TokenType.LAMBDA);
        return absWithoutLambda();
    }

    private Abs absWithoutLambda() {
        Var var = var();
        Expr expr = get(this::tryNestedAbs, this::tryAbsExpr);
        return new Abs(var, expr);
    }

    private Optional<Expr> tryNestedAbs() {
        return tryExpr(this::absWithoutLambda);
    }

    private Optional<Expr> tryAbsExpr() {
        return tryExpr(() -> {
            match(TokenType.PERIOD);
            return expr();
        });
    }

    protected App app() {
        Expr left = get(this::tryParen, this::tryVar, this::tryAlias);
        return get(() -> tryAppWithLeft(left));
    }

    private Optional<App> tryAppWithLeft(Expr left) {
        return tryExpr(() -> {
            Expr right = get(this::tryParen,
                             this::tryAbs,
                             this::tryVar,
                             this::tryAlias);
            App app = new App(left, right);
            return tryAppWithLeft(app).orElse(app);
        });
    }

    private Optional<Expr> tryParen() {
        return tryExpr(this::paren);
    }

    private Optional<Expr> tryVar() {
        return tryExpr(this::var);
    }

    private Optional<Expr> tryAbs() {
        return tryExpr(this::abs);
    }

    private Optional<Expr> tryApp() {
        return tryExpr(this::app);
    }

    private <T extends Expr> Optional<T> tryExpr(Supplier<T> supplier) {
        indexes.add(index);
        try {
            T node = supplier.get();
            return Optional.of(node);
        } catch (ParseException e) {
            index = indexes.get(indexes.size() - 1);
            token = tokens.get(index - 1);
            return Optional.empty();
        } finally {
            indexes.remove(indexes.size() - 1);
        }
    }

    @SafeVarargs
    private final <T extends Expr> T get(Supplier<Optional<T>>... suppliers) {
        //TODO ParseExceptionの詳細
        return Arrays.stream(suppliers)
                     .map(Supplier::get)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .findFirst()
                     .orElseThrow(ParseException::new);
    }

    protected Def def() {
        match(TokenType.DEF);
        Alias alias = alias();
        match(TokenType.EQUAL);
        Expr expr = expr();
        return new Def(alias, expr);
    }

    protected Alias alias() {
        Token name = token;
        match(TokenType.ALIAS);
        return new Alias(name.text);
    }

    private Optional<Expr> tryAlias() {
        return tryExpr(this::alias);
    }
}
