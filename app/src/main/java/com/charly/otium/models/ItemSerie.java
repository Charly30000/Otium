package com.charly.otium.models;

import java.util.Date;

public class ItemSerie {
    private int id;
    private String name;
    private Date createAt;
    private Date lastModification;
    private Type type;
    private int season;
    private int chapter;
    private String state;
    private String annotation;
    private String image;

    public ItemSerie(int id, String name, Date createAt, Date lastModification,
                     Type type, int season, int chapter, String state,
                     String annotation, String image) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.lastModification = lastModification;
        this.type = type;
        this.season = season;
        this.chapter = chapter;
        this.state = state;
        this.annotation = annotation;
        this.image = image;
    }

    public ItemSerie(String name, Date createAt, Date lastModification,
                     Type type, int season, int chapter, String state,
                     String annotation, String image) {
        this.name = name;
        this.createAt = createAt;
        this.lastModification = lastModification;
        this.type = type;
        this.season = season;
        this.chapter = chapter;
        this.state = state;
        this.annotation = annotation;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
