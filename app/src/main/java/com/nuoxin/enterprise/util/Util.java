package com.nuoxin.enterprise.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	private static final String TAG = "Util";


	private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();   
        if ( time - lastClickTime < 800) {
            return true;   
        }   
        lastClickTime = time;   
        return false;   
    }

	public static void keepListViewHeight(ListView listView) {
		//获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);  //计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		//listView.getDividerHeight()获取子项间分隔符占用的高度
		//params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);

	}

	/**
	 * 过滤特殊字符
	 */
	public static boolean CheckString(String input){
		boolean flag = false;
		try {
			String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern regex = Pattern.compile(regEx);
			Matcher matcher = regex.matcher(input);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * dip，dp转化成px 用来处理不同分辨路的屏幕
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue){
		float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dpValue*scale +0.5f);
	}

	/**
	 * 获得屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context){
		WindowManager windowManager = ((Activity)context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size.x;//屏幕寬度
	}

	public static boolean isLowEndDevice(Context context){
		return getScreenWidth(context) < 600;
	}

	/**
	 * 加载图片
	 * @param context
	 * @param resId
     * @return
     */
	public static Bitmap readBitMap(Context context,int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email){
		boolean flag = false;
		try{
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}

	public static String getMeetingDuration(String beginTime,String endTime){
		String duration = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date begein = sdf.parse(beginTime);
			java.util.Date end = sdf.parse(endTime);
			long l = end.getTime() - begein.getTime();
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			duration = hour+"小时"+min+"分钟";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return duration;
	}

	public static int compareDate(String beginDate,String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date begin = sdf.parse(beginDate);
			Date end = sdf.parse(endDate);
			if(end.getTime() > begin.getTime()){
				return 1;
			}else if(end.getTime() < begin.getTime()){
				return -1;
			}else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 除法运算
	 * @param dividend
	 * @param divider
     * @return
     */
	public static int div(int dividend,int divider){
		if(divider == 0){
			return 0;
		}else {
			return dividend/divider;
		}
	}

}
