package code.vera.myblog.bean.home;

import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

import code.vera.myblog.bean.GeoBean;

/**
 * Created by vera on 2017/1/19 0019.
 */

public class StatusesBean implements Serializable {
    private String created_at;//创建时间
    private long id;//微博ID
    private String mid;//微博MID
    private String idstr;//	字符串型的微博ID
    private String text;//文字
    private int textLength; //文字长度
    private int source_allowclick;//来源是否可以点击
    private String source;//来源
    private boolean favorited;//是否已经收藏
    //配图
    private String pic_urls;
    private List<PicBean> pic_list;//
    @Nullable
    private String thumbnail_pic;//缩略图片地址，没有时不返回此字段
    @Nullable
    private String bmiddle_pic;//中等尺寸图片地址，没有时不返回此字段
    @Nullable
    private String original_pic;//中等尺寸图片地址，没有时不返回此字段
    private String user;
    private UserInfoBean userbean;
    private long reposts_count;//	转发数
    private long comments_count;//评论数
    private long attitudes_count;//表态数
    private String geo;//地理位置
    private GeoBean geoBean;

    @Nullable
    private String retweeted_status;//被转发的原微博信息字段，当该微博为转发微博时返回
    @Nullable
    private RetweetedStatusBean retweetedStatusBean;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Nullable
    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(@Nullable String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    @Nullable
    public String getBmiddle_pic() {
        return bmiddle_pic;
    }

    public void setBmiddle_pic(@Nullable String bmiddle_pic) {
        this.bmiddle_pic = bmiddle_pic;
    }

    @Nullable
    public String getOriginal_pic() {
        return original_pic;
    }

    public void setOriginal_pic(@Nullable String original_pic) {
        this.original_pic = original_pic;
    }

    @Nullable
    public String getRetweeted_status() {
        return retweeted_status;
    }

    @Nullable
    public void setRetweeted_status(String retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    @Nullable
    public RetweetedStatusBean getRetweetedStatusBean() {
        return JSON.parseObject(retweeted_status, RetweetedStatusBean.class);
    }

    @Nullable
    public void setRetweetedStatusBean(RetweetedStatusBean retweetedStatusBean) {
        this.retweetedStatusBean = retweetedStatusBean;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(String pic_urls) {
        this.pic_urls = pic_urls;
    }

    public List<PicBean> getPic_list() {
        return JSONArray.parseArray(pic_urls, PicBean.class);
    }

    public void setPic_list(List<PicBean> pic_list) {
        this.pic_list = pic_list;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserInfoBean getUserBean() {
        return JSONObject.parseObject(user, UserInfoBean.class);
    }

    public void setUserBean(UserInfoBean user) {
        this.userbean = user;
    }

    public long getComments_count() {
        return comments_count;
    }

    public void setComments_count(long comments_count) {
        this.comments_count = comments_count;
    }

    public long getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(long reposts_count) {
        this.reposts_count = reposts_count;
    }

    public long getAttitudes_count() {
        return attitudes_count;
    }

    public void setAttitudes_count(long attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public GeoBean getGeoBean() {
        return JSON.parseObject(geo, GeoBean.class);
    }

    public void setGeoBean(GeoBean geoBean) {
        this.geoBean = geoBean;
    }

    @Override
    public String toString() {
        return "StatusesBean{" +
                "created_at='" + created_at + '\'' +
                ", idstr='" + idstr + '\'' +
                ", text='" + text + '\'' +
                ", textLength=" + textLength +
                ", source_allowclick=" + source_allowclick +
                ", user=" + user +
                ", reposts_count=" + reposts_count +
                ", comments_count=" + comments_count +
                ", attitudes_count=" + attitudes_count +
                ",retweeted_status=" + retweeted_status +
                '}';
    }
}
