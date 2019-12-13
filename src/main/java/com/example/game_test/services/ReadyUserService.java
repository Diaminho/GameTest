package com.example.game_test.services;

import com.example.game_test.entity.ReadyUser;
import com.example.game_test.repositories.ReadyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadyUserService {
    private final ReadyUserRepository readyUserRepository;

    public ReadyUserService(ReadyUserRepository readyUserRepository) {
        this.readyUserRepository = readyUserRepository;
    }


    public void addPlayerToReady(Long userId){
        readyUserRepository.save(new ReadyUser(userId));
    }

    public void deletePlayerFromReady(Long userId){
        readyUserRepository.deleteById(userId);
    }

    public List<ReadyUser> findAll(){
        return readyUserRepository.findAll();
    }
}
