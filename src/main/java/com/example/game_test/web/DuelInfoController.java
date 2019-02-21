package com.example.game_test.web;

import com.example.game_test.services.DuelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@SessionAttributes("login")
public class DuelInfoController {
    @Autowired
    DuelInfoService duelInfoService;

    public DuelInfoController() { }

    @RequestMapping("/duelInfo")
    public String showRating(ModelMap modelMap, @ModelAttribute("login") String login){
        return duelInfoService.showRating(modelMap, login);
    }
}
