package UFRN.com.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlunoResponseDTO {

    @Schema(description = "Identificador único do aluno", example = "64e83f6fa7e47c5b984d90a1")
    private String id;

    @Schema(description = "Nome completo do aluno", example = "João da Silva")
    private String nome;

    @Schema(description = "Email institucional do aluno", example = "joao.silva@ufrn.br")
    private String email;

    @Schema(description = "Número de matrícula do aluno", example = "2021001234")
    private String matricula;
}
