package EA.Project.RabbitMQ;

import java.util.List;

import javax.annotation.Resource;

import EA.Project.RabbitMQ.domain.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {
    @Resource
    private IBookDao bookDao;
    
    public List<Book> getAll() {
        return bookDao.findAll();
    }

    public void add(Book book) {
        bookDao.save(book);
    }

    public Book get(int id) {
        return bookDao.findById(id).get();
    }

    public void update(Book book) {
        bookDao.save(book);
    }

    public void delete(int id) {
        bookDao.deleteById(id);
    }
}