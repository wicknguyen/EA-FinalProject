package EA.Project.RabbitMQ;

import EA.Project.RabbitMQ.domain.Person;
import EA.Project.RabbitMQ.service.BookServiceImpl;
import EA.Project.RabbitMQ.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private PersonServiceImpl personService;
	@Autowired
	private BookServiceImpl bookService;

	public static void main(String[] args) {


		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		DemoApplication main = context.getBean(DemoApplication.class);
		main.test();




	}

	public void test()
	{
		//bookService.add(new Book("book1","1234","Bao Tran",10));
		//bookService.add(new Book("book2","12345","Bao Nguyen",10));


		personService.addPerson(new Person("Bao","Tran",12));
		personService.addPerson(new Person("Bao","Nguyen",20));

		System.out.println(personService.getAll());
		System.out.println(bookService.getAll());
	}

}
