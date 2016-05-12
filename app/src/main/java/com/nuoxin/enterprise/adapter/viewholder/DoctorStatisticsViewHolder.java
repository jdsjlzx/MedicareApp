package com.nuoxin.enterprise.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.widget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lizhixian on 16/2/28.
 */
public class DoctorStatisticsViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.avatar_image)
    public CircleImageView avtarImage;
    @Bind(R.id.name_text)
    public TextView nameText;
    @Bind(R.id.hospital_text)
    public TextView hospitalText;
    @Bind(R.id.department_text)
    public TextView departmentText;
    @Bind(R.id.count_text)
    public TextView countText;


    public DoctorStatisticsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
