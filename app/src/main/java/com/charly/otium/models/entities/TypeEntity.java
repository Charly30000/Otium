package com.charly.otium.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.charly.otium.config.Config;

/**
 * No se esta utilizando esta clase actualmente
 */
@Entity(tableName = Config.TABLE_TYPES, indices = {@Index(value = {"type"}, unique = true)})
public class TypeEntity {
    @PrimaryKey(autoGenerate = true)
    public int typeId;

    @ColumnInfo(name = "type")
    public String type;

    public TypeEntity() {
    }

    public TypeEntity(int typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

    public TypeEntity(String type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
