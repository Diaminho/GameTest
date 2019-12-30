package com.example.game_test.controller;

import com.example.game_test.service.AuthService;
import com.example.game_test.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("sessionId")
public class AuthController {
    private final AuthService authService;
    private final SessionService sessionService;

    public AuthController(AuthService authService, SessionService sessionService) {
        this.authService = authService;
        this.sessionService = sessionService;
    }

    @ModelAttribute("sessionId")
    public Long sessionId() {
        return -1L;
    }

    @PostMapping(value ={ "/auth", "/"})
    public ModelAndView doAuth(ModelMap modelMap,@RequestParam("login") String login, @RequestParam String password){
        if (login!=null && password!=null) {
            return new ModelAndView(authService.authUser(login, password, modelMap), modelMap);
        }
        else
            return new ModelAndView("auth");
    }

    @GetMapping(value ={ "/auth", "/"})
    public String getAuth(){
        return "auth";
    }

    @RequestMapping(value="/quit")
    public String endSession(ModelMap modelMap, @SessionAttribute("sessionId") Long sessionId){
        sessionService.deleteSession(sessionId);
        sessionId=null;
        return "redirect:/auth";
    }
}