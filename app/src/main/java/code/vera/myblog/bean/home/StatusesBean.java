package code.vera.myblog.bean.home;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by vera on 2017/1/19 0019.
 */

public class StatusesBean {
    private String created_at;//创建时间
    private long id;
    private String mid;
    private String idstr;
    private String text;//文字
    private int textLength; //文字长度
    private int  source_allowclick;//来源是否可以点击
    private String source;//来源
    //配图
    private String pic_urls;
    private List<PicBean> pic_list;//

    private String user;
    private UserBean userbean;
    private int reposts_count;//	转发数
    private int comments_count;//评论数
    private int attitudes_count;//表态数

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
        return JSONArray.parseArray(pic_urls,PicBean.class);
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

//    public String getMid() {
//        return mid;
//    }
//
//    public void setMid(String mid) {
//        this.mid = mid;
//    }

//    public String getIdstr() {
//        return idstr;
//    }
//
//    public void setIdstr(String idstr) {
//        this.idstr = idstr;
//    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public int getTextLength() {
//        return textLength;
//    }
//
//    public void setTextLength(int textLength) {
//        this.textLength = textLength;
//    }
//
//    public int getSource_allowclick() {
//        return source_allowclick;
//    }
//
//    public void setSource_allowclick(int source_allowclick) {
//        this.source_allowclick = source_allowclick;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public List<PicBean> getPic_urls() {
//        return pic_urls;
//    }
//
//    public void setPic_urls(List<PicBean> pic_urls) {
//        this.pic_urls = pic_urls;
//    }

    public UserBean getUserBean() {
        return JSONObject.parseObject(user, UserBean.class);
    }

    public void setUserBean(UserBean user) {
        this.userbean = user;
    }

//    public int getReposts_count() {
//        return reposts_count;
//    }
//
//    public void setReposts_count(int reposts_count) {
//        this.reposts_count = reposts_count;
//    }
//
//    public int getComments_count() {
//        return comments_count;
//    }
//
//    public void setComments_count(int comments_count) {
//        this.comments_count = comments_count;
//    }
//
//    public int getAttitudes_count() {
//        return attitudes_count;
//    }
//
//    public void setAttitudes_count(int attitudes_count) {
//        this.attitudes_count = attitudes_count;
//    }

    @Override
    public String toString() {
        return "StatusesBean{" +
                "created_at='" + created_at + '\'' +
                ", id=" + id +
                ", mid='" + mid + '\'' +
                ", idstr='" + idstr + '\'' +
                ", text='" + text + '\'' +
                ", textLength=" + textLength +
                ", source_allowclick=" + source_allowclick +
//                ", source='" + source + '\'' +
                ", user=" + user +
                ", reposts_count=" + reposts_count +
                ", comments_count=" + comments_count +
                ", attitudes_count=" + attitudes_count +
                '}';
    }
}
