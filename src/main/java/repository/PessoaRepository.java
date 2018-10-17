package repository;

import java.util.List;
import model.Pessoa;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Muriel
 */
public interface PessoaRepository extends MongoRepository<Pessoa, String> {

public List<Pessoa> findByNomeLikeIgnoreCase(String nome);
public Pessoa findByIdPessoa(String idPessoa);
}
