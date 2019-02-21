package com.example.game_test.services;

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
    UserService userService;

    public DuelInfoService() {
    }

    public String showRating(ModelMap modelMap, String login){
        if (!login.equals("")) {
            modelMap.put("rating", userService.findUserByLogin(login).getRating());
            return "duelInfo";
        }

        return "auth";
        //return "auth";
    }
}
