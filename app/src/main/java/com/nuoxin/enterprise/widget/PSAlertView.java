package com.nuoxin.enterprise.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nuoxin.enterprise.R;


public class PSAlertView {

	public static Dialog showAlertView(Context mContext, CharSequence title,
									   CharSequence message, String positiveButton,
			final OnAlertViewClickListener positiveListener,
			String[] otherButtons,
			final OnAlertViewClickListener[] otherButtonListeners) {
		final Dialog dialog = new Dialog(mContext, R.style.alertView);

		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view =  inflater.inflate(R.layout.alertview, null);

		TextView titleTextView = (TextView) view
				.findViewById(R.id.titleTextView);
		TextView messageTextView = (TextView) view
				.findViewById(R.id.messageTextView);
		if (TextUtils.isEmpty(title)) {
			titleTextView.setVisibility(View.GONE);
		} else {
			titleTextView.setText(title);
		}
		if (TextUtils.isEmpty(message)) {
			messageTextView.setVisibility(View.GONE);
		} else {
			messageTextView.setText(message);
		}
		Button pButton = new Button(mContext);
		pButton.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, dip2px(mContext, 56), 1.0f));
		pButton.setTextColor(Color.argb(255, 43, 191, 171));

		LinearLayout buttonLayout = (LinearLayout) view
				.findViewById(R.id.buttonLayout);

		if (null == otherButtons || otherButtons.length == 0) {
			// һ����ť
			buttonLayout.setOrientation(LinearLayout.VERTICAL);

			pButton.setBackgroundResource(R.drawable.alert_bottom_button);

		} else if (null != otherButtons && otherButtons.length == 1) {
			buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
			pButton.setBackgroundResource(R.drawable.alert_left_button);
		} else {
			buttonLayout.setOrientation(LinearLayout.VERTICAL);
			pButton.setBackgroundResource(R.drawable.alert_middle_button);
		}

		pButton.setText(positiveButton);
		pButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (null != positiveListener) {
					positiveListener.OnAlertViewClick();
				}
				dialog.dismiss();
			}

		});

		buttonLayout.addView(pButton);
		if (null != otherButtons && otherButtons.length > 0) {
			for (int i = 0; i < otherButtons.length; i++) {
				final int index = i;
				Button otherButton = new Button(mContext);
				otherButton.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, dip2px(mContext, 56), 1.0f));
				otherButton.setText(otherButtons[i]);
				otherButton.setTextColor(Color.argb(255, 69, 69, 69));
				if (1 == otherButtons.length) {
					otherButton
							.setBackgroundResource(R.drawable.alert_right_button);
				} else if (i < (otherButtons.length - 1)) {
					otherButton
							.setBackgroundResource(R.drawable.alert_middle_button);
				} else {
					otherButton
							.setBackgroundResource(R.drawable.alert_bottom_button);
				}
				otherButton.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						if (null != otherButtonListeners
								&& null != otherButtonListeners[index]) {
							otherButtonListeners[index].OnAlertViewClick();

						}
						dialog.dismiss();
					}
				});
				buttonLayout.addView(otherButton);
			}
		}

		final int viewWidth = dip2px(mContext, 300);
		view.setMinimumWidth(viewWidth);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(view);
		dialog.show();
		return dialog;
	}

	public interface OnAlertViewClickListener {
		void OnAlertViewClick();
	}

	private static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
