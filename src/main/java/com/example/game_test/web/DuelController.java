package com.example.game_test.web;

import com.example.game_test.services.DuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DuelController {
    @Autowired
    DuelService duelService;

    public DuelController() { }

    @RequestMapping("/duel")
    public ModelAndView showDuel(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        return new ModelAndView(duelService.getFightInfo(modelMap, sessionId), modelMap);
    }

    @RequestMapping("/search")
    public ModelAndView searchOpponent(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        return new ModelAndView(duelService.searchOpponent(modelMap, sessionId), modelMap);
    }

    @RequestMapping("/attack")
    public ModelAndView doDuel(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        return new ModelAndView(duelService.getFight(modelMap, sessionId), modelMap);
    }
}
