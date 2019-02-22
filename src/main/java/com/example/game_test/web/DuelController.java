package com.example.game_test.web;

import com.example.game_test.services.DuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@SessionAttributes("login")
public class DuelController {
    @Autowired
    DuelService duelService;

    public DuelController() { }

    @RequestMapping("/duel")
    public ModelAndView showDuel(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        System.out.println();
        return new ModelAndView("duel", modelMap);
    }

    @RequestMapping("/search")
    public ModelAndView searchOpponent(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        return new ModelAndView(duelService.searchOpponent(modelMap, sessionId), modelMap);
    }

    @RequestMapping("/fight")
    public ModelAndView doDuel(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        return new ModelAndView(duelService.doFight(modelMap, sessionId), modelMap);
    }
}
