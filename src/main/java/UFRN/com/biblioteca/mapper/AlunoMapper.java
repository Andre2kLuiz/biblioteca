package UFRN.com.biblioteca.mapper;

import UFRN.com.biblioteca.dto.AlunoRequestDTO;
import UFRN.com.biblioteca.dto.AlunoResponseDTO;
import UFRN.com.biblioteca.model.Aluno;

public class AlunoMapper {
    public static Aluno toEntity(AlunoRequestDTO dto) {
        return Aluno.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .matricula(gerarMatricula())
                .build();
    }

    public static AlunoResponseDTO toDTO(Aluno aluno) {
        return AlunoResponseDTO.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .email(aluno.getEmail())
                .matricula(aluno.getMatricula())
                .build();
    }

    private static String gerarMatricula() {
        return "MAT-" + System.currentTimeMillis(); // Exemplo simples
    }
}
