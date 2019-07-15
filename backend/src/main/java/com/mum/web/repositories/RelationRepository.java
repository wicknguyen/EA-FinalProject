package com.mum.web.repositories;

import com.mum.web.entities.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRepository extends JpaRepository<Relation, Integer> {
}
