package com.radius.property.picker.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.radius.property.picker.model.response.Exclusion;

import java.util.List;

@Dao
public interface ExclusionDao {

    @Query("SELECT * FROM facilities_table where name=:facilityType AND option_id NOT IN (SELECT exclusion_options_id  " +
            "FROM exclusion_table where options_id=:optionId and facility_id=:facilityId)")
    List<FacilitiesTable> getAllFacilitiestypewithoption(String facilityType, String optionId, String facilityId);


    @Query("DELETE FROM exclusion_table")
    void deleteExclusionTable();

    @Query("DELETE FROM facilities_table")
    void deleteFacilitiesTable();

    @Insert
    void insertAll(Exclusion exclusion);

    @Insert
    void insertFacilities(FacilitiesTable facility);
}
