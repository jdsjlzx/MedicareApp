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
import com.nuoxin.enterprise.bean.TotalStaticCourse;
import com.nuoxin.enterprise.ui.FragmentDetailsActivity;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.widget.circleprogressview.CircleProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class CourseFragment extends BaseStatisticsFragment {
    private String TAG = "CourseFragment";
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

    public static CourseFragment newInstance(int type, int productId, boolean isSummary) {
        Bundle args = new Bundle();
        CourseFragment fragment = new CourseFragment();
        args.putInt("type", type);
        args.putInt("productId", productId);
        args.putBoolean("isSummary", isSummary);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course, container, false);
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
        int productid = getArguments().getInt("productId");
        TLog.log(TAG, productid + "");
        NuoXinApi.getTotalstaticsCourse(productid, mResponseHandler);

    }

    int type = 1;

    @OnClick(R.id.tv_seelivecount)
    void applyPeople() {
        type = 1;
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, watchLiveCounts, true);
        mTv_countcategory.setText("观看人数");
        mTv_count.setText("" + watchLiveCounts);
        changeTextColor(mTv_seelivecount, mTv_reviewcount, mTv_joinanswercount, mTv_comment, mTv_sharecount, mTv_askcount, mTv_collectcount);
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
        type = 6;
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
        bundle.putInt("projectType", getArguments().getInt("type"));
        bundle.putInt("productId", getArguments().getInt("productId"));
        bundle.putInt("atype", 1);
        bundle.putBoolean("isSummary", getArguments().getBoolean("isSummary"));
        bundle.putInt(Constant.FRAGMENT_DETAILS, Constant.FRAGMENT_DOCTOR_STATISTICS_DETAIL);
        IntentUtil.startActivity(getActivity(), FragmentDetailsActivity.class, bundle);
    }

    @Override
    public void handleData(String json) {
        TLog.log(TAG, json);
        TotalStaticCourse tsc = JsonUtil.getTotalStaticsCourse(json);
        watchLiveCounts = tsc.watchLiveNumber;
        reviewCounts = tsc.reviewNumber;
        answerCounts = tsc.answerNumber;
        commentCounts = tsc.commentNumber;
        shareCounts = tsc.shareNumber;
        askCounts = tsc.askNumber;
        collectCounts = tsc.collectNumber;
        setParams(mCircleView1, 100, 0, 20, "", false, 0, 15, true, 0.9f, 0.9f, watchLiveCounts, true);
        mTv_countcategory.setText("观看人数");
        mTv_count.setText("" + watchLiveCounts);
        changeTextColor(mTv_seelivecount, mTv_reviewcount, mTv_joinanswercount, mTv_comment, mTv_sharecount, mTv_askcount, mTv_collectcount);
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
