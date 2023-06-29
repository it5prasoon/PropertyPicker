package com.radius.property.picker.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radius.property.picker.presenter.MainContract;
import com.radius.property.picker.R;
import com.radius.property.picker.room.FacilitiesTable;

import java.util.List;

public class FaciltyAdapter extends RecyclerView.Adapter<FaciltyAdapter.MyViewHolder> {
    private final Context context;
    private final List<FacilitiesTable> facilityModels;
    private final MainContract.RecyclerViewOnClick recyclerViewOnClick;

    public FaciltyAdapter(Context context, List<FacilitiesTable> facilityModels, MainContract.RecyclerViewOnClick recyclerViewOnClick) {
        this.context = context;
        this.facilityModels = facilityModels;
        this.recyclerViewOnClick = recyclerViewOnClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.facility_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final FacilitiesTable facilityModel = facilityModels.get(position);
        holder.tvOptionName.setText(facilityModel.getOptionName());
        final String optionIconName = facilityModel.getOptionIconName();

        switch (optionIconName) {
            case "apartment":
                holder.ivOptionIcon.setBackground(ContextCompat.getDrawable(context, R.drawable.apartment));
                break;
            case "boat":
                holder.ivOptionIcon.setBackground(ContextCompat.getDrawable(context, R.drawable.boat));
                break;
            case "condo":
                holder.ivOptionIcon.setBackground(ContextCompat.getDrawable(context, R.drawable.condo));
                break;
            case "garage":
                holder.ivOptionIcon.setBackground(ContextCompat.getDrawable(context, R.drawable.garage));
                break;
            case "garden":
                holder.ivOptionIcon.setBackground(ContextCompat.getDrawable(context, R.drawable.garden));
                break;
            case "land":
                holder.ivOptionIcon.setBackground(ContextCompat.getDrawable(context, R.drawable.land));
                break;
            case "no-room":
                holder.ivOptionIcon.setBackground(ContextCompat.getDrawable(context, R.drawable.noroom));
                break;
            case "rooms":
                holder.ivOptionIcon.setBackground(ContextCompat.getDrawable(context, R.drawable.rooms));
                break;
            case "swimming":
                holder.ivOptionIcon.setBackground(ContextCompat.getDrawable(context, R.drawable.swimming));
                break;
            default:
                break;
        }

        holder.itemView.setOnClickListener(view -> recyclerViewOnClick.onClick(facilityModel));
    }


    @Override
    public int getItemCount() {
        return facilityModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView ivOptionIcon;
        private final AppCompatTextView tvOptionName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivOptionIcon = itemView.findViewById(R.id.ivOptionIcon);
            tvOptionName = itemView.findViewById(R.id.tvOptionName);
        }
    }
}
