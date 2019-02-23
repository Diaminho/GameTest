package com.example.game_test.repositories;

import com.example.game_test.enteties.Duel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DuelRepository extends CrudRepository<Duel, Long> {
    List<Duel> findAll();
    Duel findByFirstPlayerId(Long firstPlayerId);
    Duel getById(Long id);
}
