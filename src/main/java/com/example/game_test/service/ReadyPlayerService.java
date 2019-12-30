package com.example.game_test.service;

import com.example.game_test.entity.ReadyPlayer;
import com.example.game_test.repository.ReadyPlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadyPlayerService {
    private final ReadyPlayerRepository readyPlayerRepository;

    public ReadyPlayerService(ReadyPlayerRepository readyPlayerRepository) {
        this.readyPlayerRepository = readyPlayerRepository;
    }

    public void addPlayerToReady(Long userId){
        readyPlayerRepository.save(new ReadyPlayer(userId, ReadyPlayer.Status.SEARCHING));
    }

    public void deletePlayerFromReady(Long userId){
        readyPlayerRepository.deleteById(userId);
    }

    public List<ReadyPlayer> findAll(){
        return readyPlayerRepository.findAll();
    }
}
