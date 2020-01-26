package com.example.game_test.service;

import com.example.game_test.entity.Player;
import com.example.game_test.repository.PlayerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player findUserByLogin(String login) {
        return this.playerRepository.findUserByLogin(login);
    }

    public Player findById(Long id) {
        return this.playerRepository.findById(id).orElse(null);
    }

    //add user
    public void addUser(String login, String password) {
        Player player = new Player.Builder(login, password).build();
        player.setPassword(encoder.encode(player.getPassword()));
        playerRepository.save(player);
    }

    public void updateUser(Player player) {
        playerRepository.save(player);
    }

    //check if user with login and password is presented in db
    public boolean authUser(String login, String password) {
        return encoder.matches(password, playerRepository.findUserByLogin(login).getPassword());
    }
}
