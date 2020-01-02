package com.example.game_test.repository;

import com.example.game_test.entity.Duel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DuelRepository extends CrudRepository<Duel, Long> {
    List<Duel> findAll();
    Duel findByFirstPlayerId(Long firstPlayerId);
    Duel findBySecondPlayerId(Long secondPlayerId);
    Duel getById(Long id);
}
