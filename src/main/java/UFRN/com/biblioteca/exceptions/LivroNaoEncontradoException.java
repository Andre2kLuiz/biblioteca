package UFRN.com.biblioteca.exceptions;

public class LivroNaoEncontradoException extends RuntimeException {
    public LivroNaoEncontradoException(String id) {
        super("Livro com ID '" + id + "' não foi encontrado.");
    }
}
