package com.nuoxin.enterprise;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.nuoxin.enterprise.adapter.MyFragmentPagerAdapter;
import com.nuoxin.enterprise.base.BaseAppCompatActivity;
import com.nuoxin.enterprise.event.UserChangedEvent;
import com.nuoxin.enterprise.fragment.DoctorsFragment;
import com.nuoxin.enterprise.fragment.HomeFragment;
import com.nuoxin.enterprise.fragment.MainContentFragment;
import com.nuoxin.enterprise.fragment.UserCenterFragment;
import com.nuoxin.enterprise.ui.LoginActivity;
import com.nuoxin.enterprise.util.AppToast;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.widget.GradientIconView;
import com.nuoxin.enterprise.widget.GradientTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends BaseAppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    public static ViewPager mViewPager ;
    @Bind(R.id.title_bar_view)
    View mTtitlebarView;
    @Bind(R.id.tab_one_layout)
    View mOneTabView;
    @Bind(R.id.tab_content_layout)
    View mContentTabView;
    @Bind(R.id.tab_two_layout)
    View mTwoTabView;
    @Bind(R.id.tab_three_layout)
    View mThreeTabView;

    private CharSequence[] mLabels;

    @Bind({R.id.menu_home_icon, R.id.menu_content_icon, R.id.menu_doctor_icon, R.id.menu_me_icon})
    List<GradientIconView> mTabIconIndicator;
    @Bind({R.id.menu_home_text, R.id.menu_content_text, R.id.menu_doctor_text, R.id.menu_me_text})
    List<GradientTextView> mTabTextIndicator;

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    {
        mFragments.add(new HomeFragment());
        mFragments.add(new MainContentFragment());
        mFragments.add(new DoctorsFragment());
        mFragments.add(new UserCenterFragment());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initTabIndicator();
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void initData() {
        mLabels = getResources().getTextArray(R.array.title_bar_labels);
        mTitleText.setText(mLabels[0]);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mLabels));
    }

    private void initTabIndicator() {
        mTabIconIndicator.get(0).setIconAlpha(1.0f);
        mTabTextIndicator.get(0).setTextViewAlpha(1.0f);

        mOneTabView.setOnClickListener(this);
        mContentTabView.setOnClickListener(this);
        mTwoTabView.setOnClickListener(this);
        mContentTabView.setOnClickListener(this);
        mThreeTabView.setOnClickListener(this);

    }

    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs() {
        resetOtherTabIcons();
        resetOtherTabText();
    }

    /**
     * 重置其他的Tab text
     */
    private void resetOtherTabText() {
        for (int i = 0; i < mTabTextIndicator.size(); i++) {
            mTabTextIndicator.get(i).setTextViewAlpha(0);
        }
    }

    /**
     * 重置其他的Tab icon
     */
    private void resetOtherTabIcons() {
        for (int i = 0; i < mTabIconIndicator.size(); i++) {
            mTabIconIndicator.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            GradientIconView iconLeft = mTabIconIndicator.get(position);
            GradientIconView iconRight = mTabIconIndicator.get(position + 1);

            GradientTextView textLeft = mTabTextIndicator.get(position);
            GradientTextView textRight = mTabTextIndicator.get(position + 1);

            iconLeft.setIconAlpha(1 - positionOffset);
            textLeft.setTextViewAlpha(1 - positionOffset);
            iconRight.setIconAlpha(positionOffset);
            textRight.setTextViewAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mTitleText.setText(mLabels[position]);

        if (position == mLabels.length - 1) {
            mTtitlebarView.setVisibility(View.GONE);
        } else {
            mTtitlebarView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        resetOtherTabs();

        switch (v.getId()) {
            case R.id.tab_one_layout:
                mTabIconIndicator.get(0).setIconAlpha(1.0f);
                mTabTextIndicator.get(0).setTextViewAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.tab_content_layout:
                mTabIconIndicator.get(1).setIconAlpha(1.0f);
                mTabTextIndicator.get(1).setTextViewAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.tab_two_layout:
                mTabIconIndicator.get(2).setIconAlpha(1.0f);
                mTabTextIndicator.get(2).setTextViewAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.tab_three_layout:
                mTabIconIndicator.get(3).setIconAlpha(1.0f);
                mTabTextIndicator.get(3).setTextViewAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
            default:
                break;
        }
    }

    private static Boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                AppToast.showShortText(this, "再按一次退出程序");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            } else {
                AppContext.finishAll();
            }
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onUserChangedEvent(UserChangedEvent event) {
        if (!AppContext.getInstance().isLogin()) {
            IntentUtil.startActivity(this, LoginActivity.class);
            MainActivity.this.finish();
        }
    }

}
