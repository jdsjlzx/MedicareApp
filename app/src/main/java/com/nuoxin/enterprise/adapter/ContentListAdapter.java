package com.nuoxin.enterprise.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.viewholder.NewsViewHolder;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.ContentBean;
import com.nuoxin.enterprise.ui.WebViewActivity;
import com.nuoxin.enterprise.util.IntentUtil;

public class ContentListAdapter extends ListBaseAdapter<ContentBean> {

    private Context mContext;

    public ContentListAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewsViewHolder viewHolder = (NewsViewHolder) holder;
        final ContentBean content = this.mDataList.get(position);

        viewHolder.titleText.setText(content.title);
        viewHolder.speakerText.setSingleLine(false);
        viewHolder.speakerText.setMaxLines(2);
        viewHolder.speakerText.setText(content.key_content);

        Glide.with(mContext)
                .load(content.url)
                .centerCrop()
                .placeholder(R.drawable.ic_image_default)
                .crossFade()
                .into(viewHolder.displayImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", content.contentUrl);
                bundle.putString("title", content.title);
                bundle.putInt("id", content.id);
                bundle.putString("content", content.key_content);
                IntentUtil.startActivity((Activity) mContext, WebViewActivity.class, bundle);
            }
        });


    }
}
