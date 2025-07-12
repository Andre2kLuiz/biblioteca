package UFRN.com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmprestimoRequestDTO {

    @Schema(description = "ID do aluno que realizará o empréstimo", example = "64e840bba7e47c5b984d90a5")
    @NotBlank(message = "ID do aluno é obrigatório")
    private String alunoId;

    @Schema(description = "ID do livro a ser emprestado", example = "64e840bba7e47c5b984d90f3")
    @NotBlank(message = "ID do livro é obrigatório")
    private String livroId;
}
