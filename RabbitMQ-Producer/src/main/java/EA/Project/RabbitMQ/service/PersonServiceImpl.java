package EA.Project.RabbitMQ.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EA.Project.RabbitMQ.domain.Person;
import EA.Project.RabbitMQ.repository.PersonRepository;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private PersonRepository personRepository;
    @Override
    public List<Person> getAll() {
        //return personRepository.findAll();
        return personRepository.myFindAll();

    }
    @Override
    public Person getOne(Long id) {
        return personRepository.myFindPersonById(id);
        //return personRepository.findById(id).get();
    }
    @Override
    public Long addPerson(Person person) {
        personRepository.save(person);
        return person.getId();
    }
    @Override
    public void updatePerson(Person person) {
        personRepository.save(person);
    }
    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
