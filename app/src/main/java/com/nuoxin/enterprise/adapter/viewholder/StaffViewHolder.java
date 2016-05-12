package com.nuoxin.enterprise.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuoxin.enterprise.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StaffViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.cbx_select) public CheckBox cbxSelect;
    @Bind(R.id.head_image) public ImageView headImage;
    @Bind(R.id.name) public TextView name;


    public StaffViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
