package com.nuoxin.enterprise.ui;

import android.graphics.Color;
import android.os.Bundle;
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


public class PhoneStaticsActivity extends BaseStatisticsActivity {
    private String TAG = "PhoneStaticsActivity";
    @Bind(R.id.tv_mytitle)
    TextView mTv_title;
    @Bind(R.id.tv_date)
    TextView mTv_date;
    @Bind(R.id.tv_time)
    TextView mTv_time;
    @Bind(R.id.circleView1)
    CircleProgressView mCircleView1;
    @Bind(R.id.tv_count)
    TextView mTv_count;
    @Bind(R.id.tv_countcategory)
    TextView mTv_countcategory;


    @Bind(R.id.tv_joincount)
    TextView mTv_joincount;
    @Bind(R.id.tv_askcount)
    TextView mTv_askcount;
    @Bind(R.id.tv_answercount)
    TextView mTv_answercount;


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
        return R.layout.activity_phonestatics;
    }

    int meetingId;

    @Override
    public void initView() {
        mTitleText.setText("电话统计");
        mBackBtn.setVisibility(View.VISIBLE);
        meetingId = getIntent().getIntExtra("meetingId", -1);
        NuoXinApi.getPhoneMeetingDetails(meetingId, mResponseHandler);
    }

    @Override
    public void initData() {
//        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, 77, true);
//        mTv_countcategory.setText("观看人数");
//        mTv_count.setText(""+77);
    }


    int type = 0;

    @OnClick(R.id.tv_joincount)
    void joinPeople() {
        type = 0;
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, watchLiveCounts, true);
        mTv_countcategory.setText("参会人数");
        mTv_count.setText("" + watchLiveCounts);
        changeTextColor(mTv_joincount, mTv_askcount, mTv_answercount);

    }


    @OnClick(R.id.tv_askcount)
    void askPeople() {
        type = 1;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, reviewCounts, true);
        mTv_countcategory.setText("提问人数");
        mTv_count.setText("" + askCounts);
        changeTextColor(mTv_askcount, mTv_joincount, mTv_answercount);
    }

    @OnClick(R.id.tv_answercount)
    void answerPeople() {
        type = 2;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, answerCounts, true);
        mTv_countcategory.setText("答题人数");
        mTv_count.setText("" + answerCounts);
        changeTextColor(mTv_answercount, mTv_askcount, mTv_joincount);
    }


    @OnClick(R.id.circleView1)
    void circleClick() {
        Bundle bundle = new Bundle();
        bundle.putInt("projectType", Constant.MOBILE);
        bundle.putSerializable("type", type);
        bundle.putBoolean("isSummary", false);
        bundle.putInt("meetingId", meetingId);
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
        MeetingDetails meetDetails = JsonUtil.getPhoneMeetingDetails(json);
        watchLiveCounts = meetDetails.watchLiveCounts;
        reviewCounts = meetDetails.reviewCounts;
        answerCounts = meetDetails.answerCounts;
        commentCounts = meetDetails.commentCounts;
        shareCounts = meetDetails.shareCounts;
        askCounts = meetDetails.askCounts;
        commentCounts = meetDetails.commentCounts;
        readCounts = meetDetails.readCounts;
        mTv_title.setText(meetDetails.title);
        mTv_date.setText(meetDetails.day);
        mTv_time.setText("电话时长：" + meetDetails.durations + " 分钟");
        mTv_countcategory.setText("参会人数");
        mTv_count.setText("" + watchLiveCounts);
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, watchLiveCounts, true);
    }


    public void changeTextColor(TextView tv, TextView tv1, TextView tv2) {
        tv.setTextColor(Color.parseColor("#68C7C0"));
        tv1.setTextColor(Color.parseColor("#ACACAC"));
        tv2.setTextColor(Color.parseColor("#ACACAC"));
    }
}