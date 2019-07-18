package EA.Project.RabbitMQ;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumerApplication {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);


    }

    @Bean
    public Queue userInfo() {
        return new Queue("RBUserInfo");
    }

    @Bean
    public Queue relationshipInfo() {
        return new Queue("RBUsers");
    }

    @Bean
    public Queue postInfo() {
        return new Queue("RBPost");
    }
}
