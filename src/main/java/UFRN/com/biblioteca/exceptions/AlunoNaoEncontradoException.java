package UFRN.com.biblioteca.exceptions;

public class AlunoNaoEncontradoException extends RuntimeException {
    public AlunoNaoEncontradoException(String id) {
        super("Aluno com ID '" + id + "' não foi encontrado.");
    }
}
