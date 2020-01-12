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
                Duel duel = new Duel(userId, player.getHp(), opponentId, opponent.getHp(), Duel.Status.INITIATED);
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
            //TODO Fix log storage
            String log = player.getLogin() + " атаковал " + opponent.getLogin() + " на " + player.getAttack() + " урона.\n\t";
            if (duel.getLog() != null) {
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
        modelMap.put("status", duel.getStatus().name());
        modelMap.put("info", duel.getLog());
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

    private boolean checkIfDuelIsFinished(Duel duel) {
        return duel.getStatus().compareTo(Duel.Status.FINISHED) == 0;
    }

    private boolean arePlayersHaveNotHp(Duel duel) {
        return duel.getFirstPlayerHp().compareTo(0L) <= 0 || duel.getSecondPlayerHp().compareTo(0L) <= 0;
    }

    private void changePlayersStats(Player winner, Player loser) {
        ///change stats
        //winner
        winner.setAttack(winner.getAttack() + 1);
        winner.setHp(winner.getHp() + 10);
        winner.setRating(winner.getRating() + 1);
        playerService.updateUser(winner);
        //
        //loser
        loser.setAttack(loser.getAttack() + 1);
        loser.setHp(loser.getHp() + 10);
        loser.setRating(loser.getRating() - 1);
        playerService.updateUser(loser);
    }

}
