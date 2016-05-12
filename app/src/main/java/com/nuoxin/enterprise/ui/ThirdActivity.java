package com.nuoxin.enterprise.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseStatisticsActivity;
import com.nuoxin.enterprise.bean.MeetingDetails;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.widget.circleprogressview.CircleProgressView;

import butterknife.Bind;
import butterknife.OnClick;


public class ThirdActivity extends BaseStatisticsActivity {
    private String TAG = "ThirdActivity";

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
    @Bind(R.id.tv_seelivecount)
    TextView mTv_seelivecount;
    @Bind(R.id.tv_reviewcount)
    TextView mTv_reviewcount;
    @Bind(R.id.tv_joinanswercount)
    TextView mTv_joinanswercount;
    @Bind(R.id.tv_comment)
    TextView mTv_comment;
    @Bind(R.id.tv_sharecount)
    TextView mTv_sharecount;
    @Bind(R.id.tv_askcount)
    TextView mTv_askcount;
    @Bind(R.id.tv_collectcount)
    TextView mTv_collectcount;

    private int watchLiveCounts;
    private int reviewCounts;
    private int answerCounts;
    private int commentCounts;
    private int shareCounts;
    private int askCounts;
    private int collectCounts;
    private int readCounts;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_three;
    }

    @Override
    public void initView() {
        mTitleText.setText("汇总统计");
        mBackBtn.setVisibility(View.VISIBLE);
        int dataId = getIntent().getIntExtra("dataId", -1);
        int projectType = getIntent().getIntExtra("projectType", -1);
        Log.d(TAG, "initView dataId =" + dataId + "projectType" + projectType);
        NuoXinApi.getAppWebMeetingDetails(dataId, projectType, mResponseHandler);
    }

    @Override
    public void initData() {
//        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, 77, true);
//        mTv_countcategory.setText("观看人数");
//        mTv_count.setText(""+77);
    }

    int type = 1;

    @OnClick(R.id.tv_seelivecount)
    void applyPeople() {
        type = 1;
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, watchLiveCounts, true);
        mTv_countcategory.setText("观看人数");
        mTv_count.setText("" + watchLiveCounts);
        changeTextColor(mTv_seelivecount, mTv_reviewcount, mTv_joinanswercount, mTv_comment, mTv_sharecount, mTv_askcount, mTv_collectcount);
//	    mCircleView1.setMaxValue(100);
//        mCircleView1.setValue(0);
//        mCircleView1.setValueAnimated(20);

//        //show unit
//        mCircleView1.setUnit("");
//        mCircleView1.setShowUnit(false);
//
//        mCircleView1.setTextSize(0); // text size set, auto text size off
//        mCircleView1.setUnitSize(15); // if i set the text size i also have to set the unit size
//        mCircleView1.setAutoTextSize(true); // enable auto text size, previous values are overwritten
//        //if you want the calculated text sizes to be bigger/smaller you can do so via
//        mCircleView1.setUnitScale(0.9f);
//        mCircleView1.setTextScale(0.9f);
//
//
//        mCircleView1.spin(); // start spinning
//        mCircleView1.stopSpinning(); // stops spinning. Spinner gets shorter until it disappears.
//        mCircleView1.setValueAnimated(19); // stops spinning. Spinner spins until on top. Then fills to set value.
//        mCircleView1.setShowTextWhileSpinning(true);
    }


    @OnClick(R.id.tv_reviewcount)
    void joinPeople() {
        type = 2;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, reviewCounts, true);
        mTv_countcategory.setText("回看人数");
        mTv_count.setText("" + reviewCounts);
        changeTextColor(mTv_reviewcount, mTv_seelivecount, mTv_joinanswercount, mTv_comment, mTv_sharecount, mTv_askcount, mTv_collectcount);
    }

    @OnClick(R.id.tv_joinanswercount)
    void signPeople() {
        type = 3;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, answerCounts, true);
        mTv_countcategory.setText("答题人数");
        mTv_count.setText("" + answerCounts);
        changeTextColor(mTv_joinanswercount, mTv_reviewcount, mTv_seelivecount, mTv_comment, mTv_sharecount, mTv_askcount, mTv_collectcount);
    }


    @OnClick(R.id.tv_comment)
    void commentPeople() {
        type = 4;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, commentCounts, true);
        mTv_countcategory.setText("评论人数");
        mTv_count.setText("" + commentCounts);
        changeTextColor(mTv_comment, mTv_joinanswercount, mTv_reviewcount, mTv_seelivecount, mTv_sharecount, mTv_askcount, mTv_collectcount);
    }

    @OnClick(R.id.tv_sharecount)
    void sharePeople() {
        type = 5;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, shareCounts, true);
        mTv_countcategory.setText("分享人数");
        mTv_count.setText("" + shareCounts);
        changeTextColor(mTv_sharecount, mTv_comment, mTv_joinanswercount, mTv_reviewcount, mTv_seelivecount, mTv_askcount, mTv_collectcount);
    }

    @OnClick(R.id.tv_askcount)
    void askPeople() {
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, askCounts, true);
        mTv_countcategory.setText("提问人数");
        mTv_count.setText("" + askCounts);
        changeTextColor(mTv_askcount, mTv_sharecount, mTv_comment, mTv_joinanswercount, mTv_reviewcount, mTv_seelivecount, mTv_collectcount);
    }

    @OnClick(R.id.tv_collectcount)
    void collectionPeople() {
        type = 7;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, collectCounts, true);
        mTv_countcategory.setText("收藏人数");
        mTv_count.setText("" + collectCounts);
        changeTextColor(mTv_collectcount, mTv_askcount, mTv_sharecount, mTv_comment, mTv_joinanswercount, mTv_reviewcount, mTv_seelivecount);
    }

    @OnClick(R.id.circleView1)
    void circleClick() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        bundle.putInt("projectType", getIntent().getIntExtra("from", 1));
        bundle.putInt("productId", getIntent().getIntExtra("productId", -1));
        bundle.putInt("atype", getIntent().getIntExtra("atype", -1));
        bundle.putInt("meetingId", getIntent().getIntExtra("dataId", -1));
        bundle.putBoolean("isSummary", false);
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
    }


    @Override
    public void handleData(String json) {
        TLog.log(TAG, "json" + json);
        MeetingDetails meetDetails = JsonUtil.getMeetingDetails(json);
        watchLiveCounts = meetDetails.watchLiveCounts;
        reviewCounts = meetDetails.reviewCounts;
        answerCounts = meetDetails.answerCounts;
        commentCounts = meetDetails.commentCounts;
        shareCounts = meetDetails.shareCounts;
        askCounts = meetDetails.askCounts;
        commentCounts = meetDetails.commentCounts;
        collectCounts = meetDetails.collectCounts;
        if (TextUtils.isEmpty(getIntent().getStringExtra("meetingTitle"))) {
            mTv_title.setText(meetDetails.title);
        } else {
            mTv_title.setText(getIntent().getStringExtra("meetingTitle"));
        }
        if (TextUtils.isEmpty(getIntent().getStringExtra("meetingPublishtime"))) {
            mTv_data.setText(meetDetails.publishTime);
        } else {
            mTv_data.setText(getIntent().getStringExtra("meetingPublishtime"));
        }


        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, watchLiveCounts, true);
        mTv_countcategory.setText("观看人数");
        mTv_count.setText("" + watchLiveCounts);
        changeTextColor(mTv_seelivecount, mTv_reviewcount, mTv_joinanswercount, mTv_comment, mTv_sharecount, mTv_askcount, mTv_collectcount);

    }


    public void changeTextColor(TextView tv, TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5, TextView tv6) {
        tv.setTextColor(Color.parseColor("#68C7C0"));
        tv1.setTextColor(Color.parseColor("#ACACAC"));
        tv2.setTextColor(Color.parseColor("#ACACAC"));
        tv3.setTextColor(Color.parseColor("#ACACAC"));
        tv4.setTextColor(Color.parseColor("#ACACAC"));
        tv5.setTextColor(Color.parseColor("#ACACAC"));
        tv6.setTextColor(Color.parseColor("#ACACAC"));
    }

}