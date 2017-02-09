package org.panda.pdaily.bean;

import java.io.Serializable;

/**
 * 日记内容的数据库实体类
 *
 * Created by fangzanpan on 2017/2/8.
 */
public class PDDailyContentBean implements Serializable {
//    id (Primary)	bigint(244)	No		自增主键id
//    user_id	bigint(244)	No	0	用户id，t_user_info.id
//    weather_id	bigint(244)	No	0	天气id，t_weather.id
//    title	varchar(244)	No		日记标题
//    content	text	No		日记内容
//    pic_address	text	Yes	NULL	图片地址，格式为：图片地址，格式为：["http://HOST/imgs/0/1.jpeg","http://HOST/imgs/0/2.png"]
//    latitude	double	Yes	NULL	纬度
//    longitude	double	Yes	NULL	经度
//    address	varchar(244)	Yes	NULL	显示地址名称
//    public_date	bigint(244)	No		发布日期
//    address_show_type	int(2)	Yes	NULL	地址显示类型，1：address；2：map
//    like_count	bigint(244)	No	0	被喜欢数量
//    is_public	int(2)	No	0	是否公开，默认为0，1：是；0：否
//    is_del	int(2)	No	0	是否删除，默认为0，1是，0否
//    tags	varchar(244)	Yes	NULL	日记标签，保留
    private long id;
    private long user_id;
    private long weather_id;
    private String title;
    private String content;
    private String picAddress;
    private double latitude;
    private double longitude;
    private String address;
    private long publicDate;
    private int addressShowType;
    private long likeCount;
    private int isPublic;
    private int isDel;
    private String tags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(long weather_id) {
        this.weather_id = weather_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(long publicDate) {
        this.publicDate = publicDate;
    }

    public int getAddressShowType() {
        return addressShowType;
    }

    public void setAddressShowType(int addressShowType) {
        this.addressShowType = addressShowType;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
