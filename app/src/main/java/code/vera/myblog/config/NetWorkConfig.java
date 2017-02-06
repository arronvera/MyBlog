package code.vera.myblog.config;

/**
 * Created by vera on 2017/1/18 0018.
 */

public class NetWorkConfig {

    public static final String BASE_URL="https://api.weibo.com/2";
    //获取当前登录用户及其所关注（授权）用户的最新微博
    public static final String HOME_TIME_LING=BASE_URL+"/statuses/home_timeline.json";
}
