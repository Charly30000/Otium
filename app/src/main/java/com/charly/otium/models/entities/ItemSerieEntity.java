package com.charly.otium.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.charly.otium.config.Config;
import com.charly.otium.models.converters.DateConverter;

import java.util.Date;

@Entity(tableName = Config.TABLE_ITEM_SERIE, indices = {@Index(value = {"title"}, unique = true)})
public class ItemSerieEntity {
    @PrimaryKey(autoGenerate = true)
    public int itemSerieId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "create_at")
    @TypeConverters(DateConverter.class)
    public Date createAt;

    @ColumnInfo(name = "last_modification")
    @TypeConverters(DateConverter.class)
    public Date lastModification;
    /*
    @ColumnInfo(name = "type_id")
    // Referenced TypeEntity
    public int typeId;
    */
    @ColumnInfo( name = "type")
    public String type;

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

    public ItemSerieEntity() {
    }

    public ItemSerieEntity(int itemSerieId, String title, Date createAt, Date lastModification,
                           String type, int season, int chapter, String state,
                           String annotation, String image) {
        this.itemSerieId = itemSerieId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getItemSerieId() {
        return itemSerieId;
    }

    public void setItemSerieId(int itemSerieId) {
        this.itemSerieId = itemSerieId;
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
/*
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
*/
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
