package com.example.game_test.util;

import com.example.game_test.dto.PlayerDTO;
import com.example.game_test.entity.Player;

public class PlayerUtils {
    public static PlayerDTO convertPlayerToDTO(Player player) {
        return new PlayerDTO(
                player.getId(),
                player.getLogin(),
                player.getHp(),
                player.getAttack(),
                player.getRating()
        );
    }

    public static Player changePlayerStats(Player player, boolean isWinner) {
        player.setAttack(player.getAttack() + 1);
        player.setHp(player.getHp() + 10);
        int rating =  isWinner ? 1: -1;
        player.setRating(player.getRating() + rating);
        return player;
    }

}
