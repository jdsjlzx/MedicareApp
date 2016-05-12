package com.nuoxin.enterprise.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.viewholder.ClassRoomViewHolder;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.ClassRoom;
import com.nuoxin.enterprise.bean.Doctor;
import com.nuoxin.enterprise.ui.ClassActivity;
import com.nuoxin.enterprise.ui.DoctorClassActivity;
import com.nuoxin.enterprise.ui.SecondActivity;
import com.nuoxin.enterprise.util.IntentUtil;

/**
 * Created by lizhixian on 16/2/28.
 */
public class DoctorClassRoomAdapter extends ListBaseAdapter<ClassRoom> {

    private Doctor mDoctor;
    public DoctorClassRoomAdapter(Doctor doctor) {
        this.mDoctor = doctor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_grid_item, parent, false);
        return new ClassRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ClassRoomViewHolder viewHolder = (ClassRoomViewHolder) holder;

        final ClassRoom classRoom = this.mDataList.get(position);
        viewHolder.titleText.setText(classRoom.title);
        viewHolder.countText.setText("(会议"+ classRoom.count+")");

        Glide.with(mContext)
                .load(classRoom.picUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_image_default)
                .crossFade()
                .into(viewHolder.displayImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title",classRoom.title);
                bundle.putInt("productId",classRoom.id);
                if(mDoctor == null) {
                    if(classRoom.type == 1){
                        IntentUtil.startActivity((Activity) mContext, SecondActivity.class,bundle);
                    }else {
                        IntentUtil.startActivity((Activity) mContext, ClassActivity.class,bundle);
                    }

                }else {
                    bundle.putSerializable("doctor",mDoctor);
                    IntentUtil.startActivity((Activity) mContext, DoctorClassActivity.class,bundle);
                }

            }
        });

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin_vertical = mContext.getResources().getDimensionPixelSize(R.dimen.doctor_gird_item_vertical_margin);
        int margin_horizontal = mContext.getResources().getDimensionPixelSize(R.dimen.doctor_gird_item_horizontal_margin);
        if (position % 2 == 0) {
            lp.setMargins(margin_horizontal, margin_vertical,
                    margin_vertical, margin_vertical);
        } else {
            lp.setMargins(margin_vertical, margin_vertical, margin_horizontal, margin_vertical);
        }
        viewHolder.itemView.setLayoutParams(lp);
    }
}
