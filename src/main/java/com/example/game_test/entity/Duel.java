package com.example.game_test.entity;

import javax.persistence.*;

@Entity
@Table(name = "duels")
public class Duel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "first_player_id")
    @ManyToOne
    private Player firstPlayer;

    @Column(name = "first_player_hp")
    private Long firstPlayerHp;

    @JoinColumn(name = "second_player_id")
    @ManyToOne
    private Player secondPlayer;

    @Column(name = "second_player_hp")
    private Long secondPlayerHp;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "log", columnDefinition = "text")
    private String log;

    public Duel(Player firstPlayer, Long firstPlayerHp, Player secondPlayer, Long secondPlayerHp, Status status) {
        this.firstPlayer = firstPlayer;
        this.firstPlayerHp = firstPlayerHp;
        this.secondPlayer = secondPlayer;
        this.secondPlayerHp = secondPlayerHp;
        this.status = status;
    }

    public Duel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public enum Status {
        INITIATED,
        IN_PROGRESS,
        FINISHED
    }
}
