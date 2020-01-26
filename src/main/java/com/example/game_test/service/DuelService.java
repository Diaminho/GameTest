package com.example.game_test.service;

import com.example.game_test.dto.PlayerDTO;
import com.example.game_test.entity.Duel;
import com.example.game_test.entity.Player;
import com.example.game_test.entity.ReadyPlayer;
import com.example.game_test.repository.DuelRepository;
import com.example.game_test.util.PlayerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Arrays;
import java.util.List;

import static com.example.game_test.util.DuelUtils.*;
import static com.example.game_test.util.PlayerUtils.changePlayerStats;

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
                readyPlayerService.deletePlayerFromReady(userId);
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
        Player player = sessionService.findUserBySessionId(sessionId);
        Long playerId = player.getId();
        if (playerId != null) {
            if (findOpponent(modelMap, player)) {
                readyPlayerService.deletePlayerFromReady(playerId);
                return "forward:/duel";
            } else {
                modelMap.put("isLoading", true);
            }
        }
        modelMap.put("login", player.getLogin());
        modelMap.put("rating", player.getRating());
        return "duelInfo";
    }

    private boolean findOpponent(ModelMap modelMap, Player player) {
        long playerId = player.getId();
        //check if duel already exists
        Duel duel = findDuelByPlayerId(playerId);
        System.out.println("duel: " + duel + " playerId:" + playerId);
        modelMap.put("player", PlayerUtils.convertPlayerToDTO(player));
        if (duel != null) {
            Player opponent = getOpponentFromDuel(playerId, duel);
            modelMap.put("opponent", PlayerUtils.convertPlayerToDTO(opponent));
            return true;
        }
        ReadyPlayer foundReadyPlayer = readyPlayerService.findByPlayerId(playerId);
        if (foundReadyPlayer == null) {
            return false;
        }
        ReadyPlayer foundOpponent = readyPlayerService.findAll().stream()
                .filter(readyPlayer -> readyPlayer.getPlayerId().compareTo(playerId) != 0)
                .findFirst()
                .orElse(null);
        if (foundOpponent != null) {
            Long opponentId = foundOpponent.getPlayerId();
            Player opponent = playerService.findById(opponentId);
            //creating duel if it doesn't exist
            if (duelRepository.findByFirstPlayerId(playerId) == null && duelRepository.findBySecondPlayerId(playerId) == null) {
                duel = new Duel(player, player.getHp(), opponent, opponent.getHp(), Duel.Status.INITIATED);
                duelRepository.save(duel);
            }
            //put info to model
            modelMap.put("opponent", PlayerUtils.convertPlayerToDTO(opponent));
            return true;
        }
        //opponent is not found
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
            Duel duel = duelRepository.getById(getDuelIdFromSessionId(sessionId));
            if (checkIfDuelIsFinished(duel)) {
                return "redirect:/menu";
            }
            if (arePlayersHaveNotHp(duel)) {
                duel.setStatus(Duel.Status.FINISHED);
                return "redirect:/menu";
            }
            putInfoToModel(modelMap, sessionId);
            return "duel";
        }
        return "redirect:/quit";
    }


    //battle info
    public String setDuelInProgress(ModelMap modelMap, Long sessionId) {
        if (sessionId > 0) {
            Duel duel = duelRepository.getById(getDuelIdFromSessionId(sessionId));
            if (duel.getStatus().compareTo(Duel.Status.INITIATED) == 0) {
                duel.setStatus(Duel.Status.IN_PROGRESS);
                duelRepository.save(duel);
            }
            return getFightInfo(modelMap, sessionId);
        }
        return "redirect:/quit";
    }


    //do attack and save result to duel table
    private String doAttack(ModelMap modelMap, Long sessionId) {
        Duel duel = duelRepository.getById(getDuelIdFromSessionId(sessionId));
        if (checkIfDuelIsFinished(duel)) {
            return "redirect:/menu";
        }
        List<Player> players = getPlayerAndOpponentFromDuel(sessionId, duel);
        Player player =  players.get(0);
        Player opponent =  players.get(1);
        //is duel need to finish
        if (getDuelingPlayerHp(opponent.getId(), duel) < player.getAttack()) {
            duel.setStatus(Duel.Status.FINISHED);
            duelRepository.save(duel);
            modelMap.put("info", "Конец дуэли");
            changePlayersStats(player, opponent);
            return "redirect:/duelInfo";
        } else {
            changeOpponentHp(player, duel);
            if (getDuelingPlayerHp(opponent.getId(), duel) < player.getAttack()) {
                duel.setStatus(Duel.Status.FINISHED);
                duelRepository.save(duel);
                modelMap.put("info", "Конец дуэли");
                changePlayersStats(player, opponent);
                return "redirect:/duelInfo";
            }
            putInfoToModel(modelMap, sessionId);
            String log = player.getLogin() + " атаковал " + opponent.getLogin() + " на " + player.getAttack() + " урона.\n";
            if (!StringUtils.isEmpty(duel.getLog())) {
                duel.setLog(duel.getLog() + log);
            } else {
                duel.setLog(log);
            }
            duelRepository.save(duel);
            modelMap.put("info", log);

        }
        return "redirect:/duel";
    }

    //get duel_id from session_id
    public Long getDuelIdFromSessionId(Long sessionId) {
        Player player = sessionService.findUserBySessionId(sessionId);
        Duel foundDuel = duelRepository.findByFirstPlayerId(player.getId());
        foundDuel = (foundDuel == null) ? duelRepository.findBySecondPlayerId(player.getId()): foundDuel;
        return foundDuel.getId();
    }

    private void putInfoToModel(ModelMap modelMap, Long sessionId) {
        Duel duel = duelRepository.getById(getDuelIdFromSessionId(sessionId));
        List<Player> players = getPlayerAndOpponentFromDuel(sessionId, duel);
        //player info
        PlayerDTO player = PlayerUtils.convertPlayerToDTO(players.get(0));
        modelMap.put("playerHpBeforeDuel", player.getHp());
        player.setHp(getDuelingPlayerHp(player.getId(), duel));
        modelMap.put("player", player);
        //opponent info
        PlayerDTO opponent = PlayerUtils.convertPlayerToDTO(players.get(1));
        modelMap.put("opponentHpBeforeDuel", opponent.getHp());
        opponent.setHp(getDuelingPlayerHp(opponent.getId(), duel));
        modelMap.put("opponent", opponent);
        modelMap.put("status", duel.getStatus().name());
        modelMap.put("info", duel.getLog());
    }


    /**
     * Get Player List from duel
     * @param sessionId
     * @param duel
     * @return list of players in duel. First item in ArrayList is a Player. Second item is an opponent.
     */
    private List<Player> getPlayerAndOpponentFromDuel(Long sessionId, Duel duel) {
        Player player = sessionService.findUserBySessionId(sessionId);
        Player opponent = duel.getFirstPlayer();
        opponent = (opponent.getId().compareTo(player.getId()) == 0) ? duel.getSecondPlayer(): opponent;
        return Arrays.asList(player, opponent);
    }



    private void changeOpponentHp(Player player, Duel duel) {
        if (duel.getFirstPlayer().getId().compareTo(player.getId()) != 0) {
            duel.setFirstPlayerHp(duel.getFirstPlayerHp() - player.getAttack());
        } else {
            duel.setSecondPlayerHp(duel.getSecondPlayerHp() - player.getAttack());
        }
        duelRepository.save(duel);
    }

    private void changePlayersStats(Player winner, Player loser) {
        //winner
        playerService.updateUser(changePlayerStats(winner, true));
        //loser
        playerService.updateUser(changePlayerStats(loser, false));
    }

    private Duel findDuelByPlayerId(Long playerId) {
        return duelRepository.findAll().stream().filter(duel -> !checkIfDuelIsFinished(duel) &&
                (duel.getFirstPlayer().getId().compareTo(playerId) == 0 || duel.getSecondPlayer().getId().compareTo(playerId) == 0)).findFirst().orElse(null);
    }

}
