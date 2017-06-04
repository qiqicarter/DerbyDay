package com.dt.derbyday.model;

import java.util.Date;

public class GameInfo {
    private Integer id;

    private String title;

    private String picUrl;

    private Date startDate;

    private Date endDate;

    private String game;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game == null ? null : game.trim();
    }
}