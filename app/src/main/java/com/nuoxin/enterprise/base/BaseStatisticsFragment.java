package com.nuoxin.enterprise.base;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nuoxin.enterprise.AppContext;
import com.nuoxin.enterprise.util.AppToast;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.DialogHelper;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.TLog;

import org.apache.http.Header;

public abstract class BaseStatisticsFragment extends BaseFragment {

    protected AsyncHttpResponseHandler mResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onStart() {
            super.onStart();
            if(!getActivity().isFinishing()){
                DialogHelper.showDialogForLoading(getActivity(), "正在加载数据，请您耐心等待...", true);
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
            TLog.log("onSuccess : " +new String(bytes));

            String result = new String(bytes);

            if (200 == statusCode) {
                if (JsonUtil.getStateFromServer(result).equals(Constant.SUCCESS)){
                    handleData(result);
                }else{
                    AppToast.showShortText(getActivity(), "获取数据错误");
                }
            }


        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] responseBody,
                              Throwable arg3) {
            String result = new String(responseBody);
            TLog.log("onFailure " + result);
            if(result.contains("Mismatched access token") || result.contains("X-Access-Token")) {
                AppContext.getInstance().handleLogout();
                if(null != getActivity()){
                    getActivity().finish();
                }
            }else {
                AppToast.showShortText(getActivity(), "获取数据失败，请稍后重试");
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
            DialogHelper.stopProgressDlg();
        }
    };

    public abstract void handleData(String json);
}
