package com.example.game_test.enteties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ready_players")
public class ReadyUser {
    @Id
    @Column(name="player_id")
    private Long userId;

    public ReadyUser(Long userId) {
        this.userId = userId;
    }

    public ReadyUser() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
