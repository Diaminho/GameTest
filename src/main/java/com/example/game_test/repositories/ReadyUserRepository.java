package com.example.game_test.repositories;

import com.example.game_test.entity.ReadyUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReadyUserRepository extends CrudRepository<ReadyUser, Long> {
    ReadyUser findByUserId(Long playerId);
    List<ReadyUser> findAll();
}
