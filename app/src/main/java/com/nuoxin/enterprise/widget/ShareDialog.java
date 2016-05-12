package com.nuoxin.enterprise.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.ui.ShareChooseActivity;
import com.qihoo.share.framework.BaseShareAPI;
import com.qihoo.share.framework.ShareCallBackListener;
import com.qihoo.share.framework.ShareParam;
import com.qihoo.share.framework.ShareSdk;
import com.qihoo.share.util.ShareUtil;

import java.lang.ref.WeakReference;

/**
 */
public class ShareDialog extends Dialog implements View.OnClickListener {

    private ShareParam mParam;
    private ShareCallBackListener mListener;
    private WeakReference<Activity> mActivityRef;

    public ShareDialog(Activity context) {
        super(context, R.style.share_dialog_style);
        mActivityRef = new WeakReference<Activity>(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.share_dialog);

        initView();
    }

    private void initView() {
        findViewById(R.id.share_weixin).setOnClickListener(this);
        findViewById(R.id.share_email).setOnClickListener(this);
        findViewById(R.id.share_sms).setOnClickListener(this);
        findViewById(R.id.root).setOnClickListener(this);

        setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels); // 设置宽度
        lp.height = (int) (getContext().getResources().getDisplayMetrics().heightPixels);
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.share_email:
                // TODO
                Intent intent = new Intent(mActivityRef.get(), ShareChooseActivity.class);
                intent.putExtra("id", mId);
                intent.putExtra("shareType", ShareChooseActivity.TYPE_EMAIL);
                intent.putExtra("title", mParam.getTitle());
                intent.putExtra("url", mParam.getWebUrl());
                mActivityRef.get().startActivity(intent);
                dismiss();
                break;
            case R.id.share_weixin:
                ShareSdk.API_NAME name = ShareSdk.API_NAME.WXSession;
                BaseShareAPI api = ShareSdk.getShareAPI(name, getContext());
                if (api.isSurpport()) {
                    doShare(api);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), R.string.share_not_support, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.share_sms:
                // TODO
                intent = new Intent(mActivityRef.get(), ShareChooseActivity.class);
                intent.putExtra("id", mId);
                intent.putExtra("shareType", ShareChooseActivity.TYPE_SMS);
                intent.putExtra("title", mParam.getTitle());
                intent.putExtra("url", mParam.getWebUrl());
                mActivityRef.get().startActivity(intent);
                dismiss();
                break;
            default:
                dismiss();
                return;
        }


    }

    public void setParam(ShareParam p) {
        mParam = p;
    }

    private void doShare(BaseShareAPI api) {
        if (mParam.getThumbData() == null) {
            Bitmap b = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon);
            mParam.setThumbData(ShareUtil.bmpToByteArray(b, true, true));
        }

        if (mParam.getImageData() == null) {
            Bitmap b = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon);
            mParam.setImageData(ShareUtil.bmpToByteArray(b, true, true));
        }

        api.setCallBackListener(mListener);
        api.share(mParam, mActivityRef.get());
    }

    public void setShareCallBackListener(ShareCallBackListener l) {
        mListener = l;
    }

    public static ShareParam buildParam(String title, String webUrl, String description, String imgUrl) {
        ShareParam param = new ShareParam();
        param.setMessageType(ShareParam.MSG_TYPE_WEBPAGE);
        param.setTitle(title);
        param.setWebUrl(webUrl);
        param.setDescription(description);
        param.setImageUrl(imgUrl);
        param.setText(description + " " + webUrl);

        return param;
    }

    private int mId;

    public void setId(int id) {
        mId = id;
    }
}
