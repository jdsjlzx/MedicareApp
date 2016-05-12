package com.nuoxin.enterprise.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseStatisticsActivity;
import com.nuoxin.enterprise.bean.Wechatstatics;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.Util;
import com.nuoxin.enterprise.widget.circleprogressview.CircleProgressView;
import com.nuoxin.enterprise.widget.circleprogressview.TextMode;

import butterknife.Bind;
import butterknife.OnClick;

public class FirstActivity extends BaseStatisticsActivity {
    private static String TAG = "FirstActivity";

    @Bind(R.id.tv_title)
    TextView mTv_title;
    @Bind(R.id.tv_speaker)
    TextView mTv_speaker;
    @Bind(R.id.tv_meetingtime)
    TextView mTv_meetingtime;
    @Bind(R.id.tv_passrate)
    TextView mTv_passrate;
    @Bind(R.id.tv_signinrate)
    TextView mTv_signinrate;
    @Bind(R.id.tv_percentOut)
    TextView mTv_percentOut;
    @Bind(R.id.tv_percentMiddle)
    TextView mTv_percentMiddle;
    @Bind(R.id.tv_percentInner)
    TextView mTv_percentInner;

    @Bind(R.id.circleViewOut)
    CircleProgressView mCircleViewOut;
    @Bind(R.id.circleViewMiddle)
    CircleProgressView mCircleViewMiddle;
    @Bind(R.id.circleViewInner)
    CircleProgressView mCircleViewInner;

    @Bind(R.id.circleViewApply)
    CircleProgressView mCircleViewApply;
    @Bind(R.id.circleViewPass)
    CircleProgressView mCircleViewPass;
    @Bind(R.id.circleViewSign)
    CircleProgressView mCircleViewSign;
    @Bind(R.id.tv_reviewcount)
    TextView mTv_reviewcount;

    @Override
    public void handleData(String json) {
        Wechatstatics wechatstatics = JsonUtil.getWechatstatics(json);
        mTv_title.setText(wechatstatics.title);
        mTv_speaker.setText(wechatstatics.speaker);
        mTv_meetingtime.setText("参加会议时长 : " + Util.getMeetingDuration(wechatstatics.startTime, wechatstatics.endTime));
        mTv_passrate.setText("通过率" + ((int)(Double.parseDouble(wechatstatics.passRate) * 100)) + "%");
        mTv_signinrate.setText("签到率" + ((int)(Double.parseDouble(wechatstatics.signInRate)) * 100) + "%");
        int applyCount = wechatstatics.applyCounts;
        int passCount = wechatstatics.passCounts;
        int signInCount = wechatstatics.signInCounts;
        mTv_reviewcount.setText(wechatstatics.reviewCounts + "");
        setParams(mCircleViewInner, "%", true, 0, 0, Util.div(applyCount,applyCount)*100, "");
        setParams(mCircleViewMiddle, "%", true, 0, 0, Util.div(passCount,applyCount)*100, "");
        setParams(mCircleViewOut, "%", true, 0, 0, Util.div(signInCount,applyCount)*100, "");
        mTv_percentOut.setText(signInCount + "%");
        mTv_percentMiddle.setText(passCount + "%");
        mTv_percentInner.setText(Util.div(applyCount,applyCount)*100 + "%");
        setParams(mCircleViewApply, "", false, 22, 22, Util.div(applyCount,applyCount)*100, applyCount + "");
        setParams(mCircleViewPass, "", false, 15, 15, Util.div(passCount,applyCount)*100, passCount + "");
        setParams(mCircleViewSign, "", false, 15, 15, Util.div(signInCount,applyCount)*100, signInCount + "");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_one;
    }


    private int meetingId;

    @Override
    public void initView() {
        mTitleText.setText("微信统计");
        mBackBtn.setVisibility(View.VISIBLE);
        meetingId = getIntent().getIntExtra("meetingId", -1);
        NuoXinApi.getWeChatMeetingStatistics(meetingId, mResponseHandler);


    }


    public void setParams(final CircleProgressView ci, String unit, boolean ShowUnit, int TextSize, int UnitSize, int ValueAnimateds, final String text) {
        ci.setShowTextWhileSpinning(false); // Show/hide text in spinning mode
        ci.spin(); // start spinning
        ci.stopSpinning();// stops spinning. Spinner gets shorter until it disappears.
        ci.setValueAnimated(ValueAnimateds); // stops spinning. Spinner spins until on top. Then fills to set value.
        ci.setText(text);
        ci.setTextMode(TextMode.TEXT); // show text while spinning
        ci.setUnitVisible(false);

    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.circleViewApply, R.id.circleViewPass, R.id.circleViewSign})
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        switch (viewId) {
            case R.id.circleViewApply:
            case R.id.circleViewPass:
            case R.id.circleViewSign:
                Bundle bundle = new Bundle();
                if(viewId == R.id.circleViewApply){
                    bundle.putInt("type", 0);//0，申请人数，1通过人数，2签到人数，3会议回看人数
                } else if(viewId == R.id.circleViewPass){
                    bundle.putInt("type", 1);
                } else if(viewId == R.id.circleViewSign){
                    bundle.putInt("type", 2);
                }
                bundle.putInt("projectType", Constant.WEIXIN);
                bundle.putInt("productId", getIntent().getIntExtra("productId", -1));
                bundle.putInt("meetingId", meetingId);
                bundle.putInt(Constant.FRAGMENT_DETAILS, Constant.FRAGMENT_DOCTOR_STATISTICS_DETAIL);
                IntentUtil.startActivity((Activity) mContext, FragmentDetailsActivity.class, bundle);
                break;
            default:
                break;
        }


    }
}
