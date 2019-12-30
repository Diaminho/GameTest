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
    private Long userId;

    @Column(name="status")
    private Status status;


    public ReadyPlayer(Long userId, Status status) {
        this.userId = userId;
        this.status = status;
    }

    public ReadyPlayer() {
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static enum Status {
        SEARCHING,
        READY;

        public String getDisplayStatus(Status status) {
            switch (status) {
                case SEARCHING:
                    return "В поиске";
                case READY:
                    return "Готов";
                default:
                    throw new IllegalStateException("Incorrect ReadyPlayer Status");
            }
        }
    }
}
