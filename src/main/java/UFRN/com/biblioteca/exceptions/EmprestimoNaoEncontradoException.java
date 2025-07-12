package UFRN.com.biblioteca.exceptions;

public class EmprestimoNaoEncontradoException extends RuntimeException {
    public EmprestimoNaoEncontradoException(String id) {
        super("Emprestimo com ID '" + id + "' não foi encontrado.");
    }
}
