package com.nuoxin.enterprise.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuoxin.enterprise.AppContext;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.event.UserChangedEvent;
import com.nuoxin.enterprise.interf.BaseViewInterface;
import com.nuoxin.enterprise.ui.LoginActivity;
import com.nuoxin.enterprise.util.IntentUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public abstract class BaseAppCompatActivity extends AppCompatActivity implements View.OnClickListener,BaseViewInterface{
    @Bind(R.id.title_text)
    protected TextView mTitleText;
    @Bind(R.id.back_img)
    protected ImageView mBackBtn;
    @Bind(R.id.right_img)
    protected ImageView mRightBtn;

    protected LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        AppContext.addActivity(this);
        ButterKnife.bind(this);
        mInflater = getLayoutInflater();
        // Register
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        // Unregister
        EventBus.getDefault().unregister(this);
    }

    protected int getLayoutId() {
        return 0;
    }

    //不做实现，解决出现的异常（Subscriber ****has no public methods called ）
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onUserChangedEvent(UserChangedEvent event) {
    }
}
