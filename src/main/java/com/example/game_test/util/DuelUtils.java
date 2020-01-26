package com.example.game_test.util;

import com.example.game_test.entity.Duel;
import com.example.game_test.entity.Player;

public class DuelUtils {
    public static Long getDuelingPlayerHp(Long playerId, Duel duel) {
        return  (duel.getFirstPlayer().getId().compareTo(playerId) == 0) ? duel.getFirstPlayerHp(): duel.getSecondPlayerHp();
    }

    public static boolean checkIfDuelIsFinished(Duel duel) {
        return duel.getStatus().compareTo(Duel.Status.FINISHED) == 0;
    }

    public static boolean arePlayersHaveNotHp(Duel duel) {
        return duel.getFirstPlayerHp().compareTo(0L) <= 0 || duel.getSecondPlayerHp().compareTo(0L) <= 0;
    }

    public static Player getOpponentFromDuel(Long playerId, Duel duel) {
        return  (duel.getFirstPlayer().getId().compareTo(playerId) != 0) ? duel.getFirstPlayer(): duel.getSecondPlayer();
    }

}
