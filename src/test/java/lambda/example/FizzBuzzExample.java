package lambda.example;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import lambda.Lexer;
import lambda.Node;
import lambda.Parser;

public class FizzBuzzExample {

    public static void main(String[] args) throws Exception {
        try (BufferedReader in = Files.newBufferedReader(Paths.get(FizzBuzzExample.class.getResource("/FizzBuzz")
                                                                                        .toURI()))) {
            Node node = new Parser(new Lexer(in)).parse();
            System.out.println(node);
        }
    }
}
