package com.nuoxin.enterprise.bean;

/**
 * Created by Administrator on 2016/3/6.
 */
public class Meeting extends Entity{
    public String picUrl;
    public int meetingId;
    public int productId;
    public int projectId;
    public String title  = "";
    public String speaker = "";
    public String hospitalName = "";
    public String startTime = "";
    public String endTime = "";

    public int dataId; //课程、咨询专业内容同步来的id
    public int projectType;  //类型1：课程，2咨询，3专业内容
    public String publishTime = ""; //发布时间
    public String articleInfo = ""; //描述

}
