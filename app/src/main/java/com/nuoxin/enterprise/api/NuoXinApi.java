package com.nuoxin.enterprise.api;

import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nuoxin.enterprise.bean.Meeting;
import com.nuoxin.enterprise.util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NuoXinApi {

    public static void login(String username, String password,
                             AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("email", username);
        params.put("password", password);
        ApiHttpClient.post("drugUser/login", params, handler);
    }

    public static void recoverPasswords(String username,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("email", username);
        ApiHttpClient.post("drugUser/searchPwd", params, handler);
    }

    public static void getCourseList(int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);
        ApiHttpClient.post("drug/productList", params, handler);
    }

    public static void getDoctorList(int page, String key, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);
        if (!TextUtils.isEmpty(key)) {
            params.put("param", key);
        }
        ApiHttpClient.post("hospital/doctorList", params, handler);

    }

    public static void getDoctorStatisticsList(int page, int projectType, int type, int productId, int aType, boolean isSummary, int meetingId, String key, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);
        params.put("productId", productId);
        params.put("type", type);
        params.put("articleType", aType);
        params.put("meetingId", meetingId);
        params.put("dataId", meetingId);
        if (!TextUtils.isEmpty(key)) {
            params.put("param", key);
        }
        switch (projectType) {
            case Constant.WEIXIN:
                ApiHttpClient.post(isSummary ? "projectWechat/summary/staffList" : "projectWechat/staffList", params, handler);
                break;
            case Constant.APP:
                ApiHttpClient.post(isSummary ? "projectApp/summary/staffList" : "projectApp/staffList", params, handler);
                break;
            case Constant.WEB:
                ApiHttpClient.post(isSummary ? "net/summary/staffList" : "net/staffList", params, handler);
                break;
            case Constant.MOBILE:
                ApiHttpClient.post(isSummary ? "projectPhone/summary/staffList" : "projectPhone/staffList", params, handler);
                break;
            case Constant.FOLLOWUP:
                ApiHttpClient.post("projectFollowUp/staffList", params, handler);
                break;
            default:
                break;
        }


    }

    public static void getWeChatMeetingsList(Integer doctorId, int productId, String beginDate, String endDate, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);
        if (-1 == doctorId) {
            if (!TextUtils.isEmpty(beginDate)) {
                params.put("startTime", beginDate);
            }
            if (!TextUtils.isEmpty(endDate)) {
                params.put("endTime", endDate);
            }
            ApiHttpClient.post("drug/projectWechartList", params, handler);
        } else {
            params.put("id", doctorId);
            ApiHttpClient.post("hospital/operateProjectMeetingList", params, handler);
        }

    }

    public static void getWeChatMeetingsCount(int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        ApiHttpClient.post("drug/projectWechart/counts", params, handler);
    }

    /**
     * 全部统计
     *
     * @param productId
     * @param handler
     */
    public static void getWeChatMeetingsStatistics(int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        ApiHttpClient.post("drug/projectWechart/report", params, handler);
    }

    /**
     * 单个微信会议统计-明细
     *
     * @param meetingId
     * @param handler
     */
    public static void getWeChatMeetingStatistics(int meetingId, AsyncHttpResponseHandler handler) {
        ApiHttpClient.get("drug/projectWechart/" + meetingId, handler);
    }

    public static void getAppMeetingList(Integer doctorId, int proId, String beginDate, String endDate, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", proId);
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);

        if (-1 == doctorId) {
            if (!TextUtils.isEmpty(beginDate)) {
                params.put("startTime", beginDate);
            }
            if (!TextUtils.isEmpty(endDate)) {
                params.put("endTime", endDate);
            }
            ApiHttpClient.post("drug/projectAppOrNetList", params, handler);
        } else {
            params.put("id", doctorId);
            ApiHttpClient.post("hospital/operateProjectAppList", params, handler);
        }
    }

    public static void getWebMeetingList(Integer doctorId, int proId, String beginDate, String endDate, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", proId);
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);

        if (-1 == doctorId) {
            if (!TextUtils.isEmpty(beginDate)) {
                params.put("startTime", beginDate);
            }
            if (!TextUtils.isEmpty(endDate)) {
                params.put("endTime", endDate);
            }
            ApiHttpClient.post("net/findAll", params, handler);
        } else {
            params.put("id", doctorId);
            ApiHttpClient.post("hospital/operateProjectAppList", params, handler);
        }
    }

    //2.9单个统计-文章类(课程类)
    public static void getAppWebMeetingDetails(int dataId, int projectType, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("dataId", dataId);
        params.put("projectType", projectType);
        ApiHttpClient.post("drug/projectApp/singleReport", params, handler);
    }


    public static void getwebtatic(int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("netId", productId);
        ApiHttpClient.post("net/singleStat", params, handler);
    }


    public static void getPhoneMeetingDetails(int meetingId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("meetingId", meetingId);
        ApiHttpClient.post("drug/projectPhone/singleReport", params, handler);
    }

    //2.13 统计 咨询
    public static void getAppWebStatic(int productId, int projectType, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        if (projectType == Constant.PROJECT_TYPE_ARTICLE) {
            ApiHttpClient.post("drug/projectAppInformationList/counts", params, handler);//2.13
        } else {
            ApiHttpClient.post("drug/projectAppContentList/counts", params, handler);//2/14
        }

    }

    public static void getAppWebMeetingsCount(int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        ApiHttpClient.post("drug/projectAppList/counts", params, handler);
    }

    /**
     * @param proId
     * @param projectType 0：课程，1文章
     * @param handler
     */
    public static void getAppCourseStatistics(int proId, int projectType, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", proId);
        params.put("projectType", projectType);
        ApiHttpClient.post("drug/projectAppList/counts", params, handler);
    }

    public static void getPhoneMeetingList(Integer doctorId, int productId, String beginDate, String endDate, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);

        if (-1 == doctorId) {
            if (!TextUtils.isEmpty(beginDate)) {
                params.put("startTime", beginDate);
            }
            if (!TextUtils.isEmpty(endDate)) {
                params.put("endTime", endDate);
            }
            ApiHttpClient.post("drug/projectPhoneList", params, handler);
        } else {
            params.put("id", doctorId);
            ApiHttpClient.post("hospital/operateProjectPhoneList", params, handler);
        }
    }

    public static void getPhoneeMeetingCount(int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        ApiHttpClient.post("drug/projectPhone/counts", params, handler);
    }

    /**
     * 非随诊型电话统计全部统计
     *
     * @param productId
     * @param handler
     */
    public static void getPhoneeMeetingsStatistics(int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        ApiHttpClient.post("drug/projectPhone/report", params, handler);
    }

    public static void getDoctorAttendCourses(int doctorId, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("doctorId", doctorId);
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);
        ApiHttpClient.post("hospital/doctorAttendProduct", params, handler);
    }

    /**
     * 医生参与的非随诊型项目个数
     *
     * @param doctorId
     * @param productId
     * @param type
     * @param handler
     */
    public static void getDoctorAttendProductCount(int doctorId, int productId, int type, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", doctorId);
        params.put("productId", productId);
        params.put("type", type);
        ApiHttpClient.post("hospital/operateProduct/counts", params, handler);
    }

    /**
     * 医生参与的非随诊型项目
     *
     * @param doctorId
     * @param page
     * @param handler
     */
    public static void getDoctorAttendProducts(int doctorId, int productId, int type, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", doctorId);
        params.put("productId", productId);
        params.put("type", type);
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);
        ApiHttpClient.post("hospital/operateProjectList", params, handler);
    }

    /**
     * 医生参与项目统计-单个统计
     *
     * @param meetingId
     * @param handler
     */
    public static void getDoctorAttendProductStatistics(int meetingId, int doctorId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("meetingId", meetingId);
        params.put("doctorId", doctorId);
        ApiHttpClient.post("hospital/operateProject", params, handler);
    }

    public static void getDoctorOtherMeetingList(int type, Integer doctorId, int proId, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", proId);
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);
        params.put("id", doctorId);
        if (type == Constant.APP) {
            ApiHttpClient.post("hospital/operateProjectAppList", params, handler);
        } else if (type == Constant.WEB) {
            ApiHttpClient.post("hospital/operateProjectNetList", params, handler);
        }
        if (type == Constant.MOBILE) {
            ApiHttpClient.post("hospital/operateProjectPhoneList", params, handler);
        }

    }

    public static void getDoctorMeetingStatistics(int projectType, int meetingId, Integer doctorId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("meetingId", meetingId);
        params.put("Type", projectType);
        params.put("doctorId", doctorId);
        ApiHttpClient.post("hospital/operateProject", params, handler);
    }


    public static void getUserInfomation(AsyncHttpResponseHandler handler) {
        ApiHttpClient.post("drugUser/info", handler);
    }

    /**
     * 随诊型项目统计
     *
     * @param productId
     */
    public static void getFollowUpClinic(int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        ApiHttpClient.post("drug/projectFollowUp/report", params, handler);
    }

    /**
     * 医生参与的非随诊型项目个数
     *
     * @param doctorId
     * @param productId
     * @param handler
     */
    public static void getDoctorMeetingsCount(Integer doctorId, int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", doctorId);
        params.put("productId", productId);
        ApiHttpClient.post("hospital/operateProduct/counts", params, handler);
    }

    /**
     * 统计课程
     */
    public static void getTotalstaticsCourse(int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        ApiHttpClient.post("drug/projectAppCauseList/counts", params, handler);
    }

    /**
     * 2.14统计专业内容
     */
    public static void getTotalstaticsprofessional(int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        ApiHttpClient.post("drug/projectAppContentList/counts", params, handler);
    }

    /**
     * 非随诊型电话统计全部统计
     */
    public static void getPhoneTotalstatic(int productId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        ApiHttpClient.post("drug/projectPhone/report", params, handler);
    }


    public static List<Meeting> AnalyzePhoneMeetingsList(byte[] data) {
        List<Meeting> list = new ArrayList<>();
        try {
            String dataContent = new String(data);
            if (validateStrings(dataContent)) {
                JSONObject jsonObject = new JSONObject(dataContent);
                JSONObject dataObject = jsonObject.getJSONObject("data");
                JSONArray listObject = dataObject.getJSONArray("voList");
                for (int i = 0; i < listObject.length(); i++) {
                    JSONObject object = listObject.getJSONObject(i);
                    Meeting meeting = new Meeting();
                    if (validateStrings(object.getString("url"))) {
                        meeting.picUrl = object.getString("url");
                    }
                    meeting.meetingId = object.getInt("meetingId");
                    meeting.productId = object.getInt("productId");
                    meeting.title = object.getString("title");
                    meeting.speaker = object.getString("speaker");
                    meeting.hospitalName = object.getString("hospitalName");
                    meeting.startTime = object.getString("startTime");
                    meeting.endTime = object.getString("endTime");
                    list.add(meeting);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static boolean validateStrings(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (!str.equals("null"))
                return true;
        }
        return false;
    }

    /**
     * 获取内容信息
     */
    public static void getContentList(int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);
        ApiHttpClient.post("activity/list", params, handler);
    }

    public static void getShareStaffList(int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("currentPage", page);
        params.put("size", Constant.PAGE_SIZE);
        ApiHttpClient.post("activity/share/staff", params, handler);
    }

    public static void share(int id, int type, String title, String url, ArrayList<Long> staffs, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("shareType", type);
        params.put("title", title);
        params.put("url", url);
        String str = "";
        for (Long l : staffs) {
            str += String.valueOf(l) + ",";
        }
        params.put("personIds[]", str);
        //params.setUseJsonStreamer(true);

        ApiHttpClient.post("activity/share", params, handler);
    }

}
