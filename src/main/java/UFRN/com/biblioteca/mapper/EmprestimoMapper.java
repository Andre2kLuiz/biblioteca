package UFRN.com.biblioteca.mapper;

import UFRN.com.biblioteca.dto.EmprestimoRequestDTO;
import UFRN.com.biblioteca.dto.EmprestimoResponseDTO;
import UFRN.com.biblioteca.model.Emprestimo;

import java.time.LocalDate;

public class EmprestimoMapper {

    public static Emprestimo toEntity(EmprestimoRequestDTO dto) {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setAlunoId(dto.getAlunoId());
        emprestimo.setLivroId(dto.getLivroId());
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDevolvido(false);
        return emprestimo;
    }

    public static EmprestimoResponseDTO toDTO(Emprestimo e, String alunoNome, String livroTitulo) {
        return EmprestimoResponseDTO.builder()
                .id(e.getId())
                .alunoId(e.getAlunoId())
                .alunoNome(alunoNome)
                .livroId(e.getLivroId())
                .livroTitulo(livroTitulo)
                .dataEmprestimo(e.getDataEmprestimo())
                .dataDevolucao(e.getDataDevolucao())
                .devolvido(e.isDevolvido())
                .build();
    }
}
