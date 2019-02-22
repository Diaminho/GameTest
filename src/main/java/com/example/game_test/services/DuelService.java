package com.example.game_test.services;

import com.example.game_test.enteties.ReadyUser;
import com.example.game_test.enteties.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class DuelService {
    @Autowired
    UserService userService;
    @Autowired
    ReadyUserService readyUserService;
    @Autowired
    SessionService sessionService;

    //searching opponent
    public String searchOpponent(ModelMap modelMap, Long sessionId){
        User user=sessionService.findUserBySessionId(sessionId);
        Long userId=user.getId();

        if (userId!=null) {
            readyUserService.addPlayerToReady(userId);
            List readyPlayers=readyUserService.findAll();
            Long opponentId=null;
            modelMap.put("userHp", user.getHp());
            for (Object ru: readyPlayers){
                if (((ReadyUser) ru).getUserId().compareTo(userId)!=0){
                    opponentId=((ReadyUser) ru).getUserId();
                    break;
                }
            }

            if(opponentId!=null){
                User opponent=userService.findById(opponentId);
                System.out.println("Opponent found: "+opponentId);
                modelMap.put("opponentLogin", opponent.getLogin());
                modelMap.put("login", opponent.getLogin());
                modelMap.put("opponentHp", opponent.getHp());
                return "forward:/duel";
            }
        }
        ////
        return "redirect:/auth";
        ///

    }


    //preparing to battle
    public String doFight(ModelMap modelMap, Long sessionId){
        if(sessionId>0){
            String opponentLogin=(String)modelMap.get("opponentLogin");
            User opponent=userService.findUserByLogin(opponentLogin);

            String login=(String)modelMap.get("login");
            User user=userService.findUserByLogin(login);

            doAttack(user, opponent);
            return "duel";

        }
        return "auth";
    }


    private void doAttack(User attackingUser, User attackedUser){
        Long damage=attackingUser.getAttack();
        attackedUser.setHp(attackedUser.getHp()-damage);
    }

}
