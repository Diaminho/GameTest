package com.example.game_test.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {

    @RequestMapping("/menu")
    public String showRating(ModelMap modelMap, @ModelAttribute("login")String login){
        modelMap.put("login", login);
        return "menu";
    }
}
