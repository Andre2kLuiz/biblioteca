package UFRN.com.biblioteca.exceptions;

public class LivroIndisponivelException extends RuntimeException {
    public LivroIndisponivelException() {
        super("Este livro já está emprestado e ainda não foi devolvido.");
    }
}
