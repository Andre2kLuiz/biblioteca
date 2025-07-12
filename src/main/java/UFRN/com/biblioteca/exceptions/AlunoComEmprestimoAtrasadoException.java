
package UFRN.com.biblioteca.exceptions;

public class AlunoComEmprestimoAtrasadoException extends RuntimeException {
    public AlunoComEmprestimoAtrasadoException(String nomeAluno) {
        super("O aluno '" + nomeAluno + "' possui empréstimos em atraso e não pode pegar novos livros.");
    }
}
