package com.example.game_test.controller;

import com.example.game_test.service.DuelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DuelController {
    private final DuelService duelService;

    public DuelController(DuelService duelService) {
        this.duelService = duelService;
    }

    @RequestMapping("/duel")
    public ModelAndView showDuel(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        return new ModelAndView(duelService.getFightInfo(modelMap, sessionId), modelMap);
    }

    @RequestMapping("/search")
    public ModelAndView searchOpponent(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        return new ModelAndView(duelService.searchOpponent(modelMap, sessionId), modelMap);
    }

    @RequestMapping("/updateSearchStatus")
    public ModelAndView updateSearchStatus(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId) {
        String result = duelService.updateSearchStatus(modelMap, sessionId);
        return new ModelAndView(result, modelMap);
    }

    @RequestMapping("/attack")
    public ModelAndView doDuel(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId) {
        return new ModelAndView(duelService.attack(modelMap, sessionId), modelMap);
    }

    @RequestMapping("/updateDuelStatus")
    public ModelAndView updateDuelStatus(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId) {
        //TODO fix updateDuelStatus
        return new ModelAndView(duelService.getFightInfo(modelMap, sessionId), modelMap);
    }
}
