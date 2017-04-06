package code.vera.myblog.bean;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * 上传请求实体类
 * Created by vera on 2017/2/9 0009.
 */

public class PostBean implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;//主键
    @DatabaseField(columnName = "status")
    private String status;
    @DatabaseField(columnName = "visible")
    private int visible;//微博的可见性，0：所有人能看，1：仅自己可见，2：密友可见，3：指定分组可见，默认为0。
    @DatabaseField(columnName = "list_id")
    private String list_id;
//    @DatabaseField(columnName = "picList")
//    private ArrayList<MediaBean> picList;
    @DatabaseField(columnName = "lat")
    private double lat;
    @DatabaseField(columnName = "lon")
    private double lon;
    @DatabaseField(columnName = "annotations")
    private String annotations;
    @DatabaseField(columnName = "rip")
    private String rip;//真实ip
    @DatabaseField(columnName = "postStatus")
    private int postStatus;//状态(0-新建,1-失败,2-草稿,,3-正在发送,4-等待发送)
    @DatabaseField(columnName = "postType")
    private int postType;//类型(0-新建,1-新建评论,2-回复评论,,3-转发)

    public int getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(int postStatus) {
        this.postStatus = postStatus;
    }

    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public String getList_id() {
        return list_id;
    }

    public void setList_id(String list_id) {
        this.list_id = list_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public ArrayList<MediaBean> getPicList() {
//        return picList;
//    }
//
//    public void setPicList(ArrayList<MediaBean> picList) {
//        this.picList = picList;
//    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }
}
