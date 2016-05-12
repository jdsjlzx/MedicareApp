package com.nuoxin.enterprise.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cundong.recyclerview.HeaderLayout;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.DoctorMeetingAdapter;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseListFragment;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.DoctorMeeting;
import com.nuoxin.enterprise.bean.Meeting;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.util.Util;

import java.util.List;


public class DoctorMeetingDetailFragment extends BaseListFragment {

    private Integer mDoctorId ;
    private Meeting mMeeting;
    private HeaderLayout mHeaderView;
    private TextView mMeetingTitleText;
    private TextView mPublishTimeText;
    private TextView mDoctorNameText;
    private TextView mMeetingSpeakerText;
    private TextView mMeetingDurationText;
    private View mWeChatView;

    public static DoctorMeetingDetailFragment newInstance(Meeting meeting, Integer doctorId) {

        Bundle args = new Bundle();

        DoctorMeetingDetailFragment fragment = new DoctorMeetingDetailFragment();
        args.putSerializable("meeting",meeting);
        args.putInt("doctorId",doctorId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mMeeting = (Meeting) getArguments().getSerializable("meeting");
        mDoctorId = getArguments().getInt("doctorId");
        TLog.log("lzx","initView speaker =  " + mMeeting.speaker);
        TLog.log("lzx","initView projectType =  " + mMeeting.projectType);
        mHeaderView = new HeaderLayout(getActivity(), R.layout.layout_header_doctor_meeting_statistics);
        RecyclerViewUtils.setHeaderView(mRecyclerView, mHeaderView);

        mMeetingTitleText = (TextView) mHeaderView.findViewById(R.id.meeting_title_text);
        mPublishTimeText = (TextView) mHeaderView.findViewById(R.id.publish_time_text);
        mDoctorNameText = (TextView) mHeaderView.findViewById(R.id.doctor_name_text);
        mMeetingSpeakerText = (TextView) mHeaderView.findViewById(R.id.meeting_speaker_text);
        mMeetingDurationText = (TextView) mHeaderView.findViewById(R.id.meeting_duration_text);
        mWeChatView = mHeaderView.findViewById(R.id.meeting_wechat_view);

        if(mMeeting.projectType == 0){
            mWeChatView.setVisibility(View.VISIBLE);
            mPublishTimeText.setVisibility(View.GONE);
        }else {
            mWeChatView.setVisibility(View.GONE);
            mMeetingDurationText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {
        super.initData();

        mMeetingTitleText.setText(mMeeting.title);
        mPublishTimeText.setText(mMeeting.publishTime);
        mDoctorNameText.setText(mMeeting.speaker + "医生的统计数据");
        TLog.log("lzx","initData speaker =  " + mMeeting.speaker);
        mMeetingSpeakerText.setText("主讲人："+ mMeeting.speaker +" " + mMeeting.hospitalName);
        if(mMeeting.projectType == 0) {
            mMeetingDurationText.setText("会议参加时长："+ Util.getMeetingDuration(mMeeting.startTime,mMeeting.endTime));
        }

    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new DoctorMeetingAdapter();
    }

    @Override
    protected void sendRequestData() {
        mMeeting = (Meeting) getArguments().getSerializable("meeting");
        mDoctorId = getArguments().getInt("doctorId");
        NuoXinApi.getDoctorMeetingStatistics(mMeeting.projectType, mMeeting.meetingId,mDoctorId,mResponseHandler);
    }

    @Override
    protected List<DoctorMeeting> parseList(byte[] data) {
        List<DoctorMeeting> list = JsonUtil.AnalyzeDoctorMeetingStatistics(data, mMeeting.projectType);
        TLog.log("List<DoctorMeeting> parseList list.size() " + list.size());
        return list;
    }

    @Override
    protected void initLayoutManager() {
        mRecyclerView.setBackgroundColor(getResources().getColor(android.R.color.white));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }
}
