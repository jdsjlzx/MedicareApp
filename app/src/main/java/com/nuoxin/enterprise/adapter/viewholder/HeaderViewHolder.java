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
public class HeaderViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.head_text) public TextView stickyText;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
