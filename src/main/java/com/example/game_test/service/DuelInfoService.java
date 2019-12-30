package com.example.game_test.service;

import com.example.game_test.entity.Player;
import com.example.game_test.util.PlayerUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class DuelInfoService {
    private final SessionService sessionService;

    public DuelInfoService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public String showRating(ModelMap modelMap, Long sessionId) {
        if (sessionId > 0) {
            Player player = sessionService.findUserBySessionId(sessionId);
            if (player != null) {
                modelMap.put("player", PlayerUtils.convertPlayerToDAO(player));
                modelMap.put("sessionId", sessionId);
                return "duelInfo";
            }
        }
        return "redirect:/auth";
    }
}
