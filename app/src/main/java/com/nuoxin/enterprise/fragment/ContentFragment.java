package com.nuoxin.enterprise.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseStatisticsFragment;
import com.nuoxin.enterprise.bean.ContentStatic;
import com.nuoxin.enterprise.ui.FragmentDetailsActivity;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.widget.circleprogressview.CircleProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentFragment extends BaseStatisticsFragment {
    private String TAG = "ContentFragment";
    @Bind(R.id.circleView1)
    CircleProgressView mCircleView1;
    @Bind(R.id.tv_count)
    TextView mTv_count;
    @Bind(R.id.tv_countcategory)
    TextView mTv_countcategory;

    @Bind(R.id.tv_readcount)
    TextView mTv_readcount;
    @Bind(R.id.tv_collectcount)
    TextView mTv_collectioncount;
    @Bind(R.id.tv_commentcount)
    TextView mTv_commentcount;
    @Bind(R.id.tv_sharecount)
    TextView mTv_sharecount;


    public int commentNumber;
    public int commentTime;
    public int shareNumber;
    public int shareTime;
    public int readNumber;
    public int readTime;
    public int collectNumber;
    public int collectTime;

    public static ContentFragment newInstance(int type, int productId, boolean isSummary) {

        Bundle args = new Bundle();

        ContentFragment fragment = new ContentFragment();
        args.putInt("productId", productId);
        args.putInt("type", type);
        args.putBoolean("isSummary", isSummary);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        int productid = getArguments().getInt("productId", -1);
        int projecttype = Constant.PROJECT_TYPE_CONTENT;
        NuoXinApi.getAppWebStatic(productid, projecttype, mResponseHandler);

    }

    int type = 8;

    @OnClick(R.id.tv_readcount)
    void readPeople() {
        type = 8;
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, readNumber, true);
        mTv_countcategory.setText("" + readTime);
        mTv_count.setText("" + readNumber);
        changeTextColor(mTv_readcount, mTv_collectioncount, mTv_commentcount, mTv_sharecount);
    }

    @OnClick(R.id.tv_collectcount)
    void collectPeople() {
        type = 7;
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, collectNumber, true);
        mTv_countcategory.setText("" + collectTime);
        mTv_count.setText("" + collectNumber);
        changeTextColor(mTv_collectioncount, mTv_readcount, mTv_commentcount, mTv_sharecount);
    }

    @OnClick(R.id.tv_commentcount)
    void commentPeople() {
        type = 4;
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, commentNumber, true);
        mTv_countcategory.setText("" + commentTime);
        mTv_count.setText("" + commentNumber);
        changeTextColor(mTv_commentcount, mTv_readcount, mTv_collectioncount, mTv_sharecount);
    }


    @OnClick(R.id.tv_sharecount)
    void sharePeople() {
        type = 5;
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, shareNumber, true);
        mTv_countcategory.setText("" + shareTime);
        mTv_count.setText("" + shareNumber);
        changeTextColor(mTv_sharecount, mTv_readcount, mTv_collectioncount, mTv_commentcount);
    }

    @OnClick(R.id.circleView1)
    void circleClick() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        bundle.putInt("projectType", getArguments().getInt("type"));
        bundle.putInt("productId", getArguments().getInt("productId"));
        bundle.putInt("atype", 3);
        bundle.putBoolean("isSummary", getArguments().getBoolean("isSummary"));
        bundle.putInt(Constant.FRAGMENT_DETAILS, Constant.FRAGMENT_DOCTOR_STATISTICS_DETAIL);
        IntentUtil.startActivity(getActivity(), FragmentDetailsActivity.class, bundle);
    }


    @Override
    public void handleData(String json) {
        TLog.log(TAG, json);
        ContentStatic cs = JsonUtil.getContentStatic(json);
        commentNumber = cs.commentNumber;
        commentTime = cs.commentTime;
        shareNumber = cs.shareNumber;
        shareTime = cs.shareTime;
        readNumber = cs.readNumber;
        readTime = cs.readTime;
        collectNumber = cs.collectNumber;
        collectTime = cs.collectTime;

        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, readNumber, true);
        mTv_countcategory.setText("" + readTime);
        mTv_count.setText("" + readNumber);
        changeTextColor(mTv_readcount, mTv_collectioncount, mTv_commentcount, mTv_sharecount);

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


    public void changeTextColor(TextView tv, TextView tv1, TextView tv2, TextView tv3) {
        tv.setTextColor(Color.parseColor("#68C7C0"));
        tv1.setTextColor(Color.parseColor("#ACACAC"));
        tv2.setTextColor(Color.parseColor("#ACACAC"));
        tv3.setTextColor(Color.parseColor("#ACACAC"));
    }


}
