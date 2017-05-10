package code.vera.myblog.bean;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by vera on 2017/4/12 0012.
 */

public class CollectionBean {
    private String status;
    private String tags;
    private String favorited_time;


    private StatusesBean statusesBean;
    private List<CollectionTagBean> tagBeanList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFavorited_time() {
        return favorited_time;
    }

    public void setFavorited_time(String favorited_time) {
        this.favorited_time = favorited_time;
    }

    public StatusesBean getStatusesBean() {
        return JSON.parseObject(status,StatusesBean.class);
    }

    public void setStatusesBean(StatusesBean statusesBean) {
        this.statusesBean = statusesBean;
    }

    public List<CollectionTagBean> getTagBeanList() {
        return JSON.parseArray(tags,CollectionTagBean.class);
    }

    public void setTagBeanList(List<CollectionTagBean> tagBeanList) {
        this.tagBeanList = tagBeanList;
    }
}
