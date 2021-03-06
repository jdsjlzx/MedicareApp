package com.nuoxin.enterprise.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.HeaderSpanSizeLookup;
import com.cundong.recyclerview.MarginDecoration;
import com.nuoxin.enterprise.MainActivity;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.DoctorAdapter;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseListFragment;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.Doctor;
import com.nuoxin.enterprise.ui.SearchActivity;
import com.nuoxin.enterprise.ui.error.ErrorLayout;
import com.nuoxin.enterprise.util.AppToast;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class DoctorsFragment extends BaseListFragment implements View.OnKeyListener{

    ImageView mClearBtn;
    Button mSearchBtn;
    EditText mKeyEditText;

    private InputMethodManager imm;
    @Override
    public void initView(View view) {
        super.initView(view);

        mDynamicLayout.setVisibility(View.VISIBLE);
        View searchView = mInflater.inflate(R.layout.layout_search_view,null);
        mDynamicLayout.addView(searchView);
        mDynamicLayout.setOnClickListener(this);

        mSearchBtn = (Button) searchView.findViewById(R.id.btn_search);
        mClearBtn = (ImageView) searchView.findViewById(R.id.clear_btn);
        mKeyEditText = (EditText) searchView.findViewById(R.id.search_edit);

        mClearBtn.setOnClickListener(this);
        mSearchBtn.setOnClickListener(this);

        mKeyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mClearBtn.setVisibility(s.toString().length() == 0 ? View.GONE : View.VISIBLE);
                mKeyEditText.setSelection(s.toString().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



    @Override
    public void initData() {
        super.initData();
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new DoctorAdapter();
    }

    @Override
    protected void sendRequestData() {
        if(mKeyEditText == null){
            NuoXinApi.getDoctorList(mCurrentPage, null, mResponseHandler);
        }else {
            NuoXinApi.getDoctorList(mCurrentPage, mKeyEditText.getText().toString().trim(), mResponseHandler);
        }

    }

    @Override
    protected List<Doctor> parseList(byte[] data) {
        return JsonUtil.AnalyzeDocotorList(data);
    }

    @Override
    protected void initLayoutManager() {
        int margin = getActivity().getResources().getDimensionPixelSize(R.dimen.doctoror_item_margin);
        MarginDecoration itemDecoration = new MarginDecoration(getActivity(),margin);
        mRecyclerView.addItemDecoration(itemDecoration);
        int space = getResources().getDimensionPixelSize(R.dimen.home_item_margin);
        //mRecyclerView.setPadding(space,0,space,space);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), layoutManager.getSpanCount()));
        layoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        switch (id) {
            case R.id.clear_btn:
                mKeyEditText.setText("");
                break;
            case R.id.btn_search:
                doSearch();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER
                && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
            // 先隐藏键盘
            String text = mKeyEditText.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                AppToast.showShortText(getActivity(), "请输入搜索内容");
                return false;
            }

            if (Util.CheckString(text)) {
                AppToast.showShortText(getActivity(), "搜索内容中含有非法字符");
                return false;
            }
            imm.hideSoftInputFromWindow(mKeyEditText.getWindowToken(), 0);
            doSearch();
        }
        return false;
    }

    void doSearch() {
        if (TextUtils.isEmpty(mKeyEditText.getText().toString())) {
            AppToast.showShortText(getActivity(), "请输入搜索内容");
            return;
        }
        if (Util.CheckString(mKeyEditText.getText().toString())) {
            AppToast.showShortText(getActivity(), "搜索内容中含有非法字符");
            return;
        }
        imm.hideSoftInputFromWindow(mKeyEditText.getWindowToken(), 0);

        mCurrentPage = 0;
        requestData();
    }
}
