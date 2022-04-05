
package com.charly.otium.models.entitiesGson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemSerieGson {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("lastModification")
    @Expose
    private String lastModification;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("season")
    @Expose
    private int season;
    @SerializedName("chapter")
    @Expose
    private int chapter;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("annotation")
    @Expose
    private String annotation;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ItemSerieGson() {
    }

    /**
     * 
     * @param annotation
     * @param chapter
     * @param image
     * @param lastModification
     * @param season
     * @param state
     * @param title
     * @param type
     * @param createAt
     */
    public ItemSerieGson(String title, String createAt, String lastModification, String type, int season, int chapter, String state, String annotation, String image) {
        super();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getLastModification() {
        return lastModification;
    }

    public void setLastModification(String lastModification) {
        this.lastModification = lastModification;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
