package EA.Project.RabbitMQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProducerApplication {

	public static void main(String[] args) {


		ApplicationContext context = SpringApplication.run(ProducerApplication.class, args);
		ProducerApplication main = context.getBean(ProducerApplication.class);

	}


}
