package com.nuoxin.enterprise.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.viewholder.DoctorMeetingViewHolder;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.DoctorMeeting;

public class DoctorMeetingAdapter extends ListBaseAdapter<DoctorMeeting> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_doctor_meeting_list_item, parent, false);
        return new DoctorMeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DoctorMeetingViewHolder viewHolder = (DoctorMeetingViewHolder) holder;

        final DoctorMeeting doctorMeeting = this.mDataList.get(position);
        viewHolder.checkBox.setChecked(doctorMeeting.isChecked);
        viewHolder.titleText.setText(doctorMeeting.title);

    }
}
