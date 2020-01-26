package com.example.game_test.service;

import com.example.game_test.entity.Session;
import com.example.game_test.entity.Player;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class AuthService {
    private final PlayerService playerService;
    private final SessionService sessionService;

    public AuthService(PlayerService playerService, SessionService sessionService) {
        this.playerService = playerService;
        this.sessionService = sessionService;
    }

    //auth user
    public String authUser(String login, String password, ModelMap modelMap) {
        //check if null or empty params are presented
        if (StringUtils.trim(login).isBlank() || StringUtils.trim(password).isBlank()) {
            return "auth";
        }
        Player player = playerService.findUserByLogin(login);
        if (player != null) {
            boolean authStatus = playerService.authUser(login, password);
            if (authStatus) {
                //if user with login and pass found
                Session session = sessionService.findSessionByUserId(player.getId());
                if (session == null) {
                    session = sessionService.createSessionForUserId(player.getId());
                }
                modelMap.put("sessionId", session.getId());
                return "redirect:/menu";
            } else {
                modelMap.put("info", "Password is incorrect");
                return "auth";
            }
        }
        ///if user not found create new user
        playerService.addUser(login, password);
        modelMap.put("info", "User " + login + " created");
        return "auth";
    }
}
