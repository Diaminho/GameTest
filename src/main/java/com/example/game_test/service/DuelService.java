package com.example.game_test.service;

import com.example.game_test.dto.PlayerDTO;
import com.example.game_test.entity.Duel;
import com.example.game_test.entity.Player;
import com.example.game_test.entity.ReadyPlayer;
import com.example.game_test.repository.DuelRepository;
import com.example.game_test.util.PlayerUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Arrays;
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
    }

    public String updateSearchStatus(ModelMap modelMap, Long sessionId) {
        System.out.println("sessionId: " + sessionId);
        Player player = sessionService.findUserBySessionId(sessionId);
        Long userId = player.getId();
        if (userId != null) {
            if (findOpponent(modelMap, player)) {
                return "forward:/duel";
                //modelMap.put("isLoading", false);
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
        modelMap.put("player", PlayerUtils.convertPlayerToDTO(player));
        ReadyPlayer foundOpponent = readyPlayers.stream().filter(readyPlayer -> readyPlayer.getUserId().compareTo(player.getId()) != 0).findFirst().orElse(null);
        if (foundOpponent != null) {
            //deleting user and opponent from ready
            //readyPlayerService.deletePlayerFromReady(userId);
            Long opponentId = foundOpponent.getUserId();
            //readyPlayerService.deletePlayerFromReady(opponentId);
            Player opponent = playerService.findById(opponentId);
            //creating duel if it doesn't exist
            if (duelRepository.findByFirstPlayerId(player.getId()) == null && duelRepository.findBySecondPlayerId(player.getId()) == null) {
                Duel duel = new Duel(userId, player.getHp(), opponentId, opponent.getHp());
                duelRepository.save(duel);
            }
            //put info to model
            modelMap.put("opponent", PlayerUtils.convertPlayerToDTO(opponent));
            return true;
        }
        ////opponent not found
        return false;
    }

    //battle
    public String attack(ModelMap modelMap, Long sessionId) {
        if (sessionId > 0) {
            //finding players from duelId
            String res = doAttack(modelMap, sessionId);
            putInfoToModel(modelMap, sessionId);
            return res;
        }
        return "redirect:/quit";
    }

    //battle info
    public String getFightInfo(ModelMap modelMap, Long sessionId) {
        if (sessionId > 0) {
            putInfoToModel(modelMap, sessionId);
            return "duel";
        }
        return "redirect:/quit";
    }

    //do attack and save result to duel table
    private String doAttack(ModelMap modelMap, Long sessionId) {
        Duel duel = duelRepository.getById(getDuelIdFromSessionId(sessionId));
        //TODO check Status of Duel
        List<Player> players = getPlayerAndOpponentFromDuel(sessionId, duel);
        Player player =  players.get(0);
        Player opponent =  players.get(1);
        if (getDuelingPlayerHp(opponent.getId(), duel) < player.getAttack()) {
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
            return "redirect:/duelInfo";
        } else {
            //TODO fix hp changing
            changeOpponentHp(player, duel);
            putInfoToModel(modelMap, sessionId);
            modelMap.put("info", player.getLogin() + " атаковал " + opponent.getLogin() + " на " + player.getAttack() + " урона.");
        }
        return "duel";
    }

    //get duel_id from session_id
    public Long getDuelIdFromSessionId(Long sessionId) {
        Player player = sessionService.findUserBySessionId(sessionId);
        Duel foundDuel = duelRepository.findByFirstPlayerId(player.getId());
        foundDuel = (foundDuel == null) ? duelRepository.findBySecondPlayerId(player.getId()): foundDuel;
        return foundDuel.getId();
    }

    //find firstPlayer by duelId
    public Player getFirstPlayerByDuelId(Long duelId) {
        return playerService.findById(duelRepository.getById(duelId).getFirstPlayerId());
    }

    //find firstPlayer by duelId
    public Player getSecondPlayerByDuelId(Long duelId) {
        return playerService.findById(duelRepository.getById(duelId).getSecondPlayerId());
    }


    private void putInfoToModel(ModelMap modelMap, Long sessionId) {
        Duel duel = duelRepository.getById(getDuelIdFromSessionId(sessionId));
        List<Player> players = getPlayerAndOpponentFromDuel(sessionId, duel);
        //user info
        PlayerDTO player = PlayerUtils.convertPlayerToDTO(players.get(0));
        modelMap.put("playerHpBeforeDuel", player.getHp());
        player.setHp(getDuelingPlayerHp(player.getId(), duel));
        modelMap.put("player", player);
        //opponent info
        PlayerDTO opponent = PlayerUtils.convertPlayerToDTO(players.get(1));
        modelMap.put("opponentHpBeforeDuel", opponent.getHp());
        opponent.setHp(getDuelingPlayerHp(opponent.getId(), duel));
        modelMap.put("opponent", opponent);
    }

    private List<Player> getPlayerAndOpponentFromDuel(Long sessionId, Duel duel) {
        Player player = sessionService.findUserBySessionId(sessionId);
        Long opponentId = duel.getFirstPlayerId();
        opponentId = (opponentId.compareTo(player.getId()) == 0) ? duel.getSecondPlayerId(): opponentId;
        Player opponent =  playerService.findById(opponentId);
        return Arrays.asList(player, opponent);
    }

    private Long getDuelingPlayerHp(Long playerId, Duel duel) {
        return  (duel.getFirstPlayerId().compareTo(playerId) == 0) ? duel.getFirstPlayerHp(): duel.getSecondPlayerHp();
    }

    private void changeOpponentHp(Player player, Duel duel) {
        if (duel.getFirstPlayerId().compareTo(player.getId()) != 0) {
            duel.setFirstPlayerHp(duel.getFirstPlayerHp() - player.getAttack());
        } else {
            duel.setSecondPlayerHp(duel.getSecondPlayerHp() - player.getAttack());
        }
        duelRepository.save(duel);
    }

}
