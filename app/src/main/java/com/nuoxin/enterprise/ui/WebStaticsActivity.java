package com.nuoxin.enterprise.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseStatisticsActivity;
import com.nuoxin.enterprise.bean.WebStatics;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.widget.circleprogressview.CircleProgressView;

import butterknife.Bind;
import butterknife.OnClick;


public class WebStaticsActivity extends BaseStatisticsActivity {
    private String TAG = "WebStaticsActivity";

    @Bind(R.id.tv_title)
    TextView mTv_title;
    @Bind(R.id.tv_data)
    TextView mTv_data;
    @Bind(R.id.tv_time)
    TextView mTv_time;
    @Bind(R.id.circleView1)
    CircleProgressView mCircleView1;
    @Bind(R.id.tv_count)
    TextView mTv_count;
    @Bind(R.id.tv_countcategory)
    TextView mTv_countcategory;


    @Bind(R.id.tv_readcount)
    TextView mTv_readcount;
    @Bind(R.id.tv_collectioncount)
    TextView mTv_collectioncount;
    @Bind(R.id.tv_commentcount)
    TextView mTv_commentcount;
    @Bind(R.id.tv_sharecount)
    TextView mTv_sharecount;


    private int readcount;
    private int collectcount;
    private int commentcount;
    private int sharecount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webstatics;
    }

    @Override
    public void initView() {
        mTitleText.setText("汇总统计");
        mBackBtn.setVisibility(View.VISIBLE);
        int meetingId = getIntent().getIntExtra("id", -1);
        Log.d(TAG, "initView meetingId =" + meetingId);
        Log.d(TAG, "jinruwebstaticsactivity=" + meetingId);
        NuoXinApi.getwebtatic(meetingId, mResponseHandler);
    }

    @Override
    public void initData() {

    }

    int type = 8;

    @OnClick(R.id.tv_readcount)
    void readPeople() {
        type = 8;
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, readcount, true);
        mTv_countcategory.setText("阅读人数");
        mTv_count.setText("" + readcount);
        changeTextColor(mTv_readcount, mTv_collectioncount, mTv_commentcount, mTv_sharecount);

    }


    @OnClick(R.id.tv_collectioncount)
    void collectionPeople() {
        type = 7;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, collectcount, true);
        mTv_countcategory.setText("收藏人数");
        mTv_count.setText("" + collectcount);
        changeTextColor(mTv_collectioncount, mTv_readcount, mTv_commentcount, mTv_sharecount);
    }

    @OnClick(R.id.tv_commentcount)
    void commentPeople() {
        type = 4;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, commentcount, true);
        mTv_countcategory.setText("评论人数");
        mTv_count.setText("" + commentcount);
        changeTextColor(mTv_commentcount, mTv_collectioncount, mTv_readcount, mTv_sharecount);
    }

    @OnClick(R.id.tv_sharecount)
    void sharePeople() {
        type = 5;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, sharecount, true);
        mTv_countcategory.setText("分享人数");
        mTv_count.setText("" + sharecount);
        changeTextColor(mTv_sharecount, mTv_commentcount, mTv_collectioncount, mTv_readcount);
    }


    @OnClick(R.id.circleView1)
    void circleClick() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        bundle.putInt("projectType", Constant.WEB);
        bundle.putInt("productId", getIntent().getIntExtra("productId", -1));
        bundle.putInt("atype", getIntent().getIntExtra("atype", -1));
        bundle.putBoolean("isSummary", false);
        bundle.putInt("meetingId", getIntent().getIntExtra("dataId", -1));
        bundle.putInt(Constant.FRAGMENT_DETAILS, Constant.FRAGMENT_DOCTOR_STATISTICS_DETAIL);
        IntentUtil.startActivity(this, FragmentDetailsActivity.class, bundle);
    }

    public void setParams(CircleProgressView ci, int MaxValue, int Value, int ValueAnimated, String Unit, boolean ShowUnit, int TextSize, int UnitSize, boolean AutoTextSize,
                          float UnitScale, float TextScale, int ValueAnimateds, boolean ShowTextWhileSpinning) {
        ci.setMaxValue(MaxValue);
        ci.setValue(Value);
        ci.setValueAnimated(ValueAnimated);
        ci.setUnit(Unit);
        ci.setTextSize(TextSize);
        ci.setUnitSize(UnitSize);
        ci.setAutoTextSize(AutoTextSize);
        ci.setUnitScale(UnitScale);
        ci.setTextScale(TextScale);
        ci.spin(); // start spinning
        ci.stopSpinning();// stops spinning. Spinner gets shorter until it disappears.
        ci.setValueAnimated(ValueAnimateds); // stops spinning. Spinner spins until on top. Then fills to set value.
        ci.setShowTextWhileSpinning(ShowTextWhileSpinning); // Show/hide text in spinning mode
        ci.setUnitVisible(false);
    }


    @Override
    public void handleData(String json) {
        TLog.log(TAG, "json" + json);
//        MeetingDetails meetDetails =JsonUtil.getMeetingDetails(json);
//        watchLiveCounts =meetDetails.watchLiveCounts;
//        reviewCounts =meetDetails.reviewCounts;
//        answerCounts =meetDetails.answerCounts;
//        commentCounts =meetDetails.commentCounts;
//        shareCounts=meetDetails.shareCounts;
//        askCounts =meetDetails.askCounts;
//        commentCounts=meetDetails.commentCounts;
//        readCounts =meetDetails.readCounts;
//        mTv_title.setText(meetDetails.title);
//        mTv_data.setText(meetDetails.publishTime);
//        mTv_countcategory.setText("阅读人数");
//        mTv_count.setText(""+readCounts);
//        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, watchLiveCounts, true);

        WebStatics ws = JsonUtil.getWebstatic(json);
        readcount = ws.watchLiveCounts;
        collectcount = ws.collectCounts;
        commentcount = ws.commentCounts;
        sharecount = ws.shareCounts;
        mTv_title.setText(getIntent().getStringExtra("meetingTitle"));
        mTv_data.setText(getIntent().getStringExtra("meetingPublishtime"));
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, readcount, true);
        mTv_countcategory.setText("阅读人数");
        mTv_count.setText("" + readcount);
        changeTextColor(mTv_readcount, mTv_collectioncount, mTv_commentcount, mTv_sharecount);


    }


    public void changeTextColor(TextView tv, TextView tv1, TextView tv2, TextView tv3) {
        tv.setTextColor(Color.parseColor("#68C7C0"));
        tv1.setTextColor(Color.parseColor("#ACACAC"));
        tv2.setTextColor(Color.parseColor("#ACACAC"));
        tv3.setTextColor(Color.parseColor("#ACACAC"));
    }

}