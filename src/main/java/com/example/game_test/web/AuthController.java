package com.example.game_test.web;

import com.example.game_test.repositories.UserRepository;
import com.example.game_test.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("login")
public class AuthController {
    @Autowired
    AuthService authService;

    public AuthController() {
    }

    @ModelAttribute("login")
    public String login() {
        return " ";

    }


    @PostMapping(value ={ "/auth", "/"})
    public ModelAndView doAuth(ModelMap modelMap, @ModelAttribute("login") @RequestParam("login") String login, @RequestParam String password){
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
}
