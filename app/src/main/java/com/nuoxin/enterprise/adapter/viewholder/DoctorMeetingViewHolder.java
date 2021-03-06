package com.nuoxin.enterprise.adapter.viewholder;

import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuoxin.enterprise.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lizhixian on 16/2/28.
 */
public class DoctorMeetingViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.checkbox)
    public CheckBox checkBox;
    @Bind(R.id.title_text)
    public TextView titleText;


    public DoctorMeetingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
