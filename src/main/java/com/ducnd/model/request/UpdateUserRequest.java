package com.ducnd.model.request;

import java.sql.Date;

public class UpdateUserRequest {
    private String username;
    private String avatar;
    private Date birthday;
    private String city;
    private String job;
    private Boolean sex;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String avatar, Date birthday, String city, String job, Boolean sex) {
        this.avatar = avatar;
        this.birthday = birthday;
        this.city = city;
        this.job = job;
        this.sex = sex;
    }





    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}

