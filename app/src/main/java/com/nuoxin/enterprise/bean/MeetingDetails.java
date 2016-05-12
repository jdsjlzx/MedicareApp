package com.nuoxin.enterprise.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/20 0020.
 */
public class MeetingDetails implements Serializable {
    public int dataId;
    public int productId;
    public String title;
    public String publishTime;
    public int watchLiveCounts;
    public int reviewCounts;
    public int answerCounts;
    public int commentCounts;
    public int shareCounts;
    public int askCounts;
    public int collectCounts;
    public int readCounts;
    public int projectType;
    public int attendCounts;
    public int durations;
    public String day;//统计时间
}
