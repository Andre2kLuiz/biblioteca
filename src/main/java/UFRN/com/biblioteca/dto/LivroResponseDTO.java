package UFRN.com.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LivroResponseDTO {

    @Schema(description = "Identificador único do livro", example = "64e8455fa7e47c5b984d9100")
    private String id;

    @Schema(description = "Título do livro", example = "Dom Casmurro")
    private String titulo;

    @Schema(description = "Autor do livro", example = "Machado de Assis")
    private String autor;

    @Schema(description = "Indica se o livro está disponível para empréstimo", example = "true")
    private boolean disponivel;
}
