package com.example.game_test.services;

import com.example.game_test.entity.Session;
import com.example.game_test.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public AuthService() {
    }

    //auth user
    public String authUser(String login, String password, ModelMap modelMap){
        //check if null params are presented
        if (login.compareTo("")==0 || password.compareTo("")==0){
            return "auth";
        }
        User user=userService.findUserByLogin(login);
        if(user!=null) {
            int authStatus=userService.checkUserInDB(login, password);
            if(authStatus==0) {
                //if user with login and pass found
                Session  session=sessionService.findSessionByUserId(user.getId());
                if(session!=null) {
                    modelMap.put("sessionId", session.getId());
                }
                //create session
                else {
                    session = sessionService.createSessionForUserId(user.getId());
                    modelMap.put("sessionId", session.getId());
                }
                return "redirect:/menu";
            }
            else if(authStatus==1){
                modelMap.put("info", "Password is incorrect");
                return "auth";
            }
        }
        ///if user not found create new user
        userService.addUser(login,password);
        modelMap.put("info", "User "+login+" created");
        return "auth";
        ///
    }
}
