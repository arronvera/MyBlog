package code.vera.myblog.bean;

/**
 * 链接的实体类
 * Created by vera on 2017/3/6 0006.
 */

public class UrlBean {

    /**
     * url_short : http://t.cn/h4DwT1
     * url_long : http://finance.sina.com.cn/
     * type : 0
     * result : true
     */

    private String url_short;//短链接
    private String url_long;//长链接
    private int type;//链接的类型，0：普通网页、1：视频、2：音乐、3：活动、5、投票
    private String  result;//	短链的可用状态，true：可用、false：不可用。

    public String getUrl_short() {
        return url_short;
    }

    public void setUrl_short(String url_short) {
        this.url_short = url_short;
    }

    public String getUrl_long() {
        return url_long;
    }

    public void setUrl_long(String url_long) {
        this.url_long = url_long;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
