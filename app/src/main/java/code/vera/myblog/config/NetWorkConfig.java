package code.vera.myblog.config;

/**
 * Created by vera on 2017/1/18 0018.
 */

public class NetWorkConfig {

    public static final String BASE_URL="https://api.weibo.com/2";
    //获取当前登录用户及其所关注（授权）用户的最新微博
    public static final String HOME_TIME_LING=BASE_URL+"/statuses/home_timeline.json";
    //获取uid
    public static final String USER_UID=BASE_URL+"/account/get_uid.json";
    //用户信息
    public static final String USER_INFO=BASE_URL+"/users/show.json";
    //搜索用户时的联想搜索建议
    public static final String SEARCH_USER=BASE_URL+"/search/suggestions/users.json";

    //转发一条微博
    public static final String REPOST_WEIB=BASE_URL+"/statuses/repost.json";
    //发布上传（带图片）
    public static final String UPLOAD_WEIB=BASE_URL+"/statuses/upload.json";
    //发布上传（不带图片）
    public static final String UPDATE_WEIB=BASE_URL+"/statuses/update.json";

    //评论
    public static final String COMMENT_INFO=BASE_URL+"/comments/create.json";
    //根据ID返回某条微博的评论列表
    public static final String COMMENT_INFO_BY_ID=BASE_URL+  "/comments/show.json";
    //@我的评论
    public static final String COMMENT_MENTION=BASE_URL+ "/comments/mentions.json";
    //发出的评论列表
    public static final String COMMENT_BY_ME=BASE_URL+ "/comments/by_me.json";
    //接收到的评论列表
    public static final String COMMENT_TO_ME=BASE_URL+ "comments/to_me.json";
    //获取定微博的转发微博列表
    public static final String REPOSTS_INFO=BASE_URL+  "/statuses/repost_timeline.json";
}
