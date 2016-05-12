package com.nuoxin.enterprise.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseStatisticsActivity;
import com.nuoxin.enterprise.bean.FollwUpClinic;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.widget.circleprogressview.CircleProgressView;
import com.nuoxin.enterprise.widget.circleprogressview.TextMode;

import butterknife.Bind;
import butterknife.OnClick;

public class SecondActivity extends BaseStatisticsActivity {
    private String TAG = "SecondActivity";
    @Bind(R.id.tv_applypeople_count1)
    TextView mTv_applypeople1;
    @Bind(R.id.tv_passpeople_count1)
    TextView mTv_passpeople11;
    @Bind(R.id.tv_applypeople_count2)
    TextView Mtv_applypeople2;
    @Bind(R.id.tv_passpeople_count2)
    TextView mTv_passpeople2;
    @Bind(R.id.circleView1)
    CircleProgressView mCircleView1;


    @Bind(R.id.tv_clientpassrate)
    TextView mTv_clientpassrate;
    @Bind(R.id.tv_personcount)
    TextView mTv_personcount;
    @Bind(R.id.follow_personcount)
    TextView mFollow_personcount;


    @Bind(R.id.circleView2)
    CircleProgressView mCircleView2;
    @Bind(R.id.tv_followuprate)
    TextView mTv_followuprate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_two;
    }

    int productId;

    @Override
    public void initView() {
        mTitleText.setText("随诊型项目统计");
        mBackBtn.setVisibility(View.VISIBLE);
        productId = getIntent().getIntExtra("productId", -1);
        NuoXinApi.getFollowUpClinic(productId, mResponseHandler);
    }

    @Override
    public void initData() {

    }


    public void setParams(CircleProgressView ci, String Unit, int ValueAnimateds) {
        ci.setMaxValue(100);
        ci.setValueAnimated(ValueAnimateds); // stops spinning. Spinner spins until on top. Then fills to set value.
        ci.setTextMode(TextMode.TEXT); // show text while spinning
        ci.setUnitVisible(false);
    }

    @Override
    public void handleData(String json) {
        TLog.log(TAG, "json" + json);
        FollwUpClinic follwUpClinic = JsonUtil.getFollwUpClinic(json);
        mTv_applypeople1.setText("" + follwUpClinic.applyCounts);
        mTv_passpeople11.setText("" + follwUpClinic.passCounts);
        mTv_personcount.setText(follwUpClinic.startCounts + "");
        mFollow_personcount.setText(follwUpClinic.finishCounts + "");
        Mtv_applypeople2.setText("" + follwUpClinic.finishCounts);
        mTv_passpeople2.setText("" + follwUpClinic.unfinishCounts);

        double b = Double.parseDouble(follwUpClinic.passRate);
        double c = Double.parseDouble(follwUpClinic.finishRate);

        setParams(mCircleView1, "%", (int)(b * 100));
        mTv_clientpassrate.setText((int)(b * 100) + "%");
        setParams(mCircleView2, "%", (int) (c * 100));
        mTv_followuprate.setText((int) (c * 100) + "%");

    }

    @OnClick(R.id.count0)
    void count0Click() {
        gotoDetail(0);
    }

    @OnClick(R.id.count1)
    void count1Click() {
        gotoDetail(1);
    }

    @OnClick(R.id.count2)
    void count2Click() {
        gotoDetail(2);
    }

    @OnClick(R.id.count3)
    void count3Click() {
        gotoDetail(3);
    }

    void gotoDetail(int type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        bundle.putInt("projectType", Constant.FOLLOWUP);
        bundle.putInt("productId", productId);
        bundle.putInt(Constant.FRAGMENT_DETAILS, Constant.FRAGMENT_DOCTOR_STATISTICS_DETAIL);
        IntentUtil.startActivity(this, FragmentDetailsActivity.class, bundle);
    }
}
