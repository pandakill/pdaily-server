package org.panda.pdaily.model.response;

import org.panda.pdaily.bean.PDUserInfoBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 返回的基本用户信息类
 *
 * Created by fangzanpan on 2017/2/7.
 */
public class PDRepUserInfoModel extends PDBaseResponseModel {

    private String user_id;
    private String user_name;
    private String icon_url;
    private String birthday;
    private String gender;
    private String attribute;

    public PDRepUserInfoModel() {}

    public PDRepUserInfoModel(PDUserInfoBean userInfoBean) {
        this.user_id = userInfoBean.getId() + "";
        this.user_name = userInfoBean.getUserName();
        this.icon_url = userInfoBean.getIconUrl();
        this.birthday = new SimpleDateFormat("yyyy-MM-dd").format(userInfoBean.getBirthDay());
        this.gender = userInfoBean.getGender() == 1 ? "男" : "女";
        this.attribute = userInfoBean.getAttribute() + "";
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
