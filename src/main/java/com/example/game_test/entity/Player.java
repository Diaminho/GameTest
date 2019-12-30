package com.example.game_test.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "hp")
    private Long hp;

    @Column(name = "attack")
    private Long attack;

    @Column(name = "rating")
    private Long rating;

    public Player() {
    }

    public Player(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Player(String login, String password, Long hp, Long attack, Long rating) {
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) &&
                Objects.equals(login, player.login) &&
                password.compareTo(player.password) == 0 &&
                hp.compareTo(player.hp) == 0 &&
                attack.compareTo(player.attack) == 0 &&
                rating.compareTo(player.rating) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, hp, attack, rating);
    }


    public static class Builder {
        // Обязательные параметры
        private String login;
        private String password;
        private Long id;

        // Необязательные параметры с значениями по умолчанию
        private Long hp = 100L;
        private Long attack = 10L;
        private Long rating = 0L;

        public Builder(String login, String password) {
            this.login = login;
            this.password = password;
            this.id = id;
        }

        public Builder hp(Long val) {
            hp = val;
            return this;
        }

        public Builder attack(Long val) {
            attack = val;
            return this;
        }

        public Builder rating(Long val) {
            rating = val;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }

    private Player(Builder builder) {
        login = builder.login;
        password = builder.password;
        hp = builder.hp;
        attack = builder.attack;
        rating = builder.rating;
    }
}

