package com.nuoxin.enterprise.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuoxin.enterprise.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.display_image) public ImageView displayImage;
    @Bind(R.id.title_text) public TextView titleText;
    @Bind(R.id.speaker_text) public TextView speakerText;
    @Bind(R.id.time_text) public TextView timeText;


    public NewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
