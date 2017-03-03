package code.vera.myblog.bean.home;

/**
 * Created by vera on 2017/1/25 0025.
 */

public class HomeRequestBean {
    public String count="15";//单页返回的记录条数，设置为10
    public String page="1";//	返回结果的页码，默认为1。
    private int base_app;//是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
    private int feature;//	过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
    private String uid;
    private String screen_name;

    public int getFeature() {
        return feature;
    }

    public void setFeature(int feature) {
        this.feature = feature;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getBase_app() {
        return base_app;
    }

    public void setBase_app(int base_app) {
        this.base_app = base_app;
    }

    @Override
    public String toString() {
        return "HomeRequestBean{" +
                "count='" + count + '\'' +
                ", page='" + page + '\'' +
                ", base_app=" + base_app +
                '}';
    }
}
