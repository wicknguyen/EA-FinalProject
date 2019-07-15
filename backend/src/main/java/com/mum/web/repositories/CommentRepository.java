package com.mum.web.repositories;

import com.mum.web.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
//    @Query("SELECT p FROM Person p WHERE p.age>15")
//    List<Person> myFindAll();
}
