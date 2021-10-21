package com.charly.otium.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "items_series", indices = {@Index(value = {"name"}, unique = true)})
public class ItemSerieEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "create_at")
    public Date createAt;

    @ColumnInfo(name = "last_modification")
    public Date lastModification;

    @Embedded
    @ColumnInfo(name = "type")
    public TypeEntity type;

    @ColumnInfo(name = "season")
    public int season;

    @ColumnInfo(name = "chapter")
    public int chapter;

    @ColumnInfo(name = "state")
    public String state;

    @ColumnInfo(name = "annotation")
    public String annotation;

    @ColumnInfo(name = "image")
    public String image;

    public ItemSerieEntity(int id, String title, Date createAt, Date lastModification,
                           TypeEntity type, int season, int chapter, String state,
                           String annotation, String image) {
        this.id = id;
        this.title = title;
        this.createAt = createAt;
        this.lastModification = lastModification;
        this.type = type;
        this.season = season;
        this.chapter = chapter;
        this.state = state;
        this.annotation = annotation;
        this.image = image;
    }

    public ItemSerieEntity(String title, Date createAt, Date lastModification,
                           TypeEntity type, int season, int chapter, String state,
                           String annotation, String image) {
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
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
