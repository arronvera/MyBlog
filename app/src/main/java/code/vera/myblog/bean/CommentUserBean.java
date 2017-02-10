package code.vera.myblog.bean;

import com.alibaba.fastjson.JSON;

import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.bean.home.UserBean;

/**
 * 评论实体类
 * Created by vera on 2017/2/10 0010.
 */

public class CommentUserBean {
    private String created_at;
    private long id;
    private String text;
    private String source;
    private String mid;
    private String user;
    private UserBean userBean;//评论作者的用户信息字段
    private String idstr;
    private String status;
    private StatusesBean statusesBean;
//    private String reply_comment;//评论来源评论，当本评论属于对另一评论的回复时返回此字段


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StatusesBean getStatusesBean() {
        return JSON.parseObject(status,StatusesBean.class);
    }

    public void setStatusesBean(StatusesBean statusesBean) {
        this.statusesBean = statusesBean;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public UserBean getUserBean() {
        return JSON.parseObject(user,UserBean.class);
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
