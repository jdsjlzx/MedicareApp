package com.nuoxin.enterprise.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.MeetingsListAdapter;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseNewsListFragment;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.AppWebCounts;
import com.nuoxin.enterprise.bean.Meeting;
import com.nuoxin.enterprise.bean.PhoneCounts;
import com.nuoxin.enterprise.ui.ClassActivity;
import com.nuoxin.enterprise.ui.PhoneTotalStaticsActivity;
import com.nuoxin.enterprise.ui.WeChatMeetingsStatisticsActivity;
import com.nuoxin.enterprise.ui.WebTotalStaticsActivity;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;

import java.util.List;

import butterknife.OnClick;


public class MeetingsFragment extends BaseNewsListFragment {

    String TAG = "MeetingsFragment";
    private int mType;
    private int mProductId;
    private Integer mDoctorId;

    public static MeetingsFragment newInstance(int type, int productId, Integer doctorId) {

        Bundle args = new Bundle();

        MeetingsFragment fragment = new MeetingsFragment();
        args.putInt("type", type);
        args.putInt("productId", productId);
        if (null == doctorId) {
            args.putInt("doctorId", -1);
        } else {
            args.putInt("doctorId", doctorId);
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new MeetingsListAdapter(getArguments().getInt("type"), getArguments().getInt("doctorId"));
    }

    @Override
    public void onResume() {
        super.onResume();
        //if (!TextUtils.isEmpty(ClassActivity.mBeginDate) && !TextUtils.isEmpty(ClassActivity.mEndDate)) {
        onRefresh();
        //}
    }

    @Override
    protected void sendRequestData() {
        mType = getArguments().getInt("type");
        mProductId = getArguments().getInt("productId");
        mDoctorId = getArguments().getInt("doctorId");

        String startDate = null;
        String endDate = null;
        if (mType == ClassActivity.mFilterType) {
            startDate = ClassActivity.mBeginDate;
            endDate = ClassActivity.mEndDate;
        }

        switch (mType) {
            case Constant.WEIXIN:
                NuoXinApi.getWeChatMeetingsList(mDoctorId, mProductId, startDate, endDate, mCurrentPage, mResponseHandler);
                break;
            case Constant.APP:
                if (-1 == mDoctorId) {
                    NuoXinApi.getAppMeetingList(mDoctorId, mProductId, startDate, endDate, mCurrentPage, mResponseHandler);
                } else {
                    NuoXinApi.getDoctorOtherMeetingList(mType, mDoctorId, mProductId, mCurrentPage, mResponseHandler);
                }
                break;
            case Constant.WEB:
                if (-1 == mDoctorId) {
                    NuoXinApi.getWebMeetingList(mDoctorId, mProductId, startDate, endDate, mCurrentPage, mResponseHandler);
                } else {
                    NuoXinApi.getDoctorOtherMeetingList(mType, mDoctorId, mProductId, mCurrentPage, mResponseHandler);
                }

                break;
            case Constant.MOBILE:
                if (-1 == mDoctorId) {
                    NuoXinApi.getPhoneMeetingList(mDoctorId, mProductId, startDate, endDate, mCurrentPage, mResponseHandler);
                } else {
                    NuoXinApi.getDoctorOtherMeetingList(mType, mDoctorId, mProductId, mCurrentPage, mResponseHandler);
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected List<Meeting> parseList(byte[] data) throws Exception {
        List<Meeting> list = null;
        switch (mType) {
            case Constant.WEIXIN:
                list = JsonUtil.AnalyzeWeChatMeetingsList(mDoctorId, data);
                break;
            case Constant.APP:
                if (-1 == mDoctorId) {
                    list = JsonUtil.AnalyzeAppMeetingsList(mDoctorId, data);
                } else {
                    list = JsonUtil.AnalyzeDoctorOtherMeetingsList(mType, mDoctorId, data);
                }
                break;
            case Constant.WEB:
                if (-1 == mDoctorId) {
                    list = JsonUtil.AnalyzeWebMeetingsList(mDoctorId, data);
                } else {
                    list = JsonUtil.AnalyzeDoctorOtherMeetingsList(mType, mDoctorId, data);
                }
                break;
            case Constant.MOBILE:
                if (-1 == mDoctorId) {
                    list = JsonUtil.AnalyzePhoneMeetingsList(data);
                } else {
                    list = JsonUtil.AnalyzeDoctorOtherMeetingsList(mType, mDoctorId, data);
                }
                break;
            default:
                break;
        }
        return list;
    }

    private int mWeChatMeetingCount = 0;
    private AppWebCounts appWebCoursesCounts;

    @Override
    protected void initHeader(byte[] data) throws Exception {
        TLog.log(TAG, "initHeader mType = " + mType);
        //if (!TextUtils.isEmpty(ClassActivity.mBeginDate) && !TextUtils.isEmpty(ClassActivity.mEndDate)) {
        if (ClassActivity.mFilterType == mType
                && (!TextUtils.isEmpty(ClassActivity.mBeginDate) || !TextUtils.isEmpty(ClassActivity.mEndDate))) {
            mFilterDateView.setVisibility(View.VISIBLE);
            mTimeRangesText.setText(ClassActivity.mBeginDate + " 至 " + ClassActivity.mEndDate);
        } else {
            mFilterDateView.setVisibility(View.GONE);
        }

        //}

        switch (mType) {
            case Constant.WEIXIN:
                if (mDoctorId == -1) {
                    mWeChatMeetingCount = JsonUtil.getWeChatMeetingsCount(data);
                    mWechatHeaderView.setVisibility(View.VISIBLE);
                    mHeadText.setText("会议" + mWeChatMeetingCount + "个");
                    mStatisticsText.setText("查看汇总统计");
                }

                break;
            case Constant.APP:
                if (mDoctorId == -1) {
                    appWebCoursesCounts = JsonUtil.getAppCoursesCount(data);
                    mAppHeaderView.setVisibility(View.VISIBLE);
                    mCourseCountText.setText(appWebCoursesCounts.courseCounts + "个课程");
                    mArticleCountText.setText(appWebCoursesCounts.informationCounts + "篇文章");
                    mContentCountText.setText(appWebCoursesCounts.contentCounts + "专业内容");
                }
                break;

            case Constant.WEB:
                if (mDoctorId == -1) {
                    appWebCoursesCounts = JsonUtil.getWebCoursesCount(data);
                    mAppHeaderView.setVisibility(View.VISIBLE);
                    mCourseCountText.setText(appWebCoursesCounts.courseCounts + "个课程");
                    mArticleCountText.setText(appWebCoursesCounts.informationCounts + "篇文章");
                    mContentCountText.setText(appWebCoursesCounts.contentCounts + "专业内容");
                }

                break;
            case Constant.MOBILE:
                if (mDoctorId == -1) {
                    PhoneCounts phoneCounts = JsonUtil.getPhoneMeetinsCount(data);
                    mMobileHeaderView.setVisibility(View.VISIBLE);
                    mMeetingCountText.setText("会议" + phoneCounts.meetingCounts + "个");
                    mMeetingDurationText.setText("总时长" + phoneCounts.duration + "分钟");
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void initLayoutManager() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    @OnClick({R.id.statistics_text, R.id.statistics_app_text, R.id.statistics_mobile_text})
    public void onClick(View view) {
        super.onClick(view);
        Bundle bundle = new Bundle();
        bundle.putInt("productId", mProductId);
        switch (view.getId()) {
            case R.id.statistics_text:
                bundle.putInt("meeting_count", mWeChatMeetingCount);
                IntentUtil.startActivity(getActivity(), WeChatMeetingsStatisticsActivity.class, bundle);
                break;
            case R.id.statistics_app_text:
                bundle.putInt("type", getArguments().getInt("type"));
                bundle.putSerializable("course_count", appWebCoursesCounts);
                IntentUtil.startActivity(getActivity(), WebTotalStaticsActivity.class, bundle);
                break;
            case R.id.statistics_mobile_text:
                IntentUtil.startActivity(getActivity(), PhoneTotalStaticsActivity.class, bundle);
                break;
            default:
                break;
        }
    }
}
