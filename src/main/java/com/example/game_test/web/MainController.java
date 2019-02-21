package com.example.game_test.web;

import com.example.game_test.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    ModelMap modelMap;

    @Autowired
    MainService mainService;

    @Autowired
    public MainController() { }

    @GetMapping("/main")
    public String getMainPage(ModelMap model) {
        model.put("name",mainService.doNoting());
        return "main";

    }

}
