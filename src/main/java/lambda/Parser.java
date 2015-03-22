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
        Node expr = expr();
        match(TokenType.EOF);
        return expr;
    }

    protected Node paren() {
        match(TokenType.L_PAREN);
        Node expr = expr();
        match(TokenType.R_PAREN);
        return expr;
    }

    protected Node expr() {
        return get(this::tryApp, this::tryAbs, this::tryVar, this::tryParen);
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
        Node expr = get(this::tryNestedAbs, this::tryAbsExpr);
        return new Abs(var, expr);
    }

    private Optional<Node> tryNestedAbs() {
        return tryNode(this::absWithoutLambda);
    }

    private Optional<Node> tryAbsExpr() {
        return tryNode(() -> {
            match(TokenType.PERIOD);
            return expr();
        });
    }

    protected App app() {
        Node left = get(this::tryParen, this::tryVar);
        return get(() -> tryAppWithLeft(left));
    }

    private Optional<App> tryAppWithLeft(Node left) {
        return tryNode(() -> {
            Node right = get(this::tryParen, this::tryAbs, this::tryVar);
            App app = new App(left, right);
            return tryAppWithLeft(app).orElse(app);
        });
    }

    private Optional<Node> tryParen() {
        return tryNode(this::paren);
    }

    private Optional<Node> tryVar() {
        return tryNode(this::var);
    }

    private Optional<Node> tryAbs() {
        return tryNode(this::abs);
    }

    private Optional<Node> tryApp() {
        return tryNode(this::app);
    }

    private <T extends Node> Optional<T> tryNode(Supplier<T> supplier) {
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
    private final <T extends Node> T get(Supplier<Optional<T>>... suppliers) {
        //TODO ParseExceptionの詳細
        return Arrays.stream(suppliers)
                     .map(Supplier::get)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .findFirst()
                     .orElseThrow(ParseException::new);
    }
}
