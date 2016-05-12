package com.nuoxin.enterprise.fragment;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.enrique.stackblur.StackBlurManager;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nuoxin.enterprise.AppContext;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseFragment;
import com.nuoxin.enterprise.bean.User;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.widget.CircleImageView;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserCenterFragment extends BaseFragment {

    @Bind(R.id.ll_user_container) LinearLayout mUserContainer;

    @Bind(R.id.avatar_image)
    CircleImageView mAvatarImage;
    @Bind(R.id.right_text) TextView mLogoutText;
    @Bind(R.id.name_text) TextView mNameText;
    @Bind(R.id.employee_no_text) TextView mEmployeeNoText;
    @Bind(R.id.user_mobile_text) TextView mMobileText;
    @Bind(R.id.user_email_text) TextView mEmailText;
    @Bind(R.id.user_department_text) TextView mDeapartmentText;

    private User mUser;

    private static final int RENDER_IMAGE = 1000;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == RENDER_IMAGE){
                if(!TextUtils.isEmpty(mUser.avatarUrl) && !mUser.avatarUrl.equals("null")){
                    if (mUser.avatarUrl.startsWith("http")){

                        Glide.with(getActivity())
                                .load(mUser.avatarUrl)
                                .centerCrop()
                                .placeholder(R.mipmap.ic_user_avater)
                                .crossFade()
                                .into(mAvatarImage);

                        if(isAdded()){
                            Glide.with(getActivity()).load(mUser.avatarUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    if(resource != null) {
                                        StackBlurManager stackBlurManager = new StackBlurManager(resource);
                                        Bitmap bitmap = stackBlurManager.process(20);
                                        mUserContainer.setBackground(new BitmapDrawable(getResources(),bitmap));
                                    }
                                }
                            });
                            /*Bitmap bitmap = ImageLoader.getInstance().loadImageSync(mUser.avatarUrl,AppContext.getInstance().getDefaultOptions());
                            if(bitmap != null) {
                                StackBlurManager stackBlurManager = new StackBlurManager(bitmap);
                                bitmap = stackBlurManager.process(20);
                                mUserContainer.setBackground(new BitmapDrawable(getResources(),bitmap));
                            }*/

                        }

                    }
                }
            }
        }
    };

    private final AsyncHttpResponseHandler mActiveHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            String json = new String(arg2);
            TLog.log("onSuccess " + json);
            mUser = JsonUtil.getUserInfo(json);
            fillUI();
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] responseBody,
                              Throwable arg3) {
            if(null == responseBody){
                return;
            }
            String result = new String(responseBody);
            TLog.log("onFailure " + result);
            if(result.contains("Mismatched access token") || result.contains("X-Access-Token")) {
                AppContext.getInstance().handleLogout();
                if(null != getActivity()) {
                    getActivity().finish();
                }

            }

        }

        @Override
        public void onFinish() {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_center, container, false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        sendGetUserInfomation();
    }

    private void sendGetUserInfomation() {
        NuoXinApi.getUserInfomation(mActiveHandler);
    }

    private void fillUI() {
        mNameText.setText(mUser.name);
        mEmployeeNoText.setText(mUser.employeeNo);
        mMobileText.setText(mUser.mobile);
        mEmailText.setText(mUser.email);
        mDeapartmentText.setText(mUser.department);

        mHandler.sendEmptyMessage(RENDER_IMAGE);
    }

    @Override
    @OnClick({R.id.right_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_text:
                AppContext.getInstance().handleLogout();
                break;
            default:
                break;
        }
    }

}
