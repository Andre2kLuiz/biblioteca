package UFRN.com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LivroRequestDTO {

    @Schema(description = "Título do livro", example = "Dom Casmurro")
    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @Schema(description = "Autor do livro", example = "Machado de Assis")
    @NotBlank(message = "Autor é obrigatório")
    private String autor;
}
