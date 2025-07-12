package UFRN.com.biblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now());
        erro.put("mensagem", ex.getMessage());
        erro.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(LivroNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleLivroNaoEncontrado(LivroNaoEncontradoException ex) {
        return gerarErro(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LivroIndisponivelException.class)
    public ResponseEntity<Map<String, Object>> handleLivroIndisponivel(LivroIndisponivelException ex) {
        return gerarErro(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AlunoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleAlunoNaoEncontrado(AlunoNaoEncontradoException ex) {
        return gerarErro(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(EmprestimoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleEmprestimoNaoEmcontrado(EmprestimoNaoEncontradoException ex) {
        return gerarErro(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> gerarErro(HttpStatus status, String mensagem) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now());
        erro.put("mensagem", mensagem);
        erro.put("status", status.value());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(AlunoComEmprestimoAtrasadoException.class)
    public ResponseEntity<Map<String, Object>> handleAtraso(AlunoComEmprestimoAtrasadoException ex) {
        return gerarErro(HttpStatus.FORBIDDEN, ex.getMessage());
    }

}
