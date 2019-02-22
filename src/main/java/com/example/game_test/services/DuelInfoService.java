package com.example.game_test.services;

import com.example.game_test.enteties.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Service
public class DuelInfoService {
    @Autowired
    SessionService sessionService;

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
