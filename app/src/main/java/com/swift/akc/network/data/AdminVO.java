package com.swift.akc.network.data;

import java.util.Date;

public class AdminVO {

    private int id;
    private String isBlock;
    private String fullName;
    private Date dob;
    private String sex;
    private Date dom;
    private String isMsgDisplayed;
    private String isDoctor;
    private int doctorId;
    private String isAdmin;
    private String isReturn;
    private String userName;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(String isBlock) {
        this.isBlock = isBlock;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getDom() {
        return dom;
    }

    public void setDom(Date dom) {
        this.dom = dom;
    }

    public String getIsMsgDisplayed() {
        return isMsgDisplayed;
    }

    public void setIsMsgDisplayed(String isMsgDisplayed) {
        this.isMsgDisplayed = isMsgDisplayed;
    }

    public String getIsDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(String isDoctor) {
        this.isDoctor = isDoctor;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
