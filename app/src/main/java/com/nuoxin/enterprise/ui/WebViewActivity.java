package com.nuoxin.enterprise.ui;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.base.BaseActivity;
import com.nuoxin.enterprise.util.DialogHelper;
import com.nuoxin.enterprise.widget.ShareDialog;
import com.qihoo.share.framework.ShareCallBackListener;
import com.qihoo.share.framework.ShareResult;

import butterknife.Bind;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity implements ShareCallBackListener {

    private static final String TAG = "WebViewActivity";
    @Bind(R.id.webview)
    WebView mWebView;

    private ShareDialog mShareDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        mBackBtn.setVisibility(View.VISIBLE);
        mRightBtn.setVisibility(View.VISIBLE);
        mRightBtn.setImageResource(R.drawable.share_btn);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成h
                    DialogHelper.stopProgressDlg();
                } else {
                    // 加载中
                }
            }
        });
    }

    private String mUrl;
    private int mId;
    private String mTitle;
    private String mContent;

    @Override
    public void initData() {
        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
        mId = getIntent().getIntExtra("id", 0);
        mContent = getIntent().getStringExtra("content");

        mTitleText.setText(mTitle);

        DialogHelper.showDialogForLoading(this, "正在加载数据，请您耐心等待...", true);
        mWebView.loadUrl(mUrl);
    }

    @OnClick(R.id.right_img)
    void showShare() {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(this);
            mShareDialog.setShareCallBackListener(this);
            mShareDialog.setParam(ShareDialog.buildParam(mTitle, mUrl, mContent, ""));
            mShareDialog.setId(mId);
        }

        mShareDialog.show();
    }

    @Override
    public void callback(ShareResult shareResult) {
        if (shareResult.resultCode == ShareResult.CODE_SUCCESS) {
            Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
            // TODO

        } else {
            Toast.makeText(this, shareResult.resultMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
