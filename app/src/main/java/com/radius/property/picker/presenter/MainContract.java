package com.radius.property.picker.presenter;

import com.radius.property.picker.model.response.DataModel;
import com.radius.property.picker.room.FacilitiesTable;

import java.util.List;

public class MainContract {


    public interface MainView {

        void setDataToRecyclerView(List<FacilitiesTable> noticeArrayList);

        void onResponseFailure(Throwable throwable);

    }

    public interface GetFacilityInteractor {

        interface OnFinishedListener {
            void onFinished(DataModel dataModel);

            void onFailure(Throwable t);
        }

        void getNoticeArrayList(OnFinishedListener onFinishedListener);
    }

    public interface GetFacilityDataSource {
        void getFacilitiesArrayList(List<FacilitiesTable> dataModel);

        List<FacilitiesTable> fetchRowFromDb(String facilityType, String facilityId, String optionId);

        void storeDataInDb(DataModel dataModel);
    }

    public interface RecyclerViewOnClick {
        void onClick(FacilitiesTable facilitiesTable);
    }

    public interface presenter {

        void onDestroy();

        void requestDataFromServer();

        void requestDataFromDb(String facilityType, String facilityId, String optionId);


    }

}
