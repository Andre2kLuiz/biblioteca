package UFRN.com.biblioteca.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import UFRN.com.biblioteca.dto.AlunoRequestDTO;
import UFRN.com.biblioteca.dto.AlunoResponseDTO;
import UFRN.com.biblioteca.exceptions.AlunoNaoEncontradoException;
import UFRN.com.biblioteca.mapper.AlunoMapper;
import UFRN.com.biblioteca.model.Aluno;
import UFRN.com.biblioteca.repositories.AlunoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    @Cacheable("alunos")
    public Page<AlunoResponseDTO> listarTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        return alunoRepository.findAll(pageable)
                .map(AlunoMapper::toDTO); // converte Page<Aluno> para Page<AlunoResponseDTO>
    }

    @Cacheable(value = "aluno", key = "#id")
    public AlunoResponseDTO buscarPorId(@NotBlank String id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));
        return AlunoMapper.toDTO(aluno);
    }

    public AlunoResponseDTO salvar(@Valid AlunoRequestDTO dto) {
        Aluno aluno = AlunoMapper.toEntity(dto);
        Aluno salvo = alunoRepository.save(aluno);
        return AlunoMapper.toDTO(salvo);
    }

    public void deletar(@NotBlank String id) {
        alunoRepository.deleteById(id);
    }
}
