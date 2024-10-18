package com.penaestrada.repository;

import com.penaestrada.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    Optional<Workshop> findByUserLogin(String login);
}
