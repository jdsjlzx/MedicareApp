package com.nuoxin.enterprise.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.nuoxin.enterprise.adapter.MeetingsListAdapter;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseNewsListFragment;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.Meeting;
import com.nuoxin.enterprise.util.JsonUtil;

import java.util.List;


public class NewsListFragment extends BaseNewsListFragment {

    public static NewsListFragment newInstance(int type) {

        Bundle args = new Bundle();

        NewsListFragment fragment = new NewsListFragment();
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new MeetingsListAdapter(getArguments().getInt("type"),-1);
    }

    @Override
    protected void sendRequestData() {
        NuoXinApi.getCourseList(0, mResponseHandler);
    }

    @Override
    protected List<Meeting> parseList(byte[] data) {
        return null;
    }

    @Override
    protected void initHeader(byte[] data) throws Exception {
        mHeadText.setText("Header One");
        mStatisticsText.setText("查看统计");
    }

    @Override
    protected void initLayoutManager() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
