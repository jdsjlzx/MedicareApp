package com.nuoxin.enterprise.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuoxin.enterprise.AppContext;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.interf.BaseViewInterface;
import com.nuoxin.enterprise.util.AppToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public abstract class BaseActivity extends SwipeBackActivity implements View.OnClickListener,BaseViewInterface{
    @Bind(R.id.title_text)
    protected TextView mTitleText;
    @Bind(R.id.back_img)
    protected ImageView mBackBtn;
    @Bind(R.id.right_img)
    protected ImageView mRightBtn;
    protected LayoutInflater mInflater;
    protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }

        AppContext.addActivity(this);
        ButterKnife.bind(this);

        mInflater = getLayoutInflater();
        mContext = this;
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected int getLayoutId() {
        return 0;
    }

    @Override
    @OnClick({R.id.back_img})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_img:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
