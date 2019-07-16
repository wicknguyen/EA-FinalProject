package EA.Project.RabbitMQ.service;

import org.springframework.stereotype.Service;
import EA.Project.RabbitMQ.domain.Book;

import java.util.List;

@Service
public interface IBookService {

    List<Book> getAll();
    Book getOne(Integer id);
    Integer add(Book obj);
    void update(Book obj);
    void delete(Integer id);
}
