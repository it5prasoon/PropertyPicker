package com.radius.property.picker.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.radius.property.picker.R;
import com.radius.property.picker.adapter.FaciltyAdapter;
import com.radius.property.picker.databinding.ActivityMainBinding;
import com.radius.property.picker.presenter.MainContract;
import com.radius.property.picker.presenter.MainPresenter;
import com.radius.property.picker.room.FacilitiesTable;
import com.radius.property.picker.utils.Constant;

import java.util.List;

public class OtherFacilitiesActivity extends AppCompatActivity implements MainContract.MainView {
    private Context context;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final String facilityId = getIntent().getStringExtra(Constant.FACILITY_ID);
        final String optionId = getIntent().getStringExtra(Constant.OPTION_ID);

        context = this;
        binding.tvFacilityTitle.setText("Other Facilities");

        final MainContract.presenter presenter = new MainPresenter(this);
        presenter.requestDataFromDb("Other facilities", facilityId, optionId);
    }


    @Override
    public void setDataToRecyclerView(List<FacilitiesTable> noticeArrayList) {
        final FaciltyAdapter faciltyAdapter = new FaciltyAdapter(context, noticeArrayList, recyclerViewOnClick);
        binding.rvFacility.setLayoutManager(new LinearLayoutManager(context));
        binding.rvFacility.setAdapter(faciltyAdapter);
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
            final Intent intent = new Intent(context, DisplayChosenActivity.class);
            intent.putExtra(Constant.OTHER_FACILITY, facilityModel.getOptionName());
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        }
    };

}
