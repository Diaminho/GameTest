package com.example.game_test.services;

import com.example.game_test.enteties.Duel;
import com.example.game_test.enteties.ReadyUser;
import com.example.game_test.enteties.User;
import com.example.game_test.repositories.DuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class DuelService {
    @Autowired
    private UserService userService;
    @Autowired
    private ReadyUserService readyUserService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private DuelRepository duelRepository;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ReadyUserService getReadyUserService() {
        return readyUserService;
    }

    public void setReadyUserService(ReadyUserService readyUserService) {
        this.readyUserService = readyUserService;
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public DuelRepository getDuelRepository() {
        return duelRepository;
    }

    public void setDuelRepository(DuelRepository duelRepository) {
        this.duelRepository = duelRepository;
    }

    //searching opponent
    public String searchOpponent(ModelMap modelMap, Long sessionId){
        User user=sessionService.findUserBySessionId(sessionId);
        Long userId=user.getId();

        if (userId!=null) {
            //add player to ready Users
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
                //deleting user and opponent from ready
                readyUserService.deletePlayerFromReady(userId);
                readyUserService.deletePlayerFromReady(opponentId);
                User opponent=userService.findById(opponentId);
                //creating duel
                Duel duel=new Duel(userId, user.getHp(), opponentId, opponent.getHp());
                duelRepository.save(duel);
                //put info to model
                modelMap.put("opponentLogin", opponent.getLogin());
                modelMap.put("login", opponent.getLogin());
                modelMap.put("opponentHp", opponent.getHp());
                return "forward:/duel";
            }
        }
        ////opponent not found
        modelMap.put("info", "Соперник не найден");
        modelMap.put("login", user.getLogin());
        modelMap.put("rating", user.getRating());
        return "duelInfo";
        ///

    }


    //battle
    public String getFight(ModelMap modelMap, Long sessionId){
        if(sessionId>0){
            Long duelId=getDuelIdFromSessionId(sessionId);
            //finding players from duelId
            String res=doAttack(modelMap, duelId);
            putInfoToModel(modelMap, duelId);
            return res;
        }
        return "redirect:/quit";
    }

    //battle info
    public String getFightInfo(ModelMap modelMap, Long sessionId){
        if(sessionId>0){
            Long duelId=getDuelIdFromSessionId(sessionId);
            putInfoToModel(modelMap,duelId);
            return "duel";
        }
        return "redirect:/quit";
    }

    //do attack and save result to duel table
    private String doAttack(ModelMap modelMap, Long duelId){
        Duel duel=duelRepository.getById(duelId);
        Long opponentHp=duelRepository.getById(duelId).getSecondPlayerHp();
        User user=getFirstPlayerByDuelId(duelId);
        User opponent=getSecondPlayerByDuelId(duelId);
        if (opponentHp<user.getAttack()){
            modelMap.put("info", "Конец дуэли");
            ///change stats
            //winner
            user.setAttack(user.getAttack()+1);
            user.setHp(user.getHp()+10);
            user.setRating(user.getRating()+1);
            userService.getUserRepository().save(user);
            //
            //looser
            opponent.setAttack(opponent.getAttack()+1);
            opponent.setHp(opponent.getHp()+10);
            opponent.setRating(opponent.getRating()-1);
            userService.getUserRepository().save(opponent);
            //duelRepository.delete(duel);
            //NEED TO DELETE DUEL
            return "redirect:/duelInfo";
        }
        else {
            duel.setSecondPlayerHp(duel.getSecondPlayerHp()-user.getAttack());
            duelRepository.save(duel);
            modelMap.put("info", user.getLogin()+" атаковал "+ opponent.getLogin() + " на " + user.getAttack() + " урона.");
        }
        return "duel";
    }

    //get duel_id from session_id
    public Long getDuelIdFromSessionId(Long sessionId){
        User user=sessionService.findUserBySessionId(sessionId);
        Duel duel=duelRepository.findByFirstPlayerId(user.getId());
        return duel.getId();
    }

    //find firstPlayer by duelId
    public User getFirstPlayerByDuelId(Long duelId){
        User user=userService.findById(duelRepository.getById(duelId).getFirstPlayerId());
        return user;
    }

    //find firstPlayer by duelId
    public User getSecondPlayerByDuelId(Long duelId){
        User user=userService.findById(duelRepository.getById(duelId).getSecondPlayerId());
        return user;
    }


    private void putInfoToModel(ModelMap modelMap, Long duelId){
        Duel duel=duelRepository.getById(duelId);
        User user=getFirstPlayerByDuelId(duelId);
        User opponent=getSecondPlayerByDuelId(duelId);
        //user info
        modelMap.put("login", user.getLogin());
        modelMap.put("userHp", duel.getFirstPlayerHp());
        modelMap.put("userHpBeforeDuel", user.getHp());
        //opponent info
        modelMap.put("opponentLogin", opponent.getLogin());
        modelMap.put("opponentHp", duel.getSecondPlayerHp());
        modelMap.put("opponentHpBeforeDuel", opponent.getHp());
    }

}
