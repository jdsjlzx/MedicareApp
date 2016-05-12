package com.nuoxin.enterprise.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseStatisticsActivity;
import com.nuoxin.enterprise.bean.TotalStastics;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.widget.circleprogressview.CircleProgressView;
import com.nuoxin.enterprise.widget.circleprogressview.TextMode;

import butterknife.Bind;
import butterknife.OnClick;

public class WeChatMeetingsStatisticsActivity extends BaseStatisticsActivity {
    private static String TAG = "WeChatMeetingsStatisticsActivity";
    @Bind(R.id.meetingcount)
    TextView mTv_meetingcount;
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
        TotalStastics totalStatics = JsonUtil.getTotalStatics(json);
        if (null == totalStatics) {
            return;
        }
        mTv_meetingcount.setText("会议" + getIntent().getIntExtra("meeting_count", 0) + "个");
        mTv_passrate.setText("通过率" + (int)(Double.parseDouble(totalStatics.passRate) * 100) + "%");
        mTv_signinrate.setText("签到率" + (int)(Double.parseDouble(totalStatics.signInRate) * 100) + "%");

        setParams(mCircleViewInner, 100);
        setParams(mCircleViewMiddle, (int)(Double.parseDouble(totalStatics.passRate) * 100));
        setParams(mCircleViewOut, (int)(Double.parseDouble(totalStatics.signInRate) * 100));

        setParams(mCircleViewApply, 100, totalStatics.applyCounts + "");
        setParams(mCircleViewPass, (int)(Double.parseDouble(totalStatics.passRate) * 100), totalStatics.passCounts + "");
        setParams(mCircleViewSign, (int)(Double.parseDouble(totalStatics.signInRate) * 100), totalStatics.signInCounts + "");

        mTv_percentOut.setText((int)(Double.parseDouble(totalStatics.signInRate) * 100) + "%");
        mTv_percentMiddle.setText((int)(Double.parseDouble(totalStatics.passRate) * 100) + "%");
        mTv_percentInner.setText(100 + "%");
        mTv_reviewcount.setText(totalStatics.reviewCounts + "");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wechatmeetingstatistics;
    }


    @Override
    public void initView() {
        mTitleText.setText("汇总统计");
        mBackBtn.setVisibility(View.VISIBLE);
        int productId = getIntent().getIntExtra("productId", -1);
        NuoXinApi.getWeChatMeetingsStatistics(productId, mResponseHandler);
    }

    public void setParams(CircleProgressView ci, int ValueAnimateds) {
        ci.setMaxValue(100);
        ci.setTextSize(0);
        ci.setTextMode(TextMode.TEXT);
        ci.setValueAnimated(ValueAnimateds); // stops spinning. Spinner spins until on top. Then fills to set value.
        ci.setShowTextWhileSpinning(true); // Show/hide text in spinning mode
    }

    public void setParams(CircleProgressView ci, int ValueAnimateds, String text) {
        ci.setMaxValue(100);
        ci.setValue(0);
        ci.setText(text);
        ci.setTextMode(TextMode.TEXT);
        ci.spin(); // start spinning
        ci.stopSpinning();// stops spinning. Spinner gets shorter until it disappears.
        ci.setValueAnimated(ValueAnimateds); // stops spinning. Spinner spins until on top. Then fills to set value.
        ci.setShowTextWhileSpinning(true); // Show/hide text in spinning mode
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.circleViewApply, R.id.circleViewPass, R.id.circleViewSign, R.id.replay_num})
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        switch (viewId) {
            case R.id.circleViewApply:
            case R.id.circleViewPass:
            case R.id.circleViewSign:
            case R.id.replay_num:
                Bundle bundle = new Bundle();
                if(viewId == R.id.circleViewApply){
                    bundle.putInt("type", 0);//0，申请人数，1通过人数，2签到人数，3会议回看人数
                } else if(viewId == R.id.circleViewPass){
                    bundle.putInt("type", 1);
                } else if(viewId == R.id.circleViewSign){
                    bundle.putInt("type", 2);
                } else if(viewId == R.id.replay_num){
                    bundle.putSerializable("type", 3);
                }

                bundle.putInt("projectType", Constant.WEIXIN);
                bundle.putInt("productId", getIntent().getIntExtra("productId", -1));
                bundle.putBoolean("isSummary", true);
                bundle.putInt(Constant.FRAGMENT_DETAILS, Constant.FRAGMENT_DOCTOR_STATISTICS_DETAIL);
                IntentUtil.startActivity((Activity) mContext, FragmentDetailsActivity.class, bundle);
            default:
                break;
        }


    }
}
