package com.nuoxin.enterprise.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.nuoxin.enterprise.adapter.ContentListAdapter;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseListFragment;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.ContentBean;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;

import java.util.List;


public class MainContentFragment extends BaseListFragment {

    @Override
    public void initView(View view) {
        super.initView(view);

        mDynamicLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new ContentListAdapter();
    }

    @Override
    protected void sendRequestData() {
        NuoXinApi.getContentList(mCurrentPage, mResponseHandler);
        TLog.log("NuoXinApi.getContentList ");
    }

    @Override
    protected List<ContentBean> parseList(byte[] data) {
        TLog.log("List<ContentBean> parseList " + new String(data));
        return JsonUtil.AnalyzeContentList(data);
    }

    @Override
    protected void initLayoutManager() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
