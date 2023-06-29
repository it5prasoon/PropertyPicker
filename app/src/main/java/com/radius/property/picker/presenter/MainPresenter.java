package com.radius.property.picker.presenter;

import android.content.Context;
import com.radius.property.picker.model.DataBaseModel;
import com.radius.property.picker.model.FacilityModel;
import com.radius.property.picker.model.response.DataModel;
import com.radius.property.picker.room.FacilitiesTable;

import java.util.List;

public class MainPresenter implements MainContract.presenter, MainContract.GetFacilityInteractor.OnFinishedListener {

    private final MainContract.GetFacilityInteractor getFacilityInteractor;
    private MainContract.MainView mainView;
    private final MainContract.GetFacilityDataSource getFacilityDataSource;

    public MainPresenter(MainContract.MainView mainView) {
        this.getFacilityInteractor = new FacilityModel((Context) mainView);
        this.mainView = mainView;
        this.getFacilityDataSource = new DataBaseModel(mainView);
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void requestDataFromServer() {
        getFacilityInteractor.getNoticeArrayList(this);
    }

    @Override
    public void requestDataFromDb(String facilityType, String facilityId, String optionId) {
        final List<FacilitiesTable> list = getFacilityDataSource.fetchRowFromDb(facilityType, facilityId, optionId);
        mainView.setDataToRecyclerView(list);
    }

    @Override
    public void onFinished(DataModel dataModel) {
        getFacilityDataSource.storeDataInDb(dataModel);
    }

    @Override
    public void onFailure(Throwable t) {
        mainView.onResponseFailure(t);
    }

}
