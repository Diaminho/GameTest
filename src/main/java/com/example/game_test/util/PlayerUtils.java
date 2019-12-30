package com.example.game_test.util;

import com.example.game_test.dao.PlayerDao;
import com.example.game_test.entity.Player;

public class PlayerUtils {
    public static PlayerDao convertPlayerToDAO(Player player) {
        return new PlayerDao(
                player.getId(),
                player.getLogin(),
                player.getHp(),
                player.getAttack(),
                player.getRating()
        );
    }
}
