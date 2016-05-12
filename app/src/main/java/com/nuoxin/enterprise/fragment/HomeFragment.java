package com.nuoxin.enterprise.fragment;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.cundong.recyclerview.MarginDecoration;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.HeaderSpanSizeLookup;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.ClassRoomAdapter;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseListFragment;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.ClassRoom;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;

import java.util.List;


public class HomeFragment extends BaseListFragment {

    @Override
    public void initView(View view) {
        super.initView(view);

        mDynamicLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new ClassRoomAdapter(null);
    }

    @Override
    protected void sendRequestData() {
        NuoXinApi.getCourseList(mCurrentPage, mResponseHandler);
        TLog.log("NuoXinApi.getClassRoomList ");
    }

    @Override
    protected List<ClassRoom> parseList(byte[] data) {
        TLog.log("List<ClassRoom> parseList " + new String(data));
        return JsonUtil.AnalyzeClassRoomList(-1, data);
    }

    @Override
    protected void initLayoutManager() {
        int margin = getActivity().getResources().getDimensionPixelSize(R.dimen.home_item_margin);
        MarginDecoration itemDecoration = new MarginDecoration(getActivity(),margin);
        mRecyclerView.addItemDecoration(itemDecoration);
        int space = getResources().getDimensionPixelSize(R.dimen.home_item_margin);
        mRecyclerView.setPadding(space,space,space,space);
        mRecyclerView.setBackgroundColor(getResources().getColor(android.R.color.white));

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), layoutManager.getSpanCount()));
        layoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);

    }
}
