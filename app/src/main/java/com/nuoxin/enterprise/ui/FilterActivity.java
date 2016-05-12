package com.nuoxin.enterprise.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.base.BaseActivity;
import com.nuoxin.enterprise.util.AppToast;
import com.nuoxin.enterprise.util.TLog;
import com.nuoxin.enterprise.util.Util;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.w3c.dom.Text;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;

public class FilterActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener{

	private static final String TAG = "FilterActivity";

	@Bind(R.id.type_radioGroup)
	RadioGroup mTypeRadioGroup;
	@Bind(R.id.next) Button mNextBtn;

	@Bind(R.id.start_date_editor)
	EditText mStartEditor;
	@Bind(R.id.expiry_date_editor)
	EditText mEndEditor;

	public enum State {
		START, END
	}

	private State mCurState;
	@Override
	protected int getLayoutId() {
		return R.layout.activity_search_filter;
	}

	@Override
	public void initView() {
		mBackBtn.setVisibility(View.VISIBLE);
		mTitleText.setText(R.string.filter);


		mTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
				RadioButton radioButton = (RadioButton) findViewById(checkedId);
				TLog.log("lzx", "radioButton.getText() = " + radioButton.getText().toString());
			}
		});

		mTypeRadioGroup.check(R.id.course_radio_btn);
	}

	@Override
	public void initData() {

	}

	@OnClick(R.id.next)
	void doAnalysis() {
		String endDate = mEndEditor.getText().toString();
		String startDate = mStartEditor.getText().toString();

		//if (TextUtils.isEmpty(startDate)) {
			//AppToast.showShortText(FilterActivity.this,"请输入开始时间");
		//	startDate = endDate;
		//}
		//if (TextUtils.isEmpty(endDate)) {
			//AppToast.showShortText(FilterActivity.this,"请输入截止时间");
		//	endDate = startDate;
		//}
		if(!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate) && Util.compareDate(startDate,endDate) < 0){
			AppToast.showShortText(FilterActivity.this,"截止时间必须大于开始时间");
			return;
		}
		Intent data = new Intent();
		data.putExtra("begin_date", startDate);
		data.putExtra("end_date", endDate);
		data.putExtra("position",getIntent().getIntExtra("position",0));
		data.putExtra("type",getIntent().getIntExtra("type",0));
		setResult(RESULT_OK, data);
		finish();
	}


	@Override
	@OnClick({R.id.start_date_editor,R.id.expiry_date_editor})
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()){
			case R.id.start_date_editor:
				mCurState = State.START;
				break;
			case R.id.expiry_date_editor:
				mCurState = State.END;
				break;
			default:
				break;
		}
		showDatePicker();
	}

	private void showDatePicker(){
		Calendar now = Calendar.getInstance();
		DatePickerDialog dpd = DatePickerDialog.newInstance(
				FilterActivity.this,
				now.get(Calendar.YEAR),
				now.get(Calendar.MONTH),
				now.get(Calendar.DAY_OF_MONTH)
		);
		dpd.show(getFragmentManager(), "");
	}

	@Override
	public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
		String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
		switch (mCurState) {
			case START:
				mStartEditor.setText(date);
				break;
			case END:
				mEndEditor.setText(date);
				break;
			default:
				break;
		}
	}
}
