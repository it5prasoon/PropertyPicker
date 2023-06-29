package com.radius.property.picker.model.response;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "data_table")
public class DataModel  {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "FacilitiesList")
    @SerializedName("facilities")
    @Expose
    private List<Facility> facilities = null;

    @ColumnInfo(name = "ExclusionList")
    @SerializedName("exclusions")
    @Expose
    private List<List<Exclusion>> exclusions = null;

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public List<List<Exclusion>> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<List<Exclusion>> exclusions) {
        this.exclusions = exclusions;
    }

}

