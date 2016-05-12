package com.nuoxin.enterprise.base;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cundong.recyclerview.CustRecyclerView;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LoadingFooter;
import com.cundong.recyclerview.RecyclerOnScrollListener;
import com.cundong.recyclerview.RecyclerViewStateUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nuoxin.enterprise.AppContext;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.bean.Entity;
import com.nuoxin.enterprise.ui.error.ErrorLayout;
import com.nuoxin.enterprise.util.TLog;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

public abstract class BaseListActivity<T extends Entity> extends EmptyActivity implements SwipeRefreshLayout.OnRefreshListener{

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;
    protected int mCurrentPage = 0;
    protected int totalPage = 0;

    @Bind(R.id.swipe_container)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view)
    protected CustRecyclerView mRecyclerView;
    @Bind(R.id.error_layout)
    protected ErrorLayout mErrorLayout;
    @Bind(R.id.dynamic_view)
    protected LinearLayout mDynamicLayout;
    @Bind(R.id.title_bar_view)
    protected RelativeLayout mTitleBar;
    @Bind(R.id.title_bar_search_view)
    protected RelativeLayout mSearchTitleBar;
    @Bind(R.id.bottom_layout)
    protected LinearLayout mBottomLayout;

    protected ListBaseAdapter<T> mListAdapter;
    protected HeaderAndFooterRecyclerViewAdapter mRecyclerViewAdapter;
    public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

    protected boolean isRequestInProcess = false;
    protected boolean mIsStart = false;
    protected ParserTask mParserTask;

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(BaseListActivity.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            requestData();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pull_refresh_recyclerview;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(BaseListActivity.this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        if (mListAdapter != null) {
            mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
        } else {
            mListAdapter = getListAdapter();

            if (requestDataIfViewCreated()) {
                mErrorLayout.setErrorType(ErrorLayout.NETWORK_LOADING);
                requestData();
            } else {
                mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
            }

        }

        mRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mListAdapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mOnScrollListener.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mRecyclerView.setOnPauseListenerParams(ImageLoader.getInstance(), false, true);
        initLayoutManager();
    }


    @Override
    public void initData() {

    }

    @Override
    public void onDestroy() {
        cancelParserTask();
        super.onDestroy();
    }


    protected boolean requestDataIfViewCreated() {
        return true;
    }

    @Override
    public void onRefresh() {
        if (isRequestInProcess) {
            return;
        }
        // 设置顶部正在刷新
        setSwipeRefreshLoadingState();
        mCurrentPage = 0;
        requestData();
    }

    protected RecyclerOnScrollListener mOnScrollListener = new RecyclerOnScrollListener() {

        @Override
        public void onBottom() {
            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
            if(state == LoadingFooter.State.Loading) {
                return;
            }

            if (mCurrentPage < totalPage) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(BaseListActivity.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                requestData();
            } else {
                //the end
                if (totalPage > 1){
                    RecyclerViewStateUtils.setFooterViewState(BaseListActivity.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
                }else {
                    RecyclerViewStateUtils.setFooterViewState(BaseListActivity.this, mRecyclerView, mListAdapter.getItemCount(), LoadingFooter.State.TheEnd, null);
                }

            }
        }


    };

    /** 设置顶部正在加载的状态 */
    protected void setSwipeRefreshLoadingState() {
        TLog.log("setSwipeRefreshLoadingState ");
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    /** 设置顶部加载完毕的状态 */
    protected void setSwipeRefreshLoadedState() {
        TLog.log("setSwipeRefreshLoadedState ");
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    // 完成刷新
    protected void executeOnLoadFinish() {
        setSwipeRefreshLoadedState();
        isRequestInProcess = false;
        mIsStart = false;
    }

    protected abstract ListBaseAdapter<T> getListAdapter();
    protected void requestData() {
        mCurrentPage++;
        sendRequestData();
        isRequestInProcess = true;
    }

    protected void sendRequestData() {}
    protected String getRequestUrl(boolean mIsStart) {return null;}

    protected List<T> parseList(byte[] data) throws Exception {
        return null;
    }
    protected abstract void initLayoutManager();

    protected int getPageSize() {
        return REQUEST_COUNT;
    }

    private void executeParserTask(byte[] data) {
        cancelParserTask();
        mParserTask = new ParserTask(data);
        mParserTask.execute();
    }

    private void cancelParserTask() {
        if (mParserTask != null) {
            mParserTask.cancel(true);
            mParserTask = null;
        }
    }


    class ParserTask extends AsyncTask<Void, Void, String> {

        private final byte[] reponseData;
        private boolean parserError;
        private List<T> list;

        public ParserTask(byte[] data) {
            this.reponseData = data;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                TLog.log("11 doInBackground ");
                list = parseList(reponseData);
            } catch (Exception e) {
                e.printStackTrace();

                parserError = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TLog.log("onPostExecute ");
            if (parserError) {
                executeOnLoadDataError(null);
                TLog.log("parse error");
            } else {
                executeOnLoadDataSuccess(list);
                executeOnLoadFinish();
            }
        }
    }

    protected AsyncHttpResponseHandler mResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] responseBody) {
            TLog.log("BaseListActivity onSuccess " + new String(responseBody));
            onRefreshNetworkSuccess();
            executeParserTask(responseBody);
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] responseBody, Throwable throwable) {
            if(null == responseBody){
                return;
            }
            String result = new String(responseBody);
            TLog.log("BaseListActivity onFailure " + result);
            if(result.contains("Mismatched access token") || result.contains("X-Access-Token")) {
                AppContext.getInstance().handleLogout();
                finish();
            }else {
                //executeOnLoadDataError(null); 
                executeParserTask(responseBody);
            }
        }

    };

    protected void executeOnLoadDataSuccess(List<T> data) {
        TLog.log("executeOnLoadDataSuccess " + data.size());
        if (data == null) {
            data = new ArrayList<>();
        }


        mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
        if (mCurrentPage == 1) {
            mListAdapter.setDataList(data);
        } else {
            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
            mListAdapter.addAll(data);
        }
    }

    protected void executeOnLoadDataError(String error) {
        if (mCurrentPage == 0) {
            mErrorLayout.setErrorType(ErrorLayout.NETWORK_ERROR);
        } else {

            //在无网络时，滚动到底部时，mCurrentPage先自加了，然而在失败时却
            //没有减回来，如果刻意在无网络的情况下上拉，可以出现漏页问题
            //find by TopJohn
            mCurrentPage--;

            mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
            RecyclerViewStateUtils.setFooterViewState(BaseListActivity.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.NetWorkError, mFooterClick);
            mListAdapter.notifyDataSetChanged();
        }
    }

    protected void onRefreshNetworkSuccess() {}

}
