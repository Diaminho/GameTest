package com.example.game_test.services;

import com.example.game_test.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class MenuService {
    @Autowired
    private SessionService sessionService;

    public MenuService() {
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public String getMenu(ModelMap modelMap, Long sessionId){
        User user=sessionService.findUserBySessionId(sessionId);
        modelMap.put("login", user.getLogin());
        modelMap.put("rating", user.getRating());
        return "menu";
    }
}