package sh.fable.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Dominic Gunn
 */
@Entity
@Table(name = "books")
public class Book {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "author", nullable = false)
	private String author;

	public Book() {

	}

	public Book(String author) {
		this.author = author;
	}

	public Integer getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}
}
