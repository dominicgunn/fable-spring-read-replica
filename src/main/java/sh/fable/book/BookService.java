package sh.fable.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dominic Gunn
 */
@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Book save(final String author) {
		return bookRepository.save(new Book(author));
	}

	@Transactional(readOnly = true)
	public Book get(final Integer id) {
		return bookRepository.findById(id).orElse(null);
	}
}
