package UFRN.com.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EmprestimoResponseDTO {

    @Schema(description = "Identificador único do empréstimo", example = "64e841bfa7e47c5b984d90b8")
    private String id;

    @Schema(description = "ID do aluno associado ao empréstimo", example = "64e840bba7e47c5b984d90a5")
    private String alunoId;

    @Schema(description = "Nome do aluno que fez o empréstimo", example = "Maria Oliveira")
    private String alunoNome;

    @Schema(description = "ID do livro emprestado", example = "64e840bba7e47c5b984d90f3")
    private String livroId;

    @Schema(description = "Título do livro emprestado", example = "O Senhor dos Anéis")
    private String livroTitulo;

    @Schema(description = "Data em que o livro foi emprestado", example = "2025-07-11")
    private LocalDate dataEmprestimo;

    @Schema(description = "Data prevista ou efetiva de devolução", example = "2025-07-25")
    private LocalDate dataDevolucao;

    @Schema(description = "Indica se o livro já foi devolvido", example = "false")
    private boolean devolvido;
}
