package UFRN.com.biblioteca.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "emprestimos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emprestimo {

    @Id
    private String id;

    private String livroId;
    private String alunoId;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    private boolean devolvido;
}
