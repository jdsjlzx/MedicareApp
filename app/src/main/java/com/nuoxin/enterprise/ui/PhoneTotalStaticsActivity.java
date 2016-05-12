package com.nuoxin.enterprise.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseStatisticsActivity;
import com.nuoxin.enterprise.bean.PhoneTotalStatic;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.widget.circleprogressview.CircleProgressView;

import butterknife.Bind;
import butterknife.OnClick;


public class PhoneTotalStaticsActivity extends BaseStatisticsActivity {
    private String TAG = "PhoneTotalStaticdActivity";
    @Bind(R.id.tv_meetingcounts)
    TextView mTv_meetingcounts;
    @Bind(R.id.tv_totaltime)
    TextView mTv_totaltime;

    @Bind(R.id.circleView1)
    CircleProgressView mCircleView1;
    @Bind(R.id.tv_count)
    TextView mTv_count;
    @Bind(R.id.tv_time)
    TextView mTv_time;
    @Bind(R.id.tv_countcategory)
    TextView mTv_countcategory;


    @Bind(R.id.tv_joincount)
    TextView mTv_joincount;
    @Bind(R.id.tv_askcount)
    TextView mTv_askcount;
    @Bind(R.id.tv_answercount)
    TextView mTv_answercount;

    public int durations;
    public int askCounts;
    public int answerCounts;
    public int answerPersonCounts;
    public int askPersonCounts;
    public int attendCounts;
    public int attendPersonCounts;
    public int meetingCounts;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_phonetotalstatics;
    }

    int productId;

    @Override
    public void initView() {
        mTitleText.setText("电话统计");
        mBackBtn.setVisibility(View.VISIBLE);
        productId = getIntent().getIntExtra("productId", -1);
        Log.d(TAG, "initView productId =" + productId);
        NuoXinApi.getPhoneTotalstatic(productId, mResponseHandler);
    }

    @Override
    public void initData() {

    }

    int type = 0;

    @OnClick(R.id.tv_joincount)
    void joinPeople() {
        type = 0;
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, attendPersonCounts, true);
//        mTv_countcategory.setText("参会人数");
        mTv_count.setText("" + attendPersonCounts);
        mTv_time.setText("" + attendCounts);
        changeTextColor(mTv_joincount, mTv_askcount, mTv_answercount);

    }


    @OnClick(R.id.tv_askcount)
    void askPeople() {
        type = 1;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, askPersonCounts, true);
//        mTv_countcategory.setText("提问人数");
        mTv_count.setText("" + askPersonCounts);
        mTv_time.setText("" + askCounts);
        changeTextColor(mTv_askcount, mTv_joincount, mTv_answercount);
    }

    @OnClick(R.id.tv_answercount)
    void answerPeople() {
        type = 2;
        setParams(mCircleView1, 100, 0, 0, "", false, 0, 15, true, 0.9f, 0.9f, answerPersonCounts, true);
//        mTv_countcategory.setText("答题人数");
        mTv_count.setText("" + answerPersonCounts);
        mTv_time.setText("" + answerCounts);
        changeTextColor(mTv_answercount, mTv_askcount, mTv_joincount);
    }

    @OnClick(R.id.circleView1)
    void circleClick() {
        Bundle bundle = new Bundle();
        bundle.putInt("projectType", Constant.MOBILE);
        bundle.putSerializable("type", type);
        bundle.putBoolean("isSummary", true);
        bundle.putInt("productId", productId);
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
        PhoneTotalStatic pts = JsonUtil.getPhoneTotalStatics(json);
        askCounts = pts.askCounts;
        answerCounts = pts.answerCounts;
        answerPersonCounts = pts.answerPersonCounts;
        askPersonCounts = pts.askPersonCounts;
        attendCounts = pts.attendCounts;
        attendPersonCounts = pts.attendPersonCounts;
        meetingCounts = pts.meetingCounts;

        answerCounts = pts.answerPersonCounts;
        askCounts = pts.askPersonCounts;
        mTv_meetingcounts.setText(pts.meetingCounts + "");
        mTv_totaltime.setText(pts.durations + "分钟");
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, attendPersonCounts, true);
//        mTv_countcategory.setText("参会人数");
        mTv_count.setText("" + attendPersonCounts);
        mTv_time.setText("" + attendCounts);
        changeTextColor(mTv_joincount, mTv_askcount, mTv_answercount);
    }


    public void changeTextColor(TextView tv, TextView tv1, TextView tv2) {
        tv.setTextColor(Color.parseColor("#68C7C0"));
        tv1.setTextColor(Color.parseColor("#ACACAC"));
        tv2.setTextColor(Color.parseColor("#ACACAC"));
    }
}