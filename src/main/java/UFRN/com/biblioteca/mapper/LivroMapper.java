package UFRN.com.biblioteca.mapper;

import UFRN.com.biblioteca.dto.LivroRequestDTO;
import UFRN.com.biblioteca.dto.LivroResponseDTO;
import UFRN.com.biblioteca.model.Livro;

public class LivroMapper {

    public static Livro toEntity(LivroRequestDTO dto) {
        return Livro.builder()
                .titulo(dto.getTitulo())
                .autor(dto.getAutor())
                .disponivel(true)
                .build();
    }

    public static LivroResponseDTO toDTO(Livro livro) {
        return LivroResponseDTO.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .autor(livro.getAutor())
                .disponivel(livro.isDisponivel())
                .build();
    }
}
