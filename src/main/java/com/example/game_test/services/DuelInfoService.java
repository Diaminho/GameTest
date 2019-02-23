package com.example.game_test.services;

import com.example.game_test.enteties.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class DuelInfoService {
    @Autowired
    private SessionService sessionService;

    public SessionService getSessionService() {
        return sessionService;
    }

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public DuelInfoService() {
    }

    public String showRating(ModelMap modelMap, Long sessionId){
        if (sessionId>0) {
            User user=sessionService.findUserBySessionId(sessionId);
            if (user!=null) {
                modelMap.put("rating", user.getRating());
                modelMap.put("sessionId", sessionId);
                modelMap.put("login", user.getLogin());
                return "duelInfo";
            }
        }
        return "redirect:/auth";
    }
}
