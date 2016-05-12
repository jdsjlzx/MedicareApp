package com.nuoxin.enterprise.ui;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nuoxin.enterprise.MainActivity;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseActivity;
import com.nuoxin.enterprise.util.AppToast;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.NetUtil;
import com.nuoxin.enterprise.util.Util;
import com.nuoxin.enterprise.widget.PSAlertView;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.OnClick;

public class ForgetPwdActivity extends BaseActivity {
    private static final String TAG = "ForgetPwdActivity";
    @Bind(R.id.mail_editor)
    TextView mEmailText;
    @Bind(R.id.next)
    Button mRecoverPwdBtn;

    @Bind(R.id.total_ll)
    LinearLayout  mTotal_ll;
    @Bind(R.id.success_ll)
    LinearLayout mSuccess_ll;

    private String mEmail = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initView() {
        mBackBtn.setVisibility(View.VISIBLE);
        mTitleText.setText(R.string.recover_passwords);
    }

    @Override
    public void initData() {
        mEmailText.setText("573842281@qq.com");
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            handleFindPasswordSuccess();
        }


        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }
    };

    private void handleFindPasswordSuccess() {

        mSuccess_ll.setVisibility(View.VISIBLE);
        mTotal_ll.setVisibility(View.GONE);
    }


    private boolean prepareForFindPassWord() {
        mEmail = mEmailText.getText().toString();
        if (!NetUtil.isNetConnected(this)) {
            AppToast.showShortText(ForgetPwdActivity.this,"没有网络");
            return true;
        }
        if (mEmail.length() == 0) {
            AppToast.showShortText(ForgetPwdActivity.this,"您还没有输入邮箱");
            return true;
        }
        if (!Util.checkEmail(mEmail)) {
            AppToast.showShortText(ForgetPwdActivity.this,"邮箱格式错误，请核对！");
            return true;
        }
        return false;
    }

    @OnClick(R.id.next)
    void findPassword(){
        if (prepareForFindPassWord()) {
            return;
        }
        final String content = getText(R.string.mail_tip) + "  " + mEmail;
        SpannableString spanText = new SpannableString(content);
        spanText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_color)), getText(R.string.mail_tip).toString().length() + 2, spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        PSAlertView.showAlertView(ForgetPwdActivity.this,"",
                spanText, "确认发送", new PSAlertView.OnAlertViewClickListener(
                ) {
                    @Override
                    public void OnAlertViewClick() {

                        NuoXinApi.recoverPasswords(mEmail, mHandler);
                    }
                }, new String[]{"取消"}, null)
                .show();

    }

}
