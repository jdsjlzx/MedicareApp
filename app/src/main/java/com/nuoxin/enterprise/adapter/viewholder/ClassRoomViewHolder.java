package com.nuoxin.enterprise.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuoxin.enterprise.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lizhixian on 16/2/28.
 */
public class ClassRoomViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.display_image)
    public ImageView displayImage;
    @Bind(R.id.title_text)
    public TextView titleText;
    @Bind(R.id.count_text)
    public TextView countText;
    public ClassRoomViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
