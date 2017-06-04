package com.dt.derbyday.model;

public class UserChoice {
    private Integer id;

    private Integer userId;

    private String game;

    private Integer question;

    private Integer choice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game == null ? null : game.trim();
    }

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public Integer getChoice() {
        return choice;
    }

    public void setChoice(Integer choice) {
        this.choice = choice;
    }
}