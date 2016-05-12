package com.nuoxin.enterprise.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.StaffListAdapter;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseListActivity;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.StaffBean;
import com.nuoxin.enterprise.util.DialogHelper;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boyo on 16/4/12.
 */
public class ShareChooseActivity extends BaseListActivity implements AdapterView.OnItemClickListener {

    public static final int TYPE_SMS = 2;
    public static final int TYPE_EMAIL = 3;

    private TextView mTipView;

    private int mId;
    private int mType;
    private String mTitle;
    private String mUrl;

    private String mShareTipSuffix;

    @Override
    public void initView() {
        super.initView();
        mTitleBar.setVisibility(View.VISIBLE);
        mBackBtn.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        mDynamicLayout.setVisibility(View.VISIBLE);
        mTipView = (TextView) mInflater.inflate(R.layout.layout_share_choose_tips, mDynamicLayout, false);
        lp.bottomMargin = 20;
        mDynamicLayout.addView(mTipView, lp);

        View bottom = mInflater.inflate(R.layout.layout_share_choose_bottom, null);
        mBottomLayout.setVisibility(View.VISIBLE);
        mBottomLayout.addView(bottom, lp);
        mBottomLayout.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        ((StaffListAdapter) mListAdapter).setOnItemClickListener(this);
    }

    void send() {
        // TODO
        if (mTotalCount == 0) {
            Toast.makeText(this, "请选择联系人", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Long> ids = ((StaffListAdapter) mListAdapter).getSelectedList();
        DialogHelper.showDialogForLoading(this, "分享中，请您耐心等待...", true);
        NuoXinApi.share(mId, mType, mTitle, mUrl, ids, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                TLog.log("BaseListActivity onSuccess " + new String(responseBody));
                Toast.makeText(ShareChooseActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] responseBody, Throwable throwable) {
                String result = new String(responseBody);
                TLog.log("BaseListActivity onFailure " + result);
                Toast.makeText(ShareChooseActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                DialogHelper.stopProgressDlg();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mTitleText.setText("分享内容");

        mType = getIntent().getIntExtra("shareType", TYPE_SMS);
        if (mType == TYPE_SMS) {
            mShareTipSuffix = "（短信分享）";
        } else {
            mShareTipSuffix = "（邮件分享）";
        }
        mId = getIntent().getIntExtra("id", 0);
        mTitle = getIntent().getStringExtra("title");
        mUrl = getIntent().getStringExtra("url");

        mTipView.setText(getString(R.string.share_tips, 0) + mShareTipSuffix);
    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new StaffListAdapter();
    }

    @Override
    protected void sendRequestData() {
        NuoXinApi.getShareStaffList(mCurrentPage, mResponseHandler);
    }

    @Override
    protected List<StaffBean> parseList(byte[] data) {
        return JsonUtil.AnalyzeStaffList(data);
    }


    @Override
    protected void initLayoutManager() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private int mTotalCount = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mTotalCount = ((StaffListAdapter) mListAdapter).getSelectedCount();
        mTipView.setText(getString(R.string.share_tips, mTotalCount) + mShareTipSuffix);
    }
}
