package com.nuoxin.enterprise.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nuoxin.enterprise.R;
import com.nuoxin.enterprise.adapter.viewholder.NewsViewHolder;
import com.nuoxin.enterprise.base.ListBaseAdapter;
import com.nuoxin.enterprise.bean.Meeting;
import com.nuoxin.enterprise.ui.AppStaticActivity;
import com.nuoxin.enterprise.ui.FirstActivity;
import com.nuoxin.enterprise.ui.FragmentDetailsActivity;
import com.nuoxin.enterprise.ui.PhoneStaticsActivity;
import com.nuoxin.enterprise.ui.ThirdActivity;
import com.nuoxin.enterprise.ui.WebStaticsActivity;
import com.nuoxin.enterprise.util.Constant;
import com.nuoxin.enterprise.util.IntentUtil;
import com.nuoxin.enterprise.util.TLog;

public class MeetingsListAdapter extends ListBaseAdapter<Meeting> {

    private Context mContext;
    private int mType = Constant.WEIXIN;
    private int mDoctorId = Constant.WEIXIN;

    public MeetingsListAdapter(int type,int doctorId) {
        this.mType = type;
        this.mDoctorId = doctorId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewsViewHolder viewHolder = (NewsViewHolder) holder;
        final Meeting meeting = this.mDataList.get(position);

        switch (mType){
            case Constant.WEIXIN:
                viewHolder.titleText.setText(meeting.title);
                viewHolder.timeText.setText(meeting.startTime);
                viewHolder.speakerText.setText(meeting.speaker);
                break;
            case Constant.APP:
            case Constant.WEB:
                viewHolder.timeText.setText(meeting.publishTime);

                SpannableString msp = new SpannableString("12" + meeting.title);
                Drawable drawable = null;
                if (meeting.projectType == Constant.PROJECT_TYPE_COURSE) {
                    drawable = mContext.getResources().getDrawable(R.drawable.ic_circle_course);

                    viewHolder.speakerText.setText(meeting.speaker + " " + meeting.hospitalName);
                } else if (meeting.projectType == Constant.PROJECT_TYPE_ARTICLE) {
                    drawable = mContext.getResources().getDrawable(R.drawable.ic_circle_article);

                    viewHolder.speakerText.setText(meeting.speaker + " " + meeting.hospitalName);
                } else if (meeting.projectType == Constant.PROJECT_TYPE_CONTENT) {
                    drawable = mContext.getResources().getDrawable(R.drawable.ic_circle_content);

                    viewHolder.speakerText.setText(meeting.speaker);
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth() + 5, drawable.getIntrinsicHeight() + 5);
                msp.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.titleText.setText(msp);
                break;
            case Constant.MOBILE:
                viewHolder.titleText.setText(meeting.title);
                viewHolder.speakerText.setText(meeting.speaker);
                break;
            default:
                break;
        }

        Glide.with(mContext)
                .load(meeting.picUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_image_default)
                .crossFade()
                .into(viewHolder.displayImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                TLog.log("MeetingsListAdapter"," mType = " + mType + " mDoctorId = " + mDoctorId);
                switch (mType){
                    case Constant.WEIXIN:
                        if (-1 == mDoctorId) {//从首页点击点击进入
                            bundle.putInt("productId",meeting.productId);
                            bundle.putInt("meetingId",meeting.meetingId);
                            IntentUtil.startActivity((Activity) mContext, FirstActivity.class, bundle);
                        }else {//从医生参与的项目点击进入
                            bundle.putInt("doctorId",mDoctorId);
                            bundle.putSerializable("meeting",meeting);
                            bundle.putInt(Constant.FRAGMENT_DETAILS,Constant.FRAGMENT_DOCTOR_MEETING_DETAIL);
                            IntentUtil.startActivity((Activity) mContext, FragmentDetailsActivity.class, bundle);
                        }

                        break;
                    case Constant.APP:
                        if (-1 == mDoctorId) {
                            bundle.putInt("dataId",meeting.dataId);
                            bundle.putInt("projectType",meeting.projectType);
                            bundle.putInt("productId",meeting.productId);
                            bundle.putString("meetingTitle", meeting.title);
                            bundle.putString("meetingPublishtime",meeting.publishTime);
                            bundle.putInt("from", Constant.APP);
                            switch (meeting.projectType) {
                                case Constant.PROJECT_TYPE_COURSE:
                                    bundle.putInt("atype", 1);
                                    IntentUtil.startActivity((Activity) mContext, ThirdActivity.class, bundle);
                                    break;
                                case Constant.PROJECT_TYPE_ARTICLE:
                                    bundle.putInt("atype", 2);
                                    IntentUtil.startActivity((Activity) mContext, AppStaticActivity.class, bundle);
                                    break;
                                case Constant.PROJECT_TYPE_CONTENT:
                                    bundle.putInt("atype", 3);
                                    IntentUtil.startActivity((Activity) mContext, AppStaticActivity.class, bundle);
                                    break;
                                default:
                                    break;
                            }


                        } else {
                            bundle.putInt("doctorId",mDoctorId);
                            bundle.putSerializable("meeting",meeting);
                            bundle.putInt(Constant.FRAGMENT_DETAILS,Constant.FRAGMENT_DOCTOR_MEETING_DETAIL);
                            IntentUtil.startActivity((Activity) mContext, FragmentDetailsActivity.class, bundle);
                        }
                        break;
                    case Constant.WEB:
                        if (-1 == mDoctorId) {
                            bundle.putInt("dataId",meeting.dataId);
                            bundle.putInt("projectType",meeting.projectType);
                            bundle.putInt("productId",meeting.productId);
                            bundle.putString("meetingTitle", meeting.title);
                            bundle.putString("meetingPublishtime",meeting.publishTime);
                            bundle.putInt("from", Constant.WEB);
                            bundle.putInt("id",meeting.id);
                            switch (meeting.projectType) {
                                case Constant.PROJECT_TYPE_COURSE:
                                    bundle.putInt("atype", 1);
                                    break;
                                case Constant.PROJECT_TYPE_ARTICLE:
                                    bundle.putInt("atype", 2);
                                case Constant.PROJECT_TYPE_CONTENT:
                                    bundle.putInt("atype", 3);
                                    break;
                                default:
                                    break;
                            }
                            IntentUtil.startActivity((Activity) mContext,WebStaticsActivity.class, bundle);

                        } else {
                            bundle.putInt("doctorId",mDoctorId);
                            bundle.putSerializable("meeting",meeting);
                            bundle.putInt(Constant.FRAGMENT_DETAILS,Constant.FRAGMENT_DOCTOR_MEETING_DETAIL);
                            IntentUtil.startActivity((Activity) mContext, FragmentDetailsActivity.class, bundle);
                        }

                        break;
                    case Constant.MOBILE:

                        if (-1 == mDoctorId) {
                            bundle.putInt("meetingId",meeting.meetingId);
                            IntentUtil.startActivity((Activity) mContext,PhoneStaticsActivity.class, bundle);
                        } else {
                            bundle.putInt("doctorId",mDoctorId);
                            bundle.putSerializable("meeting",meeting);
                            bundle.putInt(Constant.FRAGMENT_DETAILS,Constant.FRAGMENT_DOCTOR_MEETING_DETAIL);
                            IntentUtil.startActivity((Activity) mContext, FragmentDetailsActivity.class, bundle);
                        }
                        break;
                    default:
                        break;
                }
            }
        });


    }
}
