package com.example.game_test.repository;

import com.example.game_test.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    List<Player> findAll();
    Player findUserByLogin(String login);
}
