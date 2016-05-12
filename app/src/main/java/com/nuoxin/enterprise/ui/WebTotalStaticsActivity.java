package com.nuoxin.enterprise.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.MyFragmentPagerAdapter;
import com.nuoxin.enterprise.base.BaseActivity;
import com.nuoxin.enterprise.bean.AppWebCounts;
import com.nuoxin.enterprise.fragment.ArticleFragment;
import com.nuoxin.enterprise.fragment.ContentFragment;
import com.nuoxin.enterprise.fragment.CourseFragment;

import java.util.ArrayList;

import butterknife.Bind;


public class WebTotalStaticsActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private String TAG = "WebStaticsActivity";
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    private CharSequence[] mLabels;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private AppWebCounts mAppWebCoursesCounts;

    private void fillData() {
        mFragments.add(CourseFragment.newInstance(getIntent().getIntExtra("type", 1), getIntent().getIntExtra("productId", -1), true));
        mFragments.add(ArticleFragment.newInstance(getIntent().getIntExtra("type", 1), getIntent().getIntExtra("productId", -1), true));
        mFragments.add(ContentFragment.newInstance(getIntent().getIntExtra("type", 1), getIntent().getIntExtra("productId", -1), true));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_webtotalstatics;
    }

    @Override
    public void initView() {
        mTitleText.setText("汇总统计");
        mBackBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        mLabels = getResources().getTextArray(R.array.totalstatics);
        mAppWebCoursesCounts = (AppWebCounts) getIntent().getSerializableExtra("course_count");
        for (int i = 0; i < mLabels.length; i++) {
            switch (i) {
                case 0:
                    mLabels[i] = mLabels[i] + "(" + mAppWebCoursesCounts.courseCounts + ")";
                    break;
                case 1:
                    mLabels[i] = mLabels[i] + "(" + mAppWebCoursesCounts.informationCounts + ")";
                    break;
                case 2:
                    mLabels[i] = mLabels[i] + "(" + mAppWebCoursesCounts.contentCounts + ")";
                    break;
                default:
                    break;
            }

            mTabLayout.addTab(mTabLayout.newTab().setText(mLabels[i]));
        }
        fillData();
        MyFragmentPagerAdapter mFragmentAdapteradapter =
                new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mLabels);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mFragmentAdapteradapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
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
}