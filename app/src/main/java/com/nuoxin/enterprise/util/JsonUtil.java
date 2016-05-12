package com.nuoxin.enterprise.util;

import android.text.TextUtils;
import android.util.Log;

import com.nuoxin.enterprise.bean.AppStatic;
import com.nuoxin.enterprise.bean.AppWebCounts;
import com.nuoxin.enterprise.bean.ArticleStatic;
import com.nuoxin.enterprise.bean.ClassRoom;
import com.nuoxin.enterprise.bean.ContentBean;
import com.nuoxin.enterprise.bean.ContentStatic;
import com.nuoxin.enterprise.bean.Doctor;
import com.nuoxin.enterprise.bean.DoctorMeeting;
import com.nuoxin.enterprise.bean.FollwUpClinic;
import com.nuoxin.enterprise.bean.Meeting;
import com.nuoxin.enterprise.bean.MeetingDetails;
import com.nuoxin.enterprise.bean.PhoneCounts;
import com.nuoxin.enterprise.bean.PhoneStatics;
import com.nuoxin.enterprise.bean.PhoneTotalStatic;
import com.nuoxin.enterprise.bean.StaffBean;
import com.nuoxin.enterprise.bean.TotalStastics;
import com.nuoxin.enterprise.bean.TotalStaticCourse;
import com.nuoxin.enterprise.bean.TotalStaticsProfessional;
import com.nuoxin.enterprise.bean.User;
import com.nuoxin.enterprise.bean.WebStatics;
import com.nuoxin.enterprise.bean.Wechatstatics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhixian on 16/2/28.
 */
public class JsonUtil {


    public static List<ClassRoom> AnalyzeClassRoomList(Integer doctorId, byte[] data) {
        Log.d("lzx", "AnalyzeClassRoomList doctorId = " + doctorId);
        List<ClassRoom> list = new ArrayList<>();
        try {
            String dataContent = new String(data);
            if (validateStrings(dataContent)) {
                JSONObject jsonObject = new JSONObject(dataContent);
                JSONArray listObject = jsonObject.getJSONArray("data");
                for (int i = 0; i < listObject.length(); i++) {
                    JSONObject object = listObject.getJSONObject(i);
                    ClassRoom cr = new ClassRoom();
                    cr.id = object.getInt("id");
                    cr.picUrl = object.getString("url");
                    cr.title = object.getString("name");
                    if (-1 == doctorId) {
                        if (validateStrings(object.getString("type"))) {
                            cr.type = object.getInt("type");
                        }
                        cr.drugId = object.getInt("drugId");
                        cr.count = object.getInt("meetingCounts");
                    } else {
                        cr.doctorId = doctorId;
                    }


                    list.add(cr);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Doctor> AnalyzeDocotorList(byte[] data) {
        List<Doctor> list = new ArrayList<>();
        try {
            String dataContent = new String(data);
            if (validateStrings(dataContent)) {
                JSONObject jsonObject = new JSONObject(dataContent);
                JSONArray listObject = jsonObject.getJSONArray("data");
                for (int i = 0; i < listObject.length(); i++) {
                    JSONObject object = listObject.getJSONObject(i);
                    Doctor doctor = new Doctor();
                    doctor.id = object.getInt("id");

                    if (validateStrings(object.getString("sex"))) {
                        doctor.sex = object.getInt("sex");
                    }
                    doctor.picUrl = object.getString("avatarUrl");
                    doctor.name = object.getString("name");

                    if (validateStrings(object.getString("depart"))) {
                        doctor.department = object.getString("depart");
                    }
                    if (validateStrings(object.getString("hospitalName"))) {
                        doctor.hospital = object.getString("hospitalName");
                    }
                    doctor.saleName = object.getString("saleName");
                    doctor.saleProvince = object.getString("saleProvince");
                    doctor.position = object.getString("position");

                    if (validateStrings(object.getString("telephone"))) {
                        doctor.telephone = object.getString("telephone");
                    }
                    if (validateStrings(object.getString("email"))) {
                        doctor.email = object.getString("email");
                    }
                    list.add(doctor);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Doctor> AnalyzeDocotorStatisticsList(byte[] data) {
        List<Doctor> list = new ArrayList<>();
        try {
            String dataContent = new String(data);
            if (validateStrings(dataContent)) {
                JSONObject jsonObject = new JSONObject(dataContent);
                JSONArray listObject = jsonObject.getJSONArray("data");
                for (int i = 0; i < listObject.length(); i++) {
                    JSONObject object = listObject.getJSONObject(i);
                    Doctor doctor = new Doctor();
                    doctor.id = object.getInt("id");
                    doctor.count = object.getInt("counts");

                    doctor.isDoctor = object.optInt("isDoctor", 1);
                    if (doctor.isDoctor == 1) {
                        doctor.picUrl = object.getString("doctorUrl");
                        doctor.name = object.getString("doctorName");
                    } else {
                        doctor.picUrl = object.getString("patientUrl");
                        doctor.name = object.getString("patientName");
                    }

                    if (validateStrings(object.getString("depart"))) {
                        doctor.department = object.getString("depart");
                    }
                    if (validateStrings(object.getString("hospital"))) {
                        doctor.hospital = object.getString("hospital");
                    }
                    list.add(doctor);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Meeting> AnalyzeWeChatMeetingsList(Integer doctorId, byte[] data) {
        List<Meeting> list = new ArrayList<>();
        try {
            String dataContent = new String(data);
            if (validateStrings(dataContent)) {
                JSONObject jsonObject = new JSONObject(dataContent);
                if (-1 == doctorId) {
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray listObject = dataObject.getJSONArray("voList");
                    for (int i = 0; i < listObject.length(); i++) {
                        JSONObject object = listObject.getJSONObject(i);
                        Meeting meeting = new Meeting();
                        meeting.picUrl = object.getString("url");
                        meeting.meetingId = object.getInt("meetingId");
                        meeting.productId = object.getInt("productId");
                        meeting.title = object.getString("title");
                        meeting.speaker = object.getString("speaker");
                        meeting.startTime = object.getString("startTime");
                        meeting.endTime = object.getString("endTime");
                        list.add(meeting);
                    }
                } else {
                    JSONArray listObject = jsonObject.getJSONArray("data");
                    for (int i = 0; i < listObject.length(); i++) {
                        JSONObject object = listObject.getJSONObject(i);
                        Meeting meeting = new Meeting();
                        meeting.picUrl = object.getString("url");
                        meeting.meetingId = object.getInt("meetingId");
                        meeting.projectId = object.getInt("projectId");
                        meeting.title = object.getString("title");
                        meeting.speaker = object.getString("speaker");
                        meeting.startTime = object.getString("startTime");
                        meeting.endTime = object.getString("endTime");
                        if (validateStrings(object.getString("projectType"))) {
                            meeting.projectType = object.getInt("projectType");
                        }
                        list.add(meeting);
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Meeting> AnalyzeAppMeetingsList(Integer doctorId, byte[] data) {
        List<Meeting> list = new ArrayList<>();
        try {
            String dataContent = new String(data);
            if (validateStrings(dataContent)) {
                JSONObject jsonObject = new JSONObject(dataContent);
                if (-1 == doctorId) {
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray listObject = dataObject.getJSONArray("voList");
                    for (int i = 0; i < listObject.length(); i++) {
                        JSONObject object = listObject.getJSONObject(i);
                        Meeting meeting = new Meeting();
                        if (validateStrings(object.getString("url"))) {
                            meeting.picUrl = object.getString("url");
                        }
                        meeting.dataId = object.getInt("dataId");
                        meeting.productId = object.getInt("productId");
                        meeting.projectType = object.getInt("projectType");
                        meeting.title = object.getString("title");

                        if (validateStrings(object.getString("speaker"))) {
                            meeting.speaker = object.getString("speaker");
                        }

                        if (validateStrings(object.getString("hospitalName"))) {
                            meeting.hospitalName = object.getString("hospitalName");
                        }
                        if (validateStrings(object.getString("publishTime"))) {
                            meeting.publishTime = object.getString("publishTime");
                        }
                        if (validateStrings(object.getString("articleInfo"))) {
                            meeting.articleInfo = object.getString("articleInfo");
                        }

                        list.add(meeting);
                    }
                } else {
                    JSONArray listObject = jsonObject.getJSONArray("data");
                    for (int i = 0; i < listObject.length(); i++) {
                        JSONObject object = listObject.getJSONObject(i);
                        Meeting meeting = new Meeting();
                        if (validateStrings(object.getString("avatarUrl"))) {
                            meeting.picUrl = object.getString("avatarUrl");
                        }
                        meeting.dataId = object.getInt("dataId");
                        meeting.productId = object.getInt("productId");
                        meeting.title = object.getString("title");
                        meeting.publishTime = object.getString("publishTime");
                        if (validateStrings(object.getString("projectType"))) {
                            meeting.projectType = object.getInt("projectType");
                        }
                        list.add(meeting);
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Meeting> AnalyzeWebMeetingsList(Integer doctorId, byte[] data) {
        List<Meeting> list = new ArrayList<>();
        try {
            String dataContent = new String(data);
            if (validateStrings(dataContent)) {
                JSONObject jsonObject = new JSONObject(dataContent);
                if (-1 == doctorId) {
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray listObject = dataObject.getJSONArray("voList");
                    for (int i = 0; i < listObject.length(); i++) {
                        JSONObject object = listObject.getJSONObject(i);
                        Meeting meeting = new Meeting();
                        meeting.id = object.getInt("id");
                        if (validateStrings(object.getString("thumbnailUrl"))) {
                            meeting.picUrl = object.getString("thumbnailUrl");
                        }
                        if (validateStrings(object.getString("dataId"))) {
                            meeting.dataId = object.getInt("dataId");
                        }
                        if (validateStrings(object.getString("publishTime"))) {
                            meeting.publishTime = object.getString("publishTime");
                        }
                        meeting.productId = object.getInt("productId");
                        meeting.projectType = object.getInt("projectType");
                        meeting.title = object.getString("title");
                        meeting.speaker = object.getString("speaker");
                        list.add(meeting);
                    }
                } else {
                    JSONArray listObject = jsonObject.getJSONArray("data");
                    for (int i = 0; i < listObject.length(); i++) {
                        JSONObject object = listObject.getJSONObject(i);
                        Meeting meeting = new Meeting();
                        if (validateStrings(object.getString("avatarUrl"))) {
                            meeting.picUrl = object.getString("avatarUrl");
                        }
                        meeting.dataId = object.getInt("dataId");
                        meeting.productId = object.getInt("productId");
                        meeting.title = object.getString("title");
                        meeting.publishTime = object.getString("publishTime");
                        if (validateStrings(object.getString("projectType"))) {
                            meeting.projectType = object.getInt("projectType");
                        }
                        list.add(meeting);
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

//    public static List<Meeting> AnalyzePhoneMeetingsList(byte[] data) {
//        List<Meeting> list = new ArrayList<>();
//        try {
//            String dataContent = new String(data);
//            if (validateStrings(dataContent)) {
//                JSONObject jsonObject = new JSONObject(dataContent);
//                JSONObject dataObject = jsonObject.getJSONObject("data");
//                JSONArray listObject = dataObject.getJSONArray("voList");
//                for (int i = 0; i < listObject.length(); i++) {
//                    JSONObject object = listObject.getJSONObject(i);
//                    Meeting meeting = new Meeting();
//                    if(validateStrings(object.getString("url"))){
//                        meeting.picUrl = object.getString("url");
//                    }
//                    meeting.productId = object.getInt("productId");
//                    meeting.title = object.getString("title");
//                    meeting.speaker = object.getString("speaker");
//                    meeting.hospitalName = object.getString("hospitalName");
//                    meeting.startTime = object.getString("startTime");
//                    meeting.endTime = object.getString("endTime");
//                    list.add(meeting);
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    public static String getUserToken(String json) {
        String token = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            token = dataObject.getString("token");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static int getPageCount(byte[] data) {
        int totalPage = 0;
        try {
            JSONObject jsonObject = new JSONObject(new String(data));
            if (jsonObject.isNull("pages")) {
                if (validateStrings(jsonObject.getString("page"))) {
                    JSONObject pagesObject = jsonObject.getJSONObject("page");
                    totalPage = pagesObject.getInt("totalPage");
                }
            } else {
                if (validateStrings(jsonObject.getString("pages"))) {
                    JSONObject pagesObject = jsonObject.getJSONObject("pages");
                    totalPage = pagesObject.getInt("totalPage");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return totalPage;
    }

    public static int getWeChatMeetingsCount(byte[] data) {
        int total = 0;
        try {
            if (validateStrings(new String(data))) {
                JSONObject jsonObject = new JSONObject(new String(data));
                JSONObject dataObject = jsonObject.getJSONObject("data");
                total = dataObject.getInt("meetingCounts");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return total;
    }

    public static AppWebCounts getAppCoursesCount(byte[] data) {
        AppWebCounts appWebCounts = null;
        try {
            appWebCounts = new AppWebCounts();
            JSONObject jsonObject = new JSONObject(new String(data));
            JSONObject dataObject = jsonObject.getJSONObject("data");
            appWebCounts.courseCounts = dataObject.getInt("courseCounts");
            appWebCounts.informationCounts = dataObject.getInt("informationCounts");
            appWebCounts.contentCounts = dataObject.getInt("contentCounts");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return appWebCounts;
    }

    public static AppWebCounts getWebCoursesCount(byte[] data) {
        AppWebCounts appWebCounts = null;
        try {
            appWebCounts = new AppWebCounts();
            JSONObject jsonObject = new JSONObject(new String(data));
            JSONObject dataObject = jsonObject.getJSONObject("data");
            appWebCounts.courseCounts = dataObject.getInt("counts");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return appWebCounts;
    }

    public static PhoneCounts getPhoneMeetinsCount(byte[] data) {
        PhoneCounts phoneCounts = null;
        try {
            phoneCounts = new PhoneCounts();
            JSONObject jsonObject = new JSONObject(new String(data));
            JSONObject dataObject = jsonObject.getJSONObject("data");
            phoneCounts.meetingCounts = dataObject.getInt("meetingCounts");
            if (validateStrings(dataObject.getString("durations"))) {
                phoneCounts.duration = dataObject.getInt("durations");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return phoneCounts;
    }

    public static String getStateFromServer(String json) {
        String state = Constant.FAIL; //SUCCESS
        try {
            JSONObject jsonObject = new JSONObject(json);
            state = jsonObject.getString("code");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return state;
    }

    public static User getUserInfo(String email, String password, String json) {
        User user = null;
        try {
            user = new User();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            user.saleName = dataObject.getString("saleName");
            user.province = dataObject.getString("saleProvince");
            user.email = email;
            user.password = password;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User getUserInfo(String json) {
        User user = null;
        try {
            user = new User();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            user.id = dataObject.getInt("id");
            user.name = dataObject.getString("name");
            user.employeeNo = dataObject.getString("employeeNo");
            user.mobile = dataObject.getString("telephone");
            user.email = dataObject.getString("email");
            user.department = dataObject.getString("dept");
            user.avatarUrl = dataObject.getString("avatarUrl");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static Wechatstatics getWechatstatics(String json) {
        Wechatstatics wechatstatics = null;
        try {
            wechatstatics = new Wechatstatics();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            wechatstatics.applyCounts = dataObject.getInt("applyCounts");
            wechatstatics.duration = dataObject.getString("duration");
            wechatstatics.endTime = dataObject.getString("endTime");
            wechatstatics.passCounts = dataObject.getInt("passCounts");
            wechatstatics.passRate = dataObject.getString("passRate");
            wechatstatics.productName = dataObject.getString("productName");
            wechatstatics.reviewCounts = dataObject.getInt("reviewCounts");
            wechatstatics.signInCounts = dataObject.getInt("signInCounts");
            wechatstatics.signInRate = dataObject.getString("signInRate");
            wechatstatics.speaker = dataObject.getString("speaker");
            wechatstatics.startTime = dataObject.getString("startTime");
            wechatstatics.title = dataObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wechatstatics;
    }

    public static TotalStastics getTotalStatics(String json) {
        TotalStastics totalStastics = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            if (validateStrings(jsonObject.getString("data"))) {
                totalStastics = new TotalStastics();
                JSONObject dataObject = jsonObject.getJSONObject("data");
                totalStastics.applyCounts = dataObject.getInt("applyCounts");
                totalStastics.passCounts = dataObject.getInt("passCounts");
                totalStastics.passRate = dataObject.getString("passRate");
                totalStastics.reviewCounts = dataObject.getInt("reviewCounts");
                totalStastics.signInCounts = dataObject.getInt("signInCounts");
                totalStastics.signInRate = dataObject.getString("signInRate");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return totalStastics;
    }


    public static MeetingDetails getMeetingDetails(String json) {
        MeetingDetails meetingDetails = null;
        JSONObject jsonObject = null;
        try {
            meetingDetails = new MeetingDetails();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            meetingDetails.dataId = dataObject.getInt("dataId");

            if (validateStrings(dataObject.getString("title"))) {
                meetingDetails.title = dataObject.getString("title");
            }

            if (validateStrings(dataObject.getString("publishTime"))) {
                meetingDetails.publishTime = dataObject.getString("publishTime");
            }

            if (validateStrings(dataObject.getString("productId"))) {
                meetingDetails.productId = dataObject.getInt("productId");
            }
            if (validateStrings(dataObject.getString("watchLiveCounts"))) {
                meetingDetails.watchLiveCounts = dataObject.getInt("watchLiveCounts");
            }
            if (validateStrings(dataObject.getString("reviewCounts"))) {
                meetingDetails.reviewCounts = dataObject.getInt("reviewCounts");
            }
            if (validateStrings(dataObject.getString("answerCounts"))) {
                meetingDetails.answerCounts = dataObject.getInt("answerCounts");
            }
            if (validateStrings(dataObject.getString("commentCounts"))) {
                meetingDetails.commentCounts = dataObject.getInt("commentCounts");
            }
            if (validateStrings(dataObject.getString("shareCounts"))) {
                meetingDetails.shareCounts = dataObject.getInt("shareCounts");
            }
            if (validateStrings(dataObject.getString("askCounts"))) {
                meetingDetails.askCounts = dataObject.getInt("askCounts");
            }
            if (validateStrings(dataObject.getString("collectCounts"))) {
                meetingDetails.collectCounts = dataObject.getInt("collectCounts");
            }
            if (validateStrings(dataObject.getString("readCounts"))) {
                meetingDetails.readCounts = dataObject.getInt("readCounts");
            }
            if (validateStrings(dataObject.getString("projectType"))) {
                meetingDetails.projectType = dataObject.getInt("projectType");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return meetingDetails;
    }

    //,"data":{"title":"dsa电话会议","day":"2016-04-12 18:58:52","durations":0,"attendCounts":1,"askCounts":1,"answerCounts":1},"description":null,"page":null}
    public static MeetingDetails getPhoneMeetingDetails(String json) {
        MeetingDetails meetingDetails = null;
        JSONObject jsonObject = null;
        try {
            meetingDetails = new MeetingDetails();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");

            if (validateStrings(dataObject.getString("title"))) {
                meetingDetails.title = dataObject.getString("title");
            }
            if (validateStrings(dataObject.getString("day"))) {
                meetingDetails.day = dataObject.getString("day");
            }

            if (validateStrings(dataObject.getString("attendCounts"))) {
                meetingDetails.attendCounts = dataObject.getInt("attendCounts");
            }
            if (validateStrings(dataObject.getString("askCounts"))) {
                meetingDetails.askCounts = dataObject.getInt("askCounts");
            }
            if (validateStrings(dataObject.getString("answerCounts"))) {
                meetingDetails.answerCounts = dataObject.getInt("answerCounts");
            }

            if (validateStrings(dataObject.getString("durations"))) {
                meetingDetails.durations = dataObject.getInt("durations");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return meetingDetails;
    }

    public static FollwUpClinic getFollwUpClinic(String json) {
        FollwUpClinic follwUpClinic = null;
        JSONObject jsonObject = null;
        try {
            follwUpClinic = new FollwUpClinic();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            follwUpClinic.applyCounts = dataObject.getInt("applyCounts");
            follwUpClinic.finishCounts = dataObject.getInt("finishCounts");
            follwUpClinic.finishRate = dataObject.getString("finishRate");
            follwUpClinic.passCounts = dataObject.getInt("passCounts");
            follwUpClinic.passRate = dataObject.getString("passRate");
            follwUpClinic.startCounts = dataObject.getInt("startCounts");
            follwUpClinic.unfinishCounts = dataObject.getInt("unfinishCounts");
            if (validateStrings(dataObject.getString("productId"))) {
                follwUpClinic.productId = dataObject.getInt("productId");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return follwUpClinic;
    }

    public static List<Meeting> AnalyzeDoctorOtherMeetingsList(int type, Integer doctorId, byte[] data) {
        List<Meeting> list = new ArrayList<>();
        try {
            String dataContent = new String(data);
            if (validateStrings(dataContent)) {
                JSONObject jsonObject = new JSONObject(dataContent);
                JSONArray listObject = jsonObject.getJSONArray("data");
                for (int i = 0; i < listObject.length(); i++) {
                    JSONObject object = listObject.getJSONObject(i);
                    Meeting meeting = new Meeting();

                    if (type == Constant.APP) {
                        meeting.picUrl = object.getString("url");
                        meeting.dataId = object.getInt("dataId");
                        meeting.productId = object.getInt("productId");
                        meeting.title = object.getString("title");
                        meeting.publishTime = object.getString("publishTime");
                        if (validateStrings(object.getString("projectType"))) {
                            meeting.projectType = object.getInt("projectType");
                        }
                    } else if (type == Constant.WEB) {
                        meeting.picUrl = object.getString("url");
                        meeting.productId = object.getInt("productId");
                        meeting.title = object.getString("title");
                        meeting.publishTime = object.getString("publishTime");
                        if (validateStrings(object.getString("projectType"))) {
                            meeting.projectType = object.getInt("projectType");
                        }
                    } else if (type == Constant.MOBILE) {
                        meeting.picUrl = object.getString("url");
                        meeting.productId = object.getInt("productId");
                        meeting.title = object.getString("title");
                        meeting.speaker = object.getString("speaker");
                        meeting.startTime = object.getString("startTime");
                        meeting.endTime = object.getString("endTime");
                        meeting.hospitalName = object.getString("hospitalName");
                        meeting.projectType = Constant.PROJECT_TYPE_MOBILE;
                    }


                    list.add(meeting);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Integer> getDoctorMeetingCounts(byte[] data) {
        List<Integer> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(new String(data));
            JSONObject dataObject = jsonObject.getJSONObject("data");
            list.add(0, dataObject.getInt("meetingCounts"));
            list.add(1, dataObject.getInt("appCounts"));
            list.add(2, dataObject.getInt("netCounts"));
            list.add(3, dataObject.getInt("teleCounts"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * {
     * "code": "SUCCESS",
     * "data": {
     * "projectType": 0,
     * "isApplyPass": 1,
     * "isReview": 1,
     * "isCollect": null,
     * "isShare": null,
     * "isAnswer": null,
     * "isSignIn": 1,
     * "isWatchLive": null,
     * "isComment": null,
     * "isAsk": null
     * }
     */
    public static List<DoctorMeeting> AnalyzeDoctorMeetingStatistics(byte[] data, int type) {
        List<DoctorMeeting> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(new String(data));
            JSONObject dataObject = jsonObject.getJSONObject("data");
            DoctorMeeting doctorMeeting;

            if (type == Constant.PROJECT_TYPE_WEIXIN && validateObject(dataObject.getString("isApplyPass"))) {
                doctorMeeting = new DoctorMeeting();
                if(validateStrings(dataObject.getString("isApplyPass"))){
                    doctorMeeting.isChecked = (dataObject.getInt("isApplyPass") == 1);
                }else {
                    doctorMeeting.isChecked = false;
                }
                doctorMeeting.title = "申请是否通过";
                list.add(doctorMeeting);
            }
            if (type == Constant.PROJECT_TYPE_WEIXIN && validateObject(dataObject.getString("isSignIn"))) {
                doctorMeeting = new DoctorMeeting();
                if(validateStrings(dataObject.getString("isSignIn"))){
                    doctorMeeting.isChecked = (dataObject.getInt("isSignIn") == 1);
                }else {
                    doctorMeeting.isChecked = false;
                }
                doctorMeeting.title = "是否签到";
                list.add(doctorMeeting);
            }
            if (type != Constant.PROJECT_TYPE_ARTICLE && validateObject(dataObject.getString("isReview"))) {
                doctorMeeting = new DoctorMeeting();
                if(validateStrings(dataObject.getString("isReview"))){
                    doctorMeeting.isChecked = (dataObject.getInt("isReview") == 1);
                }else {
                    doctorMeeting.isChecked = false;
                }
                doctorMeeting.title = "是否回看";
                list.add(doctorMeeting);
            }
            if (type == Constant.PROJECT_TYPE_COURSE && validateObject(dataObject.getString("isWatchLive"))) {
                doctorMeeting = new DoctorMeeting();
                if(validateStrings(dataObject.getString("isWatchLive"))){
                    doctorMeeting.isChecked = (dataObject.getInt("isWatchLive") == 1);
                }else {
                    doctorMeeting.isChecked = false;
                }
                doctorMeeting.title = "是否观看直播";
                list.add(doctorMeeting);
            }
            if (type != Constant.PROJECT_TYPE_WEIXIN && validateObject(dataObject.getString("isCollect"))) {
                doctorMeeting = new DoctorMeeting();
                if(validateStrings(dataObject.getString("isCollect"))){
                    doctorMeeting.isChecked = (dataObject.getInt("isCollect") == 1);
                }else {
                    doctorMeeting.isChecked = false;
                }
                doctorMeeting.title = "是否收藏";
                list.add(doctorMeeting);
            }
            if (type == Constant.PROJECT_TYPE_COURSE && validateObject(dataObject.getString("isComment"))) {
                doctorMeeting = new DoctorMeeting();
                if(validateStrings(dataObject.getString("isComment"))){
                    doctorMeeting.isChecked = (dataObject.getInt("isComment") == 1);
                }else {
                    doctorMeeting.isChecked = false;
                }
                doctorMeeting.title = "是否评论";
                list.add(doctorMeeting);
            }
            if (type != Constant.PROJECT_TYPE_WEIXIN && validateObject(dataObject.getString("isShare"))) {
                doctorMeeting = new DoctorMeeting();
                if(validateStrings(dataObject.getString("isShare"))){
                    doctorMeeting.isChecked = (dataObject.getInt("isShare") == 1);
                }else {
                    doctorMeeting.isChecked = false;
                }
                doctorMeeting.title = "是否分享";
                list.add(doctorMeeting);
            }
            if (type == Constant.PROJECT_TYPE_COURSE && validateObject(dataObject.getString("isAsk"))) {
                doctorMeeting = new DoctorMeeting();
                if(validateStrings(dataObject.getString("isAsk"))){
                    doctorMeeting.isChecked = (dataObject.getInt("isAsk") == 1);
                }else {
                    doctorMeeting.isChecked = false;
                }
                doctorMeeting.title = "是否参与提问";
                list.add(doctorMeeting);
            }
            if (type == Constant.PROJECT_TYPE_COURSE && validateObject(dataObject.getString("isAnswer"))) {
                doctorMeeting = new DoctorMeeting();
                if(validateStrings(dataObject.getString("isAnswer"))){
                    doctorMeeting.isChecked = (dataObject.getInt("isAnswer") == 1);
                }else {
                    doctorMeeting.isChecked = false;
                }
                doctorMeeting.title = "是否参与答题";
                list.add(doctorMeeting);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static TotalStaticCourse getTotalStaticsCourse(String json) {
        TotalStaticCourse tsc = null;
        JSONObject jsonObject = null;
        try {
            tsc = new TotalStaticCourse();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            if (validateStrings(dataObject.getString("answerNumber"))) {
                tsc.answerNumber = dataObject.getInt("answerNumber");
            }
            if (validateStrings(dataObject.getString("answerTime"))) {
                tsc.answerTime = dataObject.getInt("answerTime");
            }
            if (validateStrings(dataObject.getString("askNumber"))) {
                tsc.askNumber = dataObject.getInt("askNumber");
            }
            if (validateStrings(dataObject.getString("askTime"))) {
                tsc.askTime = dataObject.getInt("askTime");
            }
            if (validateStrings(dataObject.getString("collectNumber"))) {
                tsc.collectNumber = dataObject.getInt("collectNumber");
            }
            if (validateStrings(dataObject.getString("collectTime"))) {
                tsc.collectTime = dataObject.getInt("collectTime");
            }
            if (validateStrings(dataObject.getString("commentNumber"))) {
                tsc.commentNumber = dataObject.getInt("commentNumber");
            }
            if (validateStrings(dataObject.getString("reviewNumber"))) {
                tsc.reviewNumber = dataObject.getInt("reviewNumber");
            }
            if (validateStrings(dataObject.getString("reviewTime"))) {
                tsc.reviewTime = dataObject.getInt("reviewTime");
            }
            if (validateStrings(dataObject.getString("shareNumber"))) {
                tsc.shareNumber = dataObject.getInt("shareNumber");
            }

            if (validateStrings(dataObject.getString("watchLiveNumber"))) {
                tsc.watchLiveNumber = dataObject.getInt("watchLiveNumber");
            }
            if (validateStrings(dataObject.getString("watchLiveTime"))) {
                tsc.watchLiveTime = dataObject.getInt("watchLiveTime");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tsc;
    }


    public static TotalStaticsProfessional getTotalStaticsPeofessional(String json) {
        TotalStaticsProfessional tsp = null;
        JSONObject jsonObject = null;
        try {
            tsp = new TotalStaticsProfessional();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");

            if (validateStrings(dataObject.getString("collectNumber"))) {
                tsp.collectNumber = dataObject.getInt("collectNumber");
            }
            if (validateStrings(dataObject.getString("collectTime"))) {
                tsp.collectTime = dataObject.getInt("collectTime");
            }
            if (validateStrings(dataObject.getString("commentNumber"))) {
                tsp.commentNumber = dataObject.getInt("commentNumber");
            }
            if (validateStrings(dataObject.getString("commentTime"))) {
                tsp.commentTime = dataObject.getInt("commentTime");
            }
            if (validateStrings(dataObject.getString("readNumber"))) {
                tsp.readNumber = dataObject.getInt("readNumber");
            }
            if (validateStrings(dataObject.getString("readTime"))) {
                tsp.readTime = dataObject.getInt("readTime");
            }
            if (validateStrings(dataObject.getString("shareNumber"))) {
                tsp.shareNumber = dataObject.getInt("shareNumber");
            }
            if (validateStrings(dataObject.getString("shareTime"))) {
                tsp.shareTime = dataObject.getInt("shareTime");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tsp;
    }


    public static PhoneTotalStatic getPhoneTotalStatics(String json) {
        PhoneTotalStatic pts = null;
        JSONObject jsonObject = null;
        try {
            pts = new PhoneTotalStatic();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            if (validateStrings(dataObject.getString("durations"))) {
                pts.durations = dataObject.getInt("durations");
            }
            if (validateStrings(dataObject.getString("askCounts"))) {
                pts.askCounts = dataObject.getInt("askCounts");
            }
            if (validateStrings(dataObject.getString("answerCounts"))) {
                pts.answerCounts = dataObject.getInt("answerCounts");
            }
            if (validateStrings(dataObject.getString("answerPersonCounts"))) {
                pts.answerPersonCounts = dataObject.getInt("answerPersonCounts");
            }
            if (validateStrings(dataObject.getString("askPersonCounts"))) {
                pts.askPersonCounts = dataObject.getInt("askPersonCounts");
            }
            if (validateStrings(dataObject.getString("attendCounts"))) {
                pts.attendCounts = dataObject.getInt("attendCounts");
            }
            if (validateStrings(dataObject.getString("attendPersonCounts"))) {
                pts.attendPersonCounts = dataObject.getInt("attendPersonCounts");
            }
            if (validateStrings(dataObject.getString("meetingCounts"))) {
                pts.meetingCounts = dataObject.getInt("meetingCounts");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pts;
    }


    public static PhoneStatics getPhoneStatics(String json) {
        PhoneStatics ps = null;
        JSONObject jsonObject = null;
        try {
            ps = new PhoneStatics();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");

            if (validateStrings(dataObject.getString("title"))) {
                ps.title = dataObject.getString("title");
            }
            if (validateStrings(dataObject.getString("day"))) {
                ps.day = dataObject.getString("day");
            }
            if (validateStrings(dataObject.getString("durations"))) {
                ps.durations = dataObject.getInt("durations");
            }
            if (validateStrings(dataObject.getString("attendCounts"))) {
                ps.attendCounts = dataObject.getInt("attendCounts");
            }
            if (validateStrings(dataObject.getString("askCounts"))) {
                ps.askCounts = dataObject.getInt("askCounts");
            }
            if (validateStrings(dataObject.getString("answerCounts"))) {
                ps.answerCounts = dataObject.getInt("answerCounts");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ps;
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


    public static AppStatic getAppstatic(String json) {
        AppStatic as = null;
        JSONObject jsonObject = null;
        try {
            as = new AppStatic();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");


            if (validateStrings(dataObject.getString("collectNumber"))) {
                as.collectNumber = dataObject.getInt("collectNumber");
            }
            if (validateStrings(dataObject.getString("collectTime"))) {
                as.collectTime = dataObject.getInt("collectTime");
            }
            if (validateStrings(dataObject.getString("commentNumber"))) {
                as.commentNumber = dataObject.getInt("commentNumber");
            }
            if (validateStrings(dataObject.getString("commentTime"))) {
                as.commentTime = dataObject.getInt("commentTime");
            }
            if (validateStrings(dataObject.getString("readNumber"))) {
                as.readNumber = dataObject.getInt("readNumber");
            }
            if (validateStrings(dataObject.getString("readTime"))) {
                as.readTime = dataObject.getInt("readTime");
            }
            if (validateStrings(dataObject.getString("shareNumber"))) {
                as.shareNumber = dataObject.getInt("shareNumber");
            }
            if (validateStrings(dataObject.getString("shareTime"))) {
                as.shareTime = dataObject.getInt("shareTime");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return as;
    }


    public static WebStatics getWebstatic(String json) {
        WebStatics ws = null;
        JSONObject jsonObject = null;
        try {
            ws = new WebStatics();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");

            if (validateStrings(dataObject.getString("netId"))) {
                ws.netId = dataObject.getInt("netId");
            }
            if (validateStrings(dataObject.getString("watchLiveCounts"))) {
                ws.watchLiveCounts = dataObject.getInt("watchLiveCounts");
            }
            if (validateStrings(dataObject.getString("reviewCounts"))) {
                ws.reviewCounts = dataObject.getInt("reviewCounts");
            }
            if (validateStrings(dataObject.getString("answerCounts"))) {
                ws.answerCounts = dataObject.getInt("answerCounts");
            }
            if (validateStrings(dataObject.getString("commentCounts"))) {
                ws.commentCounts = dataObject.getInt("commentCounts");
            }
            if (validateStrings(dataObject.getString("shareCounts"))) {
                ws.shareCounts = dataObject.getInt("shareCounts");
            }
            if (validateStrings(dataObject.getString("askCounts"))) {
                ws.askCounts = dataObject.getInt("askCounts");
            }
            if (validateStrings(dataObject.getString("collectCounts"))) {
                ws.collectCounts = dataObject.getInt("collectCounts");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ws;
    }


    public static ArticleStatic getArticleStatic(String json) {
        ArticleStatic as = null;
        JSONObject jsonObject = null;
        try {
            as = new ArticleStatic();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");

            if (validateStrings(dataObject.getString("commentNumber"))) {
                as.commentNumber = dataObject.getInt("commentNumber");
            }
            if (validateStrings(dataObject.getString("commentTime"))) {
                as.commentTime = dataObject.getInt("commentTime");
            }
            if (validateStrings(dataObject.getString("shareNumber"))) {
                as.shareNumber = dataObject.getInt("shareNumber");
            }
            if (validateStrings(dataObject.getString("shareTime"))) {
                as.shareTime = dataObject.getInt("shareTime");
            }
            if (validateStrings(dataObject.getString("readNumber"))) {
                as.readNumber = dataObject.getInt("readNumber");
            }
            if (validateStrings(dataObject.getString("readTime"))) {
                as.readTime = dataObject.getInt("readTime");
            }
            if (validateStrings(dataObject.getString("collectNumber"))) {
                as.collectNumber = dataObject.getInt("collectNumber");
            }
            if (validateStrings(dataObject.getString("collectTime"))) {
                as.collectTime = dataObject.getInt("collectTime");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return as;
    }


    public static ContentStatic getContentStatic(String json) {
        ContentStatic cs = null;
        JSONObject jsonObject = null;
        try {
            cs = new ContentStatic();
            jsonObject = new JSONObject(json);
            JSONObject dataObject = jsonObject.getJSONObject("data");

            if (validateStrings(dataObject.getString("commentNumber"))) {
                cs.commentNumber = dataObject.getInt("commentNumber");
            }
            if (validateStrings(dataObject.getString("commentTime"))) {
                cs.commentTime = dataObject.getInt("commentTime");
            }
            if (validateStrings(dataObject.getString("shareNumber"))) {
                cs.shareNumber = dataObject.getInt("shareNumber");
            }
            if (validateStrings(dataObject.getString("shareTime"))) {
                cs.shareTime = dataObject.getInt("shareTime");
            }
            if (validateStrings(dataObject.getString("readNumber"))) {
                cs.readNumber = dataObject.getInt("readNumber");
            }
            if (validateStrings(dataObject.getString("readTime"))) {
                cs.readTime = dataObject.getInt("readTime");
            }
            if (validateStrings(dataObject.getString("collectNumber"))) {
                cs.collectNumber = dataObject.getInt("collectNumber");
            }
            if (validateStrings(dataObject.getString("collectTime"))) {
                cs.collectTime = dataObject.getInt("collectTime");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cs;
    }


    private static boolean validateStrings(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (!str.equals("null"))
                return true;
        }
        return false;
    }

    private static boolean validateObject(String str) {
        if (!TextUtils.isEmpty(str)) {
            return true;
        }
        return false;
    }

    public static List<ContentBean> AnalyzeContentList(byte[] data) {
        List<ContentBean> list = new ArrayList<>();
        try {
            String dataContent = new String(data);
            if (validateStrings(dataContent)) {
                JSONObject jsonObject = new JSONObject(dataContent);
                JSONArray listObject = jsonObject.getJSONArray("data");
                for (int i = 0; i < listObject.length(); i++) {
                    JSONObject object = listObject.getJSONObject(i);
                    ContentBean cr = new ContentBean();
                    cr.id = object.getInt("id");
                    cr.title = object.getString("title");
                    cr.key_content = object.getString("key_content");
                    cr.url = object.getString("url");
                    cr.contentUrl = object.getString("contentUrl");

                    list.add(cr);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<StaffBean> AnalyzeStaffList(byte[] data) {
        List<StaffBean> list = new ArrayList<>();
        try {
            String dataContent = new String(data);
            if (validateStrings(dataContent)) {
                JSONObject jsonObject = new JSONObject(dataContent);
                JSONArray listObject = jsonObject.getJSONArray("data");
                for (int i = 0; i < listObject.length(); i++) {
                    JSONObject object = listObject.getJSONObject(i);
                    StaffBean cr = new StaffBean();
                    cr.id = object.getInt("id");
                    cr.name = object.getString("name");
                    cr.url = object.getString("url");

                    list.add(cr);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

}
