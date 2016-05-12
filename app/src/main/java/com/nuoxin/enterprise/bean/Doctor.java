package com.nuoxin.enterprise.bean;

/**
 * Created by lizhixian on 16/3/2.
 */
public class Doctor extends Entity{
    public String name = "";
    public String picUrl = "";
    public String hospital = "";
    public String department = "";
    public String saleName = "";
    public String saleProvince = "";
    public String telephone = "";
    public String email = "";
    public String position = "";//职务
    public int isDoctor = 1;
    public int count = 0;
    public int sex;// 0：男，1：女，2：未知

}
