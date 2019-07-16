package EA.Project.RabbitMQ.service;

import EA.Project.RabbitMQ.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EA.Project.RabbitMQ.domain.Book;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> getAll() {

        return bookRepository.findAll();

    }
    @Override
    public Book getOne(Integer id) {
        return bookRepository.findById(id).get();
    }
    @Override
    public Integer add(Book obj) {
        bookRepository.save(obj);// .save(obj);
        return 1;
        //return obj.getId();
    }
    @Override
    public void update(Book obj) {
        bookRepository.save(obj);
    }
    @Override
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }
}
