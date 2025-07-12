package UFRN.com.biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlunoRequestDTO {

    @Schema(description = "Nome completo do aluno", example = "João da Silva")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Schema(description = "Email institucional do aluno", example = "joao.silva@ufrn.br")
    @Email(message = "Email inválido")
    private String email;

}
