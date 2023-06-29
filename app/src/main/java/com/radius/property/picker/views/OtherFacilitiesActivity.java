package com.radius.property.picker.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;


import com.radius.property.picker.R;
import com.radius.property.picker.adapter.FaciltyAdapter;
import com.radius.property.picker.utils.Constant;
import com.radius.property.picker.presenter.MainContract;
import com.radius.property.picker.presenter.MainPresenter;
import com.radius.property.picker.room.FacilitiesTable;

import java.util.List;

public class OtherFacilitiesActivity extends AppCompatActivity implements MainContract.MainView {
    private RecyclerView recyclerView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String facilityId = getIntent().getStringExtra(Constant.FACILITY_ID);
        final String optionId = getIntent().getStringExtra(Constant.OPTION_ID);

        context = this;
        recyclerView = findViewById(R.id.rvFacility);
        final AppCompatTextView tvFacilityTitle = findViewById(R.id.tvFacilityTitle);
        tvFacilityTitle.setText("Other Facilities");

        final MainContract.presenter presenter = new MainPresenter(this);
        presenter.requestDataFromDb("Other facilities", facilityId, optionId);
    }


    @Override
    public void setDataToRecyclerView(List<FacilitiesTable> noticeArrayList) {
        final FaciltyAdapter faciltyAdapter = new FaciltyAdapter(context, noticeArrayList, recyclerViewOnClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(faciltyAdapter);
        faciltyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final Intent intent = new Intent(OtherFacilitiesActivity.this, RoomsNumberActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d("ERROR:: ", throwable.getLocalizedMessage());
    }

    MainContract.RecyclerViewOnClick recyclerViewOnClick = new MainContract.RecyclerViewOnClick() {
        @Override
        public void onClick(FacilitiesTable facilityModel) {
            final Intent intent = new Intent(context, DisplayChosen.class);
            intent.putExtra(Constant.OTHER_FACILITY, facilityModel.getOptionName());
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        }
    };

}
