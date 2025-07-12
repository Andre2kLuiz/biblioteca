package UFRN.com.biblioteca.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import UFRN.com.biblioteca.model.Emprestimo;

public interface EmprestimoRepository extends MongoRepository<Emprestimo, String> {
    List<Emprestimo> findByAlunoId(String alunoId);

    boolean existsByAlunoIdAndLivroIdAndDevolvidoFalse(String alunoId, String livroId);

    boolean existsByLivroIdAndDevolvidoFalse(String livroId);

}
