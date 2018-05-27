package sh.fable;

import sh.fable.book.BookService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author Dominic Gunn
 */
@SpringBootApplication
public class Launcher {

	public static void main(String[] args) {
		final ApplicationContext applicationContext = SpringApplication.run(Launcher.class, args);

		applicationContext.getBean(BookService.class).save("Test Author");
		applicationContext.getBean(BookService.class).get(1);

		applicationContext.getBean(BookService.class).save("Test Author2");
		applicationContext.getBean(BookService.class).get(2);
	}
}
