package UFRN.com.biblioteca.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import UFRN.com.biblioteca.model.Aluno;

public interface AlunoRepository extends MongoRepository<Aluno, String> {

}
