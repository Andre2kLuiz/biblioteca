package UFRN.com.biblioteca.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import UFRN.com.biblioteca.model.Livro;

public interface LivroRepository extends MongoRepository<Livro, String> {

    List<Livro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String titulo, String autor);

    List<Livro> findByDisponivelTrue();

    List<Livro> findByDisponivelFalse();
}
