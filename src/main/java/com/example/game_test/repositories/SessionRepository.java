package com.example.game_test.repositories;

import com.example.game_test.enteties.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {
    Session findSessionById(Long id);
    List<Session> findAll();
    Session findSessionByUserId(Long userId);
}
