package EA.Project.RabbitMQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import EA.Project.RabbitMQ.domain.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    @Query("SELECT p FROM Person p WHERE p.age>15")
    List<Person> myFindAll();
    @Query("SELECT p FROM Person p WHERE p.id= :personId")
    Person myFindPersonById(
            @Param("personId") Long pId);
            //@Param("name") String name);
}
