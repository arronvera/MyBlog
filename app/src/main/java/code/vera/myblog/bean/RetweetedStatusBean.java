package code.vera.myblog.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * 原微博
 * Created by vera on 2017/2/14 0014.
 */

public class RetweetedStatusBean implements Serializable{
    private String created_at;//创建时间
    private long id;//微博ID
    private String text;//文字
    private int textLength; //文字长度
    private String user;
    private UserBean userbean;
    private String pic_urls;    //配图
    private List<PicBean> pic_list;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public UserBean getUserbean() {
        return  JSONObject.parseObject(user, UserBean.class);
    }

    public void setUserbean(UserBean userbean) {
        this.userbean = userbean;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextLength() {
        return textLength;
    }

    public void setTextLength(int textLength) {
        this.textLength = textLength;
    }
}
