package UFRN.com.biblioteca.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import UFRN.com.biblioteca.dto.EmprestimoRequestDTO;
import UFRN.com.biblioteca.dto.EmprestimoResponseDTO;
import UFRN.com.biblioteca.exceptions.EmprestimoNaoEncontradoException;
import UFRN.com.biblioteca.services.EmprestimoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/emprestimos")
@RequiredArgsConstructor
@Tag(name = "Empréstimos", description = "Gerenciamento de empréstimos de livros")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @GetMapping
    @Operation(summary = "Listar todos os empréstimos", description = "Retorna uma lista paginada de empréstimos")
    public ResponseEntity<Page<EmprestimoResponseDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(emprestimoService.listarTodos(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empréstimo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empréstimo encontrado"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    public ResponseEntity<EmprestimoResponseDTO> buscarPorId(@PathVariable @NotBlank String id) {
        try {
            return ResponseEntity.ok(emprestimoService.buscarPorId(id));
        } catch (RuntimeException e) {
            throw new EmprestimoNaoEncontradoException(id);
        }
    }

    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Buscar empréstimos de um aluno pelo ID do aluno")
    public ResponseEntity<List<EmprestimoResponseDTO>> buscarPorAluno(@PathVariable @NotBlank String alunoId) {
        return ResponseEntity.ok(emprestimoService.buscarPorAluno(alunoId));
    }

    @PostMapping
    @Operation(summary = "Registrar um novo empréstimo")
    @ApiResponse(responseCode = "201", description = "Empréstimo registrado com sucesso")
    public ResponseEntity<EmprestimoResponseDTO> registrarEmprestimo(
            @Valid @RequestBody EmprestimoRequestDTO dto) {
        EmprestimoResponseDTO salvo = emprestimoService.registraEmprestimo(dto);
        URI location = URI.create("/api/emprestimos/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}/devolucao")
    @Operation(summary = "Registrar devolução de um livro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolução registrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    public ResponseEntity<EmprestimoResponseDTO> registrarDevolucao(@PathVariable @NotBlank String id) {
        try {
            return ResponseEntity.ok(emprestimoService.registrarDevolucao(id));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado para devolução");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um empréstimo")
    @ApiResponse(responseCode = "204", description = "Empréstimo excluído com sucesso")
    public ResponseEntity<Void> deletar(@PathVariable @NotBlank String id) {
        emprestimoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/atrasados")
    @Operation(summary = "Listar empréstimos em atraso")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarAtrasados() {
        return ResponseEntity.ok(emprestimoService.listarAtrasados());
    }

}
