package com.mum.web.repositories;

import com.mum.web.entities.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<Interaction, String> {
}
