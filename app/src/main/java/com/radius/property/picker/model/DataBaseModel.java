package com.radius.property.picker.model;

import android.content.Context;

import com.radius.property.picker.presenter.MainContract;
import com.radius.property.picker.model.response.DataModel;
import com.radius.property.picker.model.response.Exclusion;
import com.radius.property.picker.model.response.Facility;
import com.radius.property.picker.model.response.Option;
import com.radius.property.picker.room.AppDatabase;
import com.radius.property.picker.room.FacilitiesTable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseModel implements MainContract.GetFacilityDataSource {
    private final AppDatabase db;

    public DataBaseModel(MainContract.MainView mainView) {
        db = AppDatabase.getInstance((Context) mainView);
    }

    @Override
    public void getFacilitiesArrayList(List<FacilitiesTable> facilitiesTableList) {
    }

    @Override
    public List<FacilitiesTable> fetchRowFromDb(String facilityType, String facilityId, String optionId) {
        return new ArrayList<>(db.userDao().getAllFacilitiestypewithoption(facilityType, optionId, facilityId));
    }

    @Override
    public void storeDataInDb(DataModel dataModel) {

        db.userDao().deleteExclusionTable();
        db.userDao().deleteFacilitiesTable();
        List<List<Exclusion>> exclusionList = new ArrayList<>();
        exclusionList.addAll(dataModel.getExclusions());

        final Exclusion exclusion = new Exclusion();
        for (int i = 0; i < exclusionList.size(); i++) {
            exclusion.setFacilityId(exclusionList.get(i).get(0).getFacilityId());
            exclusion.setOptionsId(exclusionList.get(i).get(0).getOptionsId());
            exclusion.setExclusionFacilityId(exclusionList.get(i).get(1).getFacilityId());
            exclusion.setExclusionOptionsId(exclusionList.get(i).get(1).getOptionsId());
            db.userDao().insertAll(exclusion);
        }

        final FacilitiesTable facilitiesTable = new FacilitiesTable();
        for (Facility facility : dataModel.getFacilities()) {
            facilitiesTable.setName(facility.getName());
            facilitiesTable.setFacilityId(facility.getFacilityId());
            for (Option option : facility.getOptions()) {
                facilitiesTable.setOptionIconName(option.getIcon());
                facilitiesTable.setOptionId(option.getId());
                facilitiesTable.setOptionName(option.getName());
                db.userDao().insertFacilities(facilitiesTable);
            }
        }
    }
}
