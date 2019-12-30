package com.example.game_test.service;

import com.example.game_test.entity.Player;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class MenuService {
    private final SessionService sessionService;

    public MenuService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public String getMenu(ModelMap modelMap, Long sessionId) {
        Player player = sessionService.findUserBySessionId(sessionId);
        modelMap.put("login", player.getLogin());
        modelMap.put("rating", player.getRating());
        return "menu";
    }
}