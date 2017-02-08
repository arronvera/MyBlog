package code.vera.myblog.bean;

/**
 * 搜索用户实体类
 * Created by vera on 2017/2/8 0008.
 */

public class SearchUserBean {

    private String screen_name;
    private long followers_count;
    private long uid;

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public long getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(long followers_count) {
        this.followers_count = followers_count;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
