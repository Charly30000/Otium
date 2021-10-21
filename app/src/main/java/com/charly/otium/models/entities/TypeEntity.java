package com.charly.otium.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "types", indices = {@Index(value = {"type"}, unique = true)})
public class TypeEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "type")
    public String type;

    public TypeEntity(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public TypeEntity(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
