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
}
