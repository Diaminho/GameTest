package com.example.game_test.service;

import com.example.game_test.entity.Duel;
import com.example.game_test.entity.Player;
import com.example.game_test.entity.ReadyPlayer;
import com.example.game_test.repository.DuelRepository;
import com.example.game_test.util.PlayerUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class DuelService {
    private final PlayerService playerService;
    private final ReadyPlayerService readyPlayerService;
    private final SessionService sessionService;
    private final DuelRepository duelRepository;

    public DuelService(PlayerService playerService, ReadyPlayerService readyPlayerService, SessionService sessionService, DuelRepository duelRepository) {
        this.playerService = playerService;
        this.readyPlayerService = readyPlayerService;
        this.sessionService = sessionService;
        this.duelRepository = duelRepository;
    }


    //searching opponent
    public String searchOpponent(ModelMap modelMap, Long sessionId) {
        Player player = sessionService.findUserBySessionId(sessionId);
        Long userId = player.getId();
        if (userId != null) {
            readyPlayerService.addPlayerToReady(userId);
            if (findOpponent(modelMap, player)) {
                //return "forward:/duel";
                modelMap.put("isLoading", false);
            } else {
                modelMap.put("isLoading", true);
            }
        }
        modelMap.put("login", player.getLogin());
        modelMap.put("rating", player.getRating());
        return "duelInfo";
        ///
    }

    public String retrySearch (ModelMap modelMap, Long sessionId) {
        Player player = sessionService.findUserBySessionId(sessionId);
        Long userId = player.getId();
        if (userId != null) {
            if (findOpponent(modelMap, player)) {
                //return "forward:/duel";
                modelMap.put("isLoading", false);
            } else {
                modelMap.put("isLoading", true);
            }
        }
        modelMap.put("login", player.getLogin());
        modelMap.put("rating", player.getRating());
        return "duelInfo";
    }

    private boolean findOpponent(ModelMap modelMap, Player player) {
        List<ReadyPlayer> readyPlayers = readyPlayerService.findAll();
        Long userId = player.getId();
        modelMap.put("player", PlayerUtils.convertPlayerToDAO(player));
        ReadyPlayer foundOpponent = readyPlayers.stream().filter(readyPlayer -> readyPlayer.getUserId().compareTo(player.getId()) != 0).findFirst().orElse(null);
        if (foundOpponent != null) {
            //deleting user and opponent from ready
            //readyPlayerService.deletePlayerFromReady(userId);
            //readyPlayerService.deletePlayerFromReady(opponentId);
            Long opponentId = foundOpponent.getUserId();
            Player opponent = playerService.findById(opponentId);
            //creating duel
            Duel duel = new Duel(userId, player.getHp(), opponentId, opponent.getHp());
            duelRepository.save(duel);
            //put info to model
            modelMap.put("opponent", PlayerUtils.convertPlayerToDAO(opponent));
            return true;
        }
        ////opponent not found
        return false;
    }

    public String updateSearchInfo(ModelMap modelMap, Long sessionId) {
        Player player = sessionService.findUserBySessionId(sessionId);
        return "test";
    }


    //battle
    public String getFight(ModelMap modelMap, Long sessionId) {
        if (sessionId > 0) {
            Long duelId = getDuelIdFromSessionId(sessionId);
            //finding players from duelId
            String res = doAttack(modelMap, duelId);
            putInfoToModel(modelMap, duelId);
            return res;
        }
        return "redirect:/quit";
    }

    //battle info
    public String getFightInfo(ModelMap modelMap, Long sessionId) {
        if (sessionId > 0) {
            Long duelId = getDuelIdFromSessionId(sessionId);
            putInfoToModel(modelMap, duelId);
            return "duel";
        }
        return "redirect:/quit";
    }

    //do attack and save result to duel table
    private String doAttack(ModelMap modelMap, Long duelId) {
        Duel duel = duelRepository.getById(duelId);
        Long opponentHp = duelRepository.getById(duelId).getSecondPlayerHp();
        Player player = getFirstPlayerByDuelId(duelId);
        Player opponent = getSecondPlayerByDuelId(duelId);
        if (opponentHp < player.getAttack()) {
            modelMap.put("info", "Конец дуэли");
            ///change stats
            //winner
            player.setAttack(player.getAttack() + 1);
            player.setHp(player.getHp() + 10);
            player.setRating(player.getRating() + 1);
            playerService.updateUser(player);
            //
            //loser
            opponent.setAttack(opponent.getAttack() + 1);
            opponent.setHp(opponent.getHp() + 10);
            opponent.setRating(opponent.getRating() - 1);
            playerService.updateUser(opponent);
            //duelRepository.delete(duel);
            //NEED TO DELETE DUEL
            return "redirect:/duelInfo";
        } else {
            duel.setSecondPlayerHp(duel.getSecondPlayerHp() - player.getAttack());
            duelRepository.save(duel);
            modelMap.put("info", player.getLogin() + " атаковал " + opponent.getLogin() + " на " + player.getAttack() + " урона.");
        }
        return "duel";
    }

    //get duel_id from session_id
    public Long getDuelIdFromSessionId(Long sessionId) {
        Player player = sessionService.findUserBySessionId(sessionId);
        Duel duel = duelRepository.findByFirstPlayerId(player.getId());
        return duel.getId();
    }

    //find firstPlayer by duelId
    public Player getFirstPlayerByDuelId(Long duelId) {
        return playerService.findById(duelRepository.getById(duelId).getFirstPlayerId());
    }

    //find firstPlayer by duelId
    public Player getSecondPlayerByDuelId(Long duelId) {
        return playerService.findById(duelRepository.getById(duelId).getSecondPlayerId());
    }


    private void putInfoToModel(ModelMap modelMap, Long duelId) {
        Duel duel = duelRepository.getById(duelId);
        Player player = getFirstPlayerByDuelId(duelId);
        Player opponent = getSecondPlayerByDuelId(duelId);
        //user info
        //TODO fix temporal change for players hp
        modelMap.put("login", player.getLogin());
        modelMap.put("userHp", duel.getFirstPlayerHp());
        modelMap.put("userHpBeforeDuel", player.getHp());
        //opponent info
        modelMap.put("opponentLogin", opponent.getLogin());
        modelMap.put("opponentHp", duel.getSecondPlayerHp());
        modelMap.put("opponentHpBeforeDuel", opponent.getHp());
    }

}
