package com.charly.otium.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "types", indices = {@Index(value = {"type"}, unique = true)})
public class TypeEntity {
    @PrimaryKey(autoGenerate = true)
    public int typeId;

    @ColumnInfo(name = "type")
    public String type;

    public TypeEntity(int typeId, String type) {
        this.typeId = typeId;
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
