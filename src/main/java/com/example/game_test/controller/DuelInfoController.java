package com.example.game_test.controller;

import com.example.game_test.service.DuelInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DuelInfoController {
    private final DuelInfoService duelInfoService;

    public DuelInfoController(DuelInfoService duelInfoService) {
        this.duelInfoService = duelInfoService;
    }

    @RequestMapping("/duelInfo")
    public ModelAndView showRating(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        return new ModelAndView(duelInfoService.showRating(modelMap, sessionId), modelMap);
    }
}