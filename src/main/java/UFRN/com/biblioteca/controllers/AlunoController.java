package UFRN.com.biblioteca.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import UFRN.com.biblioteca.dto.AlunoRequestDTO;
import UFRN.com.biblioteca.dto.AlunoResponseDTO;
import UFRN.com.biblioteca.dto.EmprestimoResponseDTO;
import UFRN.com.biblioteca.exceptions.AlunoNaoEncontradoException;
import UFRN.com.biblioteca.services.AlunoService;
import UFRN.com.biblioteca.services.EmprestimoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "Gerenciamento de alunos")
public class AlunoController {

    private final AlunoService alunoService;
    private final EmprestimoService emprestimoService;

    @GetMapping
    @Operation(summary = "Listar todos os alunos", description = "Retorna uma lista paginada de alunos")
    public ResponseEntity<Page<AlunoResponseDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(alunoService.listarTodos(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable @NotBlank String id) {
        try {
            return ResponseEntity.ok(alunoService.buscarPorId(id));
        } catch (RuntimeException e) {
            throw new AlunoNaoEncontradoException(id);
        }
    }

    @PostMapping
    @Operation(summary = "Criar um novo aluno", responses = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso")
    })
    public ResponseEntity<AlunoResponseDTO> salvar(@Valid @RequestBody AlunoRequestDTO dto) {
        AlunoResponseDTO salvo = alunoService.salvar(dto);
        URI location = URI.create("/api/alunos/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um aluno por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Aluno deletado com sucesso")
    })
    public ResponseEntity<Void> deletar(@PathVariable @NotBlank String id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/historico")
    @Operation(summary = "Listar histórico de empréstimos do aluno")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarHistorico(
            @PathVariable @NotBlank String id) {
        return ResponseEntity.ok(emprestimoService.listarHistoricoPorAluno(id));
    }
}
