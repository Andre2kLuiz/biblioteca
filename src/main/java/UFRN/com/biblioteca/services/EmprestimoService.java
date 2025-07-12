package UFRN.com.biblioteca.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import UFRN.com.biblioteca.config.BibliotecaProperties;
import UFRN.com.biblioteca.dto.EmprestimoRequestDTO;
import UFRN.com.biblioteca.dto.EmprestimoResponseDTO;
import UFRN.com.biblioteca.exceptions.AlunoComEmprestimoAtrasadoException;
import UFRN.com.biblioteca.exceptions.LivroIndisponivelException;
import UFRN.com.biblioteca.mapper.EmprestimoMapper;
import UFRN.com.biblioteca.model.Aluno;
import UFRN.com.biblioteca.model.Emprestimo;
import UFRN.com.biblioteca.repositories.AlunoRepository;
import UFRN.com.biblioteca.repositories.EmprestimoRepository;
import UFRN.com.biblioteca.repositories.LivroRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final AlunoRepository alunoRepository;
    private final BibliotecaProperties bibliotecaProperties;

    @Cacheable("emprestimos")
    public Page<EmprestimoResponseDTO> listarTodos(int page, int size) {
        return emprestimoRepository.findAll(PageRequest.of(page, size, Sort.by("dataEmprestimo").descending()))
                .map(this::toDTOComDados);
    }

    @Cacheable(value = "emprestimo", key = "#id")
    public EmprestimoResponseDTO buscarPorId(@NotBlank String id) {
        Emprestimo e = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        return toDTOComDados(e);
    }

    public List<EmprestimoResponseDTO> buscarPorAluno(@NotBlank String alunoId) {
        return emprestimoRepository.findByAlunoId(alunoId).stream()
                .map(this::toDTOComDados)
                .toList();
    }

    public EmprestimoResponseDTO registraEmprestimo(@Valid EmprestimoRequestDTO dto) {

        boolean livroEmprestado = emprestimoRepository.existsByLivroIdAndDevolvidoFalse(dto.getLivroId());
        if (livroEmprestado) {
            throw new LivroIndisponivelException();
        }

        List<Emprestimo> emprestimosDoAluno = emprestimoRepository.findByAlunoId(dto.getAlunoId());
        int prazoDias = bibliotecaProperties.getPrazoDias();
        LocalDate hoje = LocalDate.now();

        boolean temAtraso = emprestimosDoAluno.stream()
                .anyMatch(e -> !e.isDevolvido() &&
                        e.getDataEmprestimo().plusDays(prazoDias).isBefore(hoje));

        if (temAtraso) {
            String nome = alunoRepository.findById(dto.getAlunoId())
                    .map(Aluno::getNome)
                    .orElse("Aluno");
            throw new AlunoComEmprestimoAtrasadoException(nome);
        }

        Emprestimo emprestimo = EmprestimoMapper.toEntity(dto);
        emprestimo.setDataEmprestimo(hoje);
        emprestimo.setDevolvido(false);

        Emprestimo salvo = emprestimoRepository.save(emprestimo);

        livroRepository.findById(dto.getLivroId()).ifPresent(l -> {
            l.setDisponivel(false);
            livroRepository.save(l);
        });
        System.out.println("Prazo de devolução configurado: " + bibliotecaProperties.getPrazoDias() + " dias");

        return toDTOComDados(salvo);
    }

    public EmprestimoResponseDTO registrarDevolucao(@NotBlank String id) {
        Optional<Emprestimo> optional = emprestimoRepository.findById(id);
        if (optional.isPresent()) {
            Emprestimo e = optional.get();
            e.setDevolvido(true);
            e.setDataDevolucao(LocalDate.now());
            emprestimoRepository.save(e);

            livroRepository.findById(e.getLivroId()).ifPresent(l -> {
                l.setDisponivel(true);
                livroRepository.save(l);
            });

            return toDTOComDados(e);
        }
        throw new RuntimeException("Empréstimo não encontrado para devolução");
    }

    public void deletar(@NotBlank String id) {
        emprestimoRepository.deleteById(id);
    }

    private EmprestimoResponseDTO toDTOComDados(Emprestimo e) {
        String alunoNome = alunoRepository.findById(e.getAlunoId())
                .map(a -> a.getNome())
                .orElse("Aluno desconhecido");

        String livroTitulo = livroRepository.findById(e.getLivroId())
                .map(l -> l.getTitulo())
                .orElse("Livro desconhecido");

        return EmprestimoMapper.toDTO(e, alunoNome, livroTitulo);
    }

    public List<EmprestimoResponseDTO> listarHistoricoPorAluno(String alunoId) {
        List<Emprestimo> emprestimos = emprestimoRepository.findByAlunoId(alunoId);
        return emprestimos.stream()
                .map(this::toDTOComDados)
                .toList();
    }

    public List<EmprestimoResponseDTO> listarAtrasados() {
        List<Emprestimo> todos = emprestimoRepository.findAll();
        int prazoDias = bibliotecaProperties.getPrazoDias();
        LocalDate hoje = LocalDate.now();

        return todos.stream()
                .filter(e -> !e.isDevolvido() &&
                        e.getDataEmprestimo().plusDays(prazoDias).isBefore(hoje))
                .map(this::toDTOComDados)
                .toList();
    }

}
