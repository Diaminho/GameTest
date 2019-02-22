package com.example.game_test.services;

import com.example.game_test.enteties.Session;
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
    @Autowired
    SessionService sessionService;

    public AuthService() {
    }


    public String authUser(String login, String password, ModelMap modelMap){
        User user=userService.findUserByLogin(login);

        if(user!=null) {
            if(userService.checkUserInDB(user.getLogin(), user.getPassword())) {
                modelMap.put("login", login);
                Session  session=sessionService.findSessionByUserId(user.getId());
                if(session==null) {
                    session = sessionService.createSessionForUserId(user.getId());
                }
                modelMap.put("sessionId", session.getId());
                return "redirect:/menu";
            }
            else{
                return "auth";
            }
        }

        ///
        userService.addUser(login,password);
        return "auth";
        ///

    }

}
