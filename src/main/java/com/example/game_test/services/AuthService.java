package com.example.game_test.services;

import com.example.game_test.enteties.User;
import com.example.game_test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    UserService userService;

    public AuthService() {
    }


    public String authUser(String login, String password, ModelMap modelMap){
        if(userService.findUserByLogin(login)!=null) {
            if(userService.checkUserInDB(login, password)) {
                modelMap.put("login", login);
                return "redirect:/menu";
            }
        }

        ///
        userService.addUser(login,password);
        return "auth";
        ///

    }

}
