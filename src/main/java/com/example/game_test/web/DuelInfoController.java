package com.example.game_test.web;

import com.example.game_test.services.DuelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@SessionAttributes("login")
public class DuelInfoController {
    @Autowired
    DuelInfoService duelInfoService;

    public DuelInfoController() { }

    @RequestMapping("/duelInfo")
    public ModelAndView showRating(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        //modelMap.put("sessionId", sessionId);
        System.out.println();
        return new ModelAndView(duelInfoService.showRating(modelMap, sessionId), modelMap);
    }
}
