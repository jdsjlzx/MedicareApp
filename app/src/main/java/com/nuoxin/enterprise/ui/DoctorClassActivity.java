package com.nuoxin.enterprise.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nuoxin.enterprise.AppContext;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.MyFragmentPagerAdapter;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseActivity;
import com.nuoxin.enterprise.bean.Doctor;
import com.nuoxin.enterprise.bean.DoctorMeetingCounts;
import com.nuoxin.enterprise.bean.User;
import com.nuoxin.enterprise.fragment.MeetingsFragment;
import com.nuoxin.enterprise.util.AppToast;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.DialogHelper;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class DoctorClassActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private String TAG = "DoctorClassActivity";
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;

    private CharSequence[] mLabels;
    private Doctor mDoctor;
    private int productId;
    private List<Integer> mDoctorMeetingCounts;

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
            TLog.log(TAG,"onSuccess : " +new String(bytes));

            DialogHelper.stopProgressDlg();
            String json = new String(bytes);

            if (200 == statusCode) {
                if (JsonUtil.getStateFromServer(json).equals(Constant.SUCCESS)){
                    mDoctorMeetingCounts = JsonUtil.getDoctorMeetingCounts(bytes);
                    fillData();
                }
            }

        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] responseBody,
                              Throwable arg3) {
            DialogHelper.stopProgressDlg();
            String result = new String(responseBody);
            TLog.log("BaseListActivity onFailure " + result);
            if(result.contains("Mismatched access token")) {
                AppContext.getInstance().handleLogout();
                finish();
            }
        }

    };

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private void fillData() {
        mFragments.add(MeetingsFragment.newInstance(Constant.WEIXIN, productId, mDoctor.id));
        mFragments.add(MeetingsFragment.newInstance(Constant.APP, productId, mDoctor.id));
        mFragments.add(MeetingsFragment.newInstance(Constant.WEB, productId, mDoctor.id));
        mFragments.add(MeetingsFragment.newInstance(Constant.MOBILE, productId, mDoctor.id));

        mLabels = getResources().getTextArray(R.array.class_type_labels);

        for (int i = 0; i < mLabels.length; i++) {
            mLabels[i] = mLabels[i] + "(" + mDoctorMeetingCounts.get(i) + ")";
            mTabLayout.addTab(mTabLayout.newTab().setText(mLabels[i]));
        }

        MyFragmentPagerAdapter mFragmentAdapteradapter =
                new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mLabels);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mFragmentAdapteradapter);
        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_class;
    }

    public void initView() {
        mBackBtn.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {
        productId = getIntent().getIntExtra("productId",0);
        mDoctor = (Doctor) getIntent().getSerializableExtra("doctor");
        mTitleText.setText(mDoctor.name + "-" + getIntent().getStringExtra("title"));

        NuoXinApi.getDoctorMeetingsCount(mDoctor.id, productId, mHandler);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @OnClick(R.id.right_img)
    void filterSearch() {
        IntentUtil.startActivity(DoctorClassActivity.this, FilterActivity.class);
    }

}
