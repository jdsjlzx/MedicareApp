package com.nuoxin.enterprise.ui;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cundong.recyclerview.CommonHeader;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.HeaderSpanSizeLookup;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.DoctorClassRoomAdapter;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseListActivity;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.ClassRoom;
import com.nuoxin.enterprise.bean.Doctor;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.widget.CircleImageView;

import java.util.List;

public class InvolvedProjectActivity extends BaseListActivity {

    private CircleImageView mAvtarImage;
    private TextView mNameText;
    private TextView mDepartmentText;
    private TextView mMobileText;
    private TextView mEmailText;
    private CommonHeader headerView;
    private Doctor mDoctor;

    @Override
    public void initView() {
        super.initView();
        mTitleBar.setVisibility(View.VISIBLE);
        mBackBtn.setVisibility(View.VISIBLE);

        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        mDynamicLayout.setVisibility(View.VISIBLE);
        View view = mInflater.inflate(R.layout.layout_doctor_info,null);
        mDynamicLayout.addView(view,lp);*/

        headerView = new CommonHeader(this, R.layout.layout_doctor_info);
        RecyclerViewUtils.setHeaderView(mRecyclerView, headerView);

        mAvtarImage = (CircleImageView) headerView.findViewById(R.id.avtar_image);
        mNameText = (TextView) headerView.findViewById(R.id.name_text);
        mDepartmentText = (TextView) headerView.findViewById(R.id.department_text);
        mMobileText = (TextView) headerView.findViewById(R.id.mobile_text);
        mEmailText = (TextView) headerView.findViewById(R.id.email_text);
    }

    @Override
    public void initData() {
        super.initData();
        mTitleText.setText(mDoctor.name);
        mNameText.setText(mDoctor.position);
        mDepartmentText.setText(mDoctor.hospital + "/" + mDoctor.department);
        mMobileText.setText(mDoctor.telephone);
        mEmailText.setText(mDoctor.email);

        if (TextUtils.isEmpty(mDoctor.picUrl)) {
            mAvtarImage.setImageResource(R.mipmap.ic_user_avater);
        } else {

            Glide.with(mContext)
                    .load(mDoctor.picUrl)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_user_avater)
                    .crossFade()
                    .into(mAvtarImage);

        }


    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new DoctorClassRoomAdapter((Doctor) getIntent().getSerializableExtra("doctor"));
    }

    @Override
    protected void sendRequestData() {
        mDoctor = (Doctor) getIntent().getSerializableExtra("doctor");
        NuoXinApi.getDoctorAttendCourses(mDoctor.id, mCurrentPage, mResponseHandler);
    }

    @Override
    protected List<ClassRoom> parseList(byte[] data) {
        return JsonUtil.AnalyzeClassRoomList(mDoctor.id, data);
    }


    @Override
    protected void initLayoutManager() {
        mRecyclerView.setBackgroundColor(getResources().getColor(android.R.color.white));

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        layoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), layoutManager.getSpanCount()));
        layoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);


    }

}
