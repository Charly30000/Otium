package com.charly.otium.models.entitiesGson;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileJson {

    @SerializedName("items")
    @Expose
    private List<ItemSerieGson> itemSerieGson;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("types")
    @Expose
    private List<String> types;

    public FileJson(List<ItemSerieGson> itemSerieEntity, String version, List<String> types) {
        this.itemSerieGson = itemSerieEntity;
        this.version = version;
        this.types = types;
    }

    public List<ItemSerieGson> getItemSerieGson() {
        return itemSerieGson;
    }

    public void setItemSerieGson(List<ItemSerieGson> itemSerieGson) {
        this.itemSerieGson = itemSerieGson;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}
