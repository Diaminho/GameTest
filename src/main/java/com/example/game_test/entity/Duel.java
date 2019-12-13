package com.example.game_test.entity;

import javax.persistence.*;

@Entity
@Table(name="duels")
public class Duel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="first_player_id")
    private Long firstPlayerId;

    @Column(name="first_player_hp")
    private Long firstPlayerHp;

    @Column(name="second_player_id")
    private Long secondPlayerId;
    
    @Column(name="second_player_hp")
    private Long secondPlayerHp;

    public Duel(Long firstPlayerId, Long firstPlayerHp, Long secondPlayerId, Long secondPlayerHp) {
        this.firstPlayerId = firstPlayerId;
        this.firstPlayerHp = firstPlayerHp;
        this.secondPlayerId = secondPlayerId;
        this.secondPlayerHp = secondPlayerHp;
    }

    public Duel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirstPlayerId() {
        return firstPlayerId;
    }

    public void setFirstPlayerId(Long firstPlayerId) {
        this.firstPlayerId = firstPlayerId;
    }

    public Long getSecondPlayerId() {
        return secondPlayerId;
    }

    public void setSecondPlayerId(Long secondPlayerId) {
        this.secondPlayerId = secondPlayerId;
    }

    public Long getFirstPlayerHp() {
        return firstPlayerHp;
    }

    public void setFirstPlayerHp(Long firstPlayerHp) {
        this.firstPlayerHp = firstPlayerHp;
    }

    public Long getSecondPlayerHp() {
        return secondPlayerHp;
    }

    public void setSecondPlayerHp(Long secondPlayerHp) {
        this.secondPlayerHp = secondPlayerHp;
    }
}
