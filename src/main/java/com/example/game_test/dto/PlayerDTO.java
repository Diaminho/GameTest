package com.example.game_test.dto;

public class PlayerDTO {
    private Long id;
    private String login;
    private Long hp;
    private Long attack;
    private Long rating;

    public PlayerDTO(Long id, String login, Long hp, Long attack, Long rating) {
        this.id = id;
        this.login = login;
        this.hp = hp;
        this.attack = attack;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getHp() {
        return hp;
    }

    public void setHp(Long hp) {
        this.hp = hp;
    }

    public Long getAttack() {
        return attack;
    }

    public void setAttack(Long attack) {
        this.attack = attack;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }
}
