package UFRN.com.biblioteca.services;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import UFRN.com.biblioteca.dto.LivroRequestDTO;
import UFRN.com.biblioteca.dto.LivroResponseDTO;
import UFRN.com.biblioteca.exceptions.LivroIndisponivelException;
import UFRN.com.biblioteca.exceptions.LivroNaoEncontradoException;
import UFRN.com.biblioteca.mapper.LivroMapper;
import UFRN.com.biblioteca.model.Livro;
import UFRN.com.biblioteca.repositories.LivroRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    @Cacheable("livros")
    public Page<LivroResponseDTO> listarTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("titulo").ascending());
        return livroRepository.findAll(pageable)
                .map(LivroMapper::toDTO);
    }

    @Cacheable(value = "livro", key = "#id")
    public LivroResponseDTO listarPorId(@NotBlank String id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroIndisponivelException());
        return LivroMapper.toDTO(livro);
    }

    public LivroResponseDTO salvar(@Valid LivroRequestDTO dto) {
        Livro livro = LivroMapper.toEntity(dto);
        Livro salvo = livroRepository.save(livro);
        return LivroMapper.toDTO(salvo);
    }

    public List<LivroResponseDTO> buscarPorTituloOuAutor(String query) {
        List<Livro> livros = livroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(query, query);
        return livros.stream().map(LivroMapper::toDTO).toList();
    }

    public LivroResponseDTO atualizar(String id, @Valid LivroRequestDTO dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));

        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());

        Livro atualizado = livroRepository.save(livro);
        return LivroMapper.toDTO(atualizado);
    }

    public List<LivroResponseDTO> listarDisponiveis() {
        List<Livro> livros = livroRepository.findByDisponivelTrue();
        return livros.stream().map(LivroMapper::toDTO).toList();
    }

    public List<LivroResponseDTO> listarEmprestados() {
        List<Livro> livros = livroRepository.findByDisponivelFalse();
        return livros.stream().map(LivroMapper::toDTO).toList();
    }

    public void deletar(@NotBlank String id) {
        livroRepository.deleteById(id);
    }
}
