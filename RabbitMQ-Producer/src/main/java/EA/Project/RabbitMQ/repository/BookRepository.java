package EA.Project.RabbitMQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import EA.Project.RabbitMQ.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {


}
