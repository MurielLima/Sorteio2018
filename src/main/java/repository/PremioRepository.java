package repository;

import java.util.List;
import model.Premio;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Muriel
 */
public interface PremioRepository extends MongoRepository<Premio, String> {
public List<Premio> findByNomeLikeIgnoreCase(String nome);

}
