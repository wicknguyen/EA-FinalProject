package EA.Project.RabbitMQ.listener;

import javax.validation.ConstraintViolationException;

import EA.Project.RabbitMQ.BookService;
import EA.Project.RabbitMQ.domain.Book;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "RBbook")
public class BookListener {
    @Autowired
    private BookService bookService;

    @RabbitHandler
    public void save(@Payload Book book, @Header("operation") String operation) {
        System.out.println("received: " + book.getTitle());
        try {
            if (operation.equals("add")) {
                bookService.add(book);
            } else if (operation.equals("update")) {
                bookService.update(book);
            }
        } catch (ConstraintViolationException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @RabbitHandler
    public void delete(Integer id) {
        bookService.delete(id);
    }
}