package com.example.game_test.web;

import com.example.game_test.repositories.UserRepository;
import com.example.game_test.services.AuthService;
import com.example.game_test.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("sessionId")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    SessionService sessionService;

    public AuthController() {
    }


    @ModelAttribute("sessionId")
    public String sessionId() {
        return " ";
    }


    @PostMapping(value ={ "/auth", "/"})
    public ModelAndView doAuth(ModelMap modelMap,@RequestParam("login") String login, @RequestParam String password){
        if (login!=null && password!=null) {
            //modelMap.put("login", login);
            //return new ModelAndView("menu", modelMap);
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
        return "auth";
    }
}
