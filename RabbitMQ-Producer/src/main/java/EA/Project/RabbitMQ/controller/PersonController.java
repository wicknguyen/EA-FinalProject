package EA.Project.RabbitMQ.controller;

import EA.Project.RabbitMQ.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import EA.Project.RabbitMQ.domain.Person;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private PersonServiceImpl personService;
    @GetMapping("/persons")
    public List<Person> getAll() {
        return personService.getAll();
    }
    @GetMapping("/person/{id}")
    private Person getOne(@PathVariable Long id, Model model) {
        return personService.getOne(id);
    }
    @PutMapping(value = "/person/{id}")
    public void put(@PathVariable Long id, @RequestBody Person person) {
        if (id != person.getId()) {
            throw new IllegalArgumentException();
        }
        personService.updatePerson(person);
    }
    @DeleteMapping("/person/{id}")
    public void delete(@PathVariable long id) {
        personService.deletePerson(id);
    }

    @RequestMapping(value = "person/redirect", method = RequestMethod.POST)
    public RedirectView post(@RequestBody Person person) {
        long id = personService.addPerson(person);
        return new RedirectView("/person/" + id);
    }
    @RequestMapping(value = "person/add", method = RequestMethod.POST)
    public @ResponseBody Long add(@RequestBody Person person) {
        Long id = personService.addPerson(person);
        return id;
    }
}
