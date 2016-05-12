package com.nuoxin.enterprise.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.viewholder.StaffViewHolder;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.StaffBean;

import java.util.ArrayList;

public class StaffListAdapter extends ListBaseAdapter<StaffBean> {

    private Context mContext;

    public StaffListAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_staff, parent, false);
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final StaffViewHolder viewHolder = (StaffViewHolder) holder;
        final StaffBean content = this.mDataList.get(position);

        viewHolder.name.setText(content.name);

        Glide.with(mContext)
                .load(content.url)
                .centerCrop()
                .placeholder(R.drawable.ic_image_default)
                .crossFade()
                .into(viewHolder.headImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                viewHolder.cbxSelect.setChecked(!viewHolder.cbxSelect.isChecked());
                mDataList.get(position).isSelected = viewHolder.cbxSelect.isChecked();
                if (mListener != null) {
                    mListener.onItemClick(null, view, position, view.getId());
                }

            }
        });

    }

    private AdapterView.OnItemClickListener mListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener l) {
        mListener = l;
    }

    public int getSelectedCount() {
        int count = 0;
        if (mDataList != null && mDataList.size() > 0) {
            for (StaffBean bean : mDataList) {
                if (bean.isSelected) {
                    count++;
                }
            }
        }

        return count;
    }

    public ArrayList<Long> getSelectedList() {
        ArrayList<Long> ret = new ArrayList<>();
        for (StaffBean bean : mDataList) {
            if (bean.isSelected) {
                ret.add((long) bean.id);
            }
        }

        return ret;
    }
}
