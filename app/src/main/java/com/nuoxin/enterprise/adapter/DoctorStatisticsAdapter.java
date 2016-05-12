package com.nuoxin.enterprise.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.viewholder.DoctorStatisticsViewHolder;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.Doctor;

public class DoctorStatisticsAdapter extends ListBaseAdapter<Doctor> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_doctor_statistics_grid_item, parent, false);
        return new DoctorStatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DoctorStatisticsViewHolder viewHolder = (DoctorStatisticsViewHolder) holder;

        final Doctor doctor = this.mDataList.get(position);
        viewHolder.nameText.setText(doctor.name);
        viewHolder.hospitalText.setText(doctor.hospital);
        viewHolder.departmentText.setText(doctor.department);
        viewHolder.countText.setText(doctor.count + "次");


        if (TextUtils.isEmpty(doctor.picUrl)) {
            viewHolder.avtarImage.setImageResource(R.mipmap.ic_user_avater);
        }else {
            Glide.with(mContext)
                    .load(doctor.picUrl)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_user_avater)
                    .crossFade()
                    .into(viewHolder.avtarImage);

        }


        /*viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("doctor",doctor);
                IntentUtil.startActivity((Activity) mContext, InvolvedProjectActivity.class, bundle);
            }
        });*/
    }
}
