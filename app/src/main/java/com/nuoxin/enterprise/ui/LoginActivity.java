package com.nuoxin.enterprise.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nuoxin.enterprise.AppContext;
import com.nuoxin.enterprise.MainActivity;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseAppCompatActivity;
import com.nuoxin.enterprise.bean.User;
import com.nuoxin.enterprise.util.AppToast;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.DialogHelper;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.NetUtil;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.util.Util;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseAppCompatActivity {

    private String TAG = "LoginActivity";
    @Bind(R.id.forgot_psw)
    TextView mForgetPwdText;
    @Bind(R.id.login)
    Button mLoginBtn;

    @Bind(R.id.account_editor)
    EditText accountText;
    @Bind(R.id.psw_editor)
    EditText passwordText;

    private String mUserName = "";
    private String mPassword = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    public void initView() {
        //accountText.setText("liyanhong@163.com");
        //passwordText.setText("101SD080");
    }

    @Override
    public void initData() {

    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onStart() {
            super.onStart();
            if(!isFinishing()){
                DialogHelper.showDialogForLoading(LoginActivity.this, "正在登录，请您耐心等待...", false);
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
            TLog.log(TAG,"onSuccess : " +new String(bytes));

            DialogHelper.stopProgressDlg();
            String json = new String(bytes);

            if (200 == statusCode) {
                if (JsonUtil.getStateFromServer(json).equals(Constant.SUCCESS)){
                    String userToken = JsonUtil.getUserToken(json);
                    AppContext.getInstance().setToken(userToken);
                    String email = accountText.getText().toString();
                    String userPass = passwordText.getText().toString();
                    User user = JsonUtil.getUserInfo(email,userPass,json);
                    AppContext.getInstance().saveUserInfo(user);
                    handleLoginSuccess();
                }else{
                    AppToast.showShortText(LoginActivity.this, "用户名或密码错误");
                }
            }


        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            DialogHelper.stopProgressDlg();
            AppToast.showShortText(LoginActivity.this, "登录失败，请稍后重试");
        }

    };

    private void handleLoginSuccess() {
        IntentUtil.startActivity(LoginActivity.this, MainActivity.class);
        finish();
    }


    private boolean prepareForLogin() {
        if (!NetUtil.isNetConnected(this)) {
            AppToast.showShortText(LoginActivity.this,"没有网络");
            return true;
        }
        if (accountText.getText().length() == 0) {
            AppToast.showShortText(LoginActivity.this,"邮箱不能为空!");
            accountText.requestFocus();
            return true;
        }
        if (!Util.checkEmail(accountText.getText().toString())) {
            AppToast.showShortText(LoginActivity.this,"邮箱含有非法字符，请核对！");
            return true;
        }
        if (passwordText.getText().length() == 0) {
            AppToast.showShortText(LoginActivity.this,"密码不能为空");
            passwordText.requestFocus();
            return true;
        }

        if (passwordText.getText().toString().length() < 4) {
            AppToast.showShortText(LoginActivity.this,"密码过短，请重新输入!");
            passwordText.requestFocus();
            return true;
        }


        return false;
    }

    private void handleLogin() {

        if (prepareForLogin()) {
            return;
        }

        // if the data has ready
        mUserName = accountText.getText().toString();
        mPassword = passwordText.getText().toString();

        NuoXinApi.login(mUserName, mPassword, mHandler);
    }

    @Override
    @OnClick({R.id.login,R.id.forgot_psw})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login:
                handleLogin();
                break;
            case R.id.forgot_psw:
                IntentUtil.startActivity(LoginActivity.this, ForgetPwdActivity.class);
                break;
            default:
                break;
        }
    }


}
