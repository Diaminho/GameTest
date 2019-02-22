package com.example.game_test.web;

import com.example.game_test.services.SessionService;
import com.example.game_test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MenuController {
    @Autowired
    SessionService sessionService;


    @RequestMapping("/menu")
    public ModelAndView showRating(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        if (sessionId!=null) {
            modelMap.put("login", sessionService.findUserBySessionId(sessionId).getLogin());
            return new ModelAndView("menu", modelMap);
        }
        else {
            return new ModelAndView("auth");
        }
    }

}
