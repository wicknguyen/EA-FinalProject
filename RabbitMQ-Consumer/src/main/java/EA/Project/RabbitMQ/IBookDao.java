package EA.Project.RabbitMQ;

import EA.Project.RabbitMQ.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookDao extends JpaRepository<Book, Integer> {
}