package com.nuoxin.enterprise.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.base.BaseActivity;
import com.nuoxin.enterprise.bean.Meeting;
import com.nuoxin.enterprise.fragment.DoctorMeetingDetailFragment;
import com.nuoxin.enterprise.fragment.DoctorsStatisticsFragment;
import com.nuoxin.enterprise.util.Constant;

public class FragmentDetailsActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment_details;
    }

    @Override
    public void initView() {
        mTitleText.setText("操作统计");
        mBackBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

        Fragment fragment = null;
        Bundle bundle = getIntent().getExtras();
        int extra = bundle.getInt(Constant.FRAGMENT_DETAILS, Constant.FRAGMENT_DOCTOR_MEETING_DETAIL);
        switch (extra) {
            case Constant.FRAGMENT_DOCTOR_STATISTICS_DETAIL:
                fragment = DoctorsStatisticsFragment.newInstance(bundle.getInt("projectType"), bundle.getInt("productId"), bundle.getInt("type")
                        , bundle.getInt("atype"), bundle.getBoolean("isSummary"), bundle.getInt("meetingId"));
                break;
            case Constant.FRAGMENT_DOCTOR_MEETING_DETAIL:
                fragment = DoctorMeetingDetailFragment.newInstance(((Meeting) bundle.getSerializable("meeting")), bundle.getInt("doctorId"));
                break;
            default:
                break;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment, fragment);
        transaction.commit();
    }
}
