package com.example.game_test.controller;

import com.example.game_test.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MenuController {
    final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping("/menu")
    public ModelAndView showRating(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        if (sessionId!=null) {
            return new ModelAndView(menuService.getMenu(modelMap,sessionId), modelMap);
        }
        else {
            return new ModelAndView("auth");
        }
    }
}