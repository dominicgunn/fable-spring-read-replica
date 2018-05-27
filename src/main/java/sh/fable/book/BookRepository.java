package sh.fable.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dominic Gunn
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

}
