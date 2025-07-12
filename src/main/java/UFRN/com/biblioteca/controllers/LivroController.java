package UFRN.com.biblioteca.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import UFRN.com.biblioteca.dto.LivroRequestDTO;
import UFRN.com.biblioteca.dto.LivroResponseDTO;
import UFRN.com.biblioteca.exceptions.LivroNaoEncontradoException;
import UFRN.com.biblioteca.services.LivroService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
@Tag(name = "Livros", description = "Gerenciamento dos livros da biblioteca")
public class LivroController {

    private final LivroService livroService;

    @GetMapping
    @Operation(summary = "Listar todos os livros", description = "Retorna uma lista paginada de todos os livros")
    public ResponseEntity<Page<LivroResponseDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(livroService.listarTodos(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable @NotBlank String id) {
        try {
            return ResponseEntity.ok(livroService.listarPorId(id));
        } catch (RuntimeException e) {
            throw new LivroNaoEncontradoException(id);
        }
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo livro")
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso")
    public ResponseEntity<LivroResponseDTO> salvar(@Valid @RequestBody LivroRequestDTO dto) {
        LivroResponseDTO salvo = livroService.salvar(dto);
        URI location = URI.create("/api/livros/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar livros por título ou autor")
    public ResponseEntity<List<LivroResponseDTO>> buscarPorTituloOuAutor(@RequestParam("q") String query) {
        return ResponseEntity.ok(livroService.buscarPorTituloOuAutor(query));
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar livros disponíveis para empréstimo")
    public ResponseEntity<List<LivroResponseDTO>> listarDisponiveis() {
        return ResponseEntity.ok(livroService.listarDisponiveis());
    }

    @GetMapping("/emprestados")
    @Operation(summary = "Listar livros emprestados atualmente")
    public ResponseEntity<List<LivroResponseDTO>> listarEmprestados() {
        return ResponseEntity.ok(livroService.listarEmprestados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um livro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<LivroResponseDTO> atualizar(
            @PathVariable String id,
            @Valid @RequestBody LivroRequestDTO dto) {
        LivroResponseDTO atualizado = livroService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um livro pelo ID")
    @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso")
    public ResponseEntity<Void> deletar(@PathVariable @NotBlank String id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
