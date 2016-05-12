package com.nuoxin.enterprise.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.HeaderSpanSizeLookup;
import com.cundong.recyclerview.MarginDecoration;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.DoctorAdapter;
import com.nuoxin.enterprise.api.NuoXinApi;
import com.nuoxin.enterprise.base.BaseActivity;
import com.nuoxin.enterprise.base.BaseListActivity;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.Doctor;
import com.nuoxin.enterprise.ui.error.ErrorLayout;
import com.nuoxin.enterprise.util.AppToast;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.JsonUtil;
import com.nuoxin.enterprise.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SearchActivity extends BaseListActivity implements View.OnKeyListener{

	private static final String TAG = "SearchActivity";
	@Bind(R.id.back_btn)
	protected ImageView mBackBtn;
	@Bind(R.id.clear_btn) ImageView mClearBtn;
	@Bind(R.id.btn_search) Button mSearchBtn;
	@Bind(R.id.search_edit) EditText mKeyEditText;

	InputMethodManager imm;

	@Override
	public void initView() {
		super.initView();

		mSearchTitleBar.setVisibility(View.VISIBLE);

		mBackBtn.setVisibility(View.VISIBLE);

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
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@Override
	protected boolean requestDataIfViewCreated() {
		return false;
	}

	@Override
	protected ListBaseAdapter getListAdapter() {
		return new DoctorAdapter();
	}

	@Override
	protected void sendRequestData() {
		NuoXinApi.getDoctorList(mCurrentPage, mKeyEditText.getText().toString().trim(), mResponseHandler);
	}

	@Override
	protected List<Doctor> parseList(byte[] data) {
		return JsonUtil.AnalyzeDocotorList(data);
	}

	@Override
	protected void initLayoutManager() {
		int margin = getResources().getDimensionPixelSize(R.dimen.doctoror_item_margin);
		MarginDecoration itemDecoration = new MarginDecoration(mContext,margin);
		mRecyclerView.addItemDecoration(itemDecoration);

		mRecyclerView.setPadding(0,16,0,0);

		GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
		layoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), layoutManager.getSpanCount()));
		layoutManager.setSmoothScrollbarEnabled(true);
		mRecyclerView.setLayoutManager(layoutManager);
	}

	@OnClick(R.id.btn_search)
	void doSearch() {
		if (TextUtils.isEmpty(mKeyEditText.getText().toString())) {
			AppToast.showShortText(SearchActivity.this,"请输入搜索内容");
			return;
		}
		if (Util.CheckString(mKeyEditText.getText().toString())) {
			AppToast.showShortText(SearchActivity.this,"搜索内容中含有非法字符");
			return;
		}
		imm.hideSoftInputFromWindow(mKeyEditText.getWindowToken(), 0);

		if (mErrorLayout != null) {
			mErrorLayout.setErrorType(ErrorLayout.NETWORK_LOADING);
			requestData();
		}
		requestData();
	}

	@OnClick(R.id.clear_btn)
	void clearSearchKey() {
		mKeyEditText.setText("");
	}


	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER
				&& event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
			// 先隐藏键盘
			String text = mKeyEditText.getText().toString().trim();
			if (TextUtils.isEmpty(text)) {
				AppToast.showShortText(SearchActivity.this, "请输入搜索内容");
				return false;
			}

			if (Util.CheckString(text)) {
				AppToast.showShortText(SearchActivity.this, "搜索内容中含有非法字符");
				return false;
			}
			imm.hideSoftInputFromWindow(mKeyEditText.getWindowToken(), 0);
			doSearch();
		}
		return false;
	}

	@OnClick(R.id.back_btn)
	void exit() {
		onBackPressed();
	}
}
