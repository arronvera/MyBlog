package code.vera.myblog.bean.home;

/**
 * Created by vera on 2017/1/25 0025.
 */

public class HomeRequestBean {
    private String access_token;
    private String count="10";//单页返回的记录条数，设置为10
    private String page="1";//	返回结果的页码，默认为1。
    private int base_app;//是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。

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
}
