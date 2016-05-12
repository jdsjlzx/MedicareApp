package com.nuoxin.enterprise.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.MyFragmentPagerAdapter;
import com.nuoxin.enterprise.base.BaseActivity;
import com.nuoxin.enterprise.fragment.MeetingsFragment;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ClassActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;

    private CharSequence[] mLabels;
    int productId;

    public static String mBeginDate;
    public static String mEndDate;
    public static int mFilterIndex;
    public static int mFilterType;


    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private void fillData() {
        mFragments.add(MeetingsFragment.newInstance(Constant.WEIXIN, productId, null));
        mFragments.add(MeetingsFragment.newInstance(Constant.APP, productId, null));
        mFragments.add(MeetingsFragment.newInstance(Constant.WEB, productId, null));
        mFragments.add(MeetingsFragment.newInstance(Constant.MOBILE, productId, null));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_class;
    }

    public void initView() {
        mBackBtn.setVisibility(View.VISIBLE);
        mRightBtn.setVisibility(View.VISIBLE);
        mRightBtn.setImageResource(R.mipmap.ic_filter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void initData() {
        productId = getIntent().getIntExtra("productId",0);
        mTitleText.setText(getIntent().getStringExtra("title"));
        mLabels = getResources().getTextArray(R.array.class_type_labels);
        for(int i=0;i<mLabels.length;i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(mLabels[i]));
        }
        fillData();
        MyFragmentPagerAdapter mFragmentAdapteradapter =
                new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mLabels);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mFragmentAdapteradapter);
        mTabLayout.setupWithViewPager(mViewPager);

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
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra("position", mViewPager.getCurrentItem());
        switch (mViewPager.getCurrentItem()){
            case 0:
                intent.putExtra("type", Constant.WEIXIN);
                break;
            case 1:
                intent.putExtra("type", Constant.APP);
                break;
            case 2:
                intent.putExtra("type", Constant.WEB);
                break;
            case 3:
                intent.putExtra("type", Constant.MOBILE);
                break;
            default:
                break;
        }

        startActivityForResult(intent, Constant.REQUEST_FILTER_DATA_BY_DATE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case Constant.REQUEST_FILTER_DATA_BY_DATE:
                if (resultCode == RESULT_OK) {
                    mFilterIndex = data.getIntExtra("position",0);
                    mBeginDate = data.getStringExtra("begin_date");
                    mEndDate = data.getStringExtra("end_date");
                    mFilterType = data.getIntExtra("type",0);
                    mViewPager.setCurrentItem(mFilterIndex);
                }
                break;


            default:
                break;
        }
    }
}
