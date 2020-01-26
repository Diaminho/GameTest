package com.example.game_test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ready_players")
public class ReadyPlayer {
    @Id
    @Column(name="player_id")
    private Long playerId;

    public ReadyPlayer(Long playerId) {
        this.playerId = playerId;
    }

    public ReadyPlayer() {
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long userId) {
        this.playerId = userId;
    }
}
