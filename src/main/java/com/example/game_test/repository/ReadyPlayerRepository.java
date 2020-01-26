package com.example.game_test.repository;

import com.example.game_test.entity.ReadyPlayer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReadyPlayerRepository extends CrudRepository<ReadyPlayer, Long> {
    ReadyPlayer findByPlayerId(Long playerId);
    List<ReadyPlayer> findAll();
}
