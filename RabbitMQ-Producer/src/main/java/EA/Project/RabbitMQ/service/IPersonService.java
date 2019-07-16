package EA.Project.RabbitMQ.service;

import org.springframework.stereotype.Service;
import EA.Project.RabbitMQ.domain.Person;

import java.util.List;

@Service
public interface IPersonService {

    List<Person> getAll();
    Person getOne(Long id);
    Long addPerson(Person person);
    void updatePerson(Person person);
    void deletePerson(Long id);
}
