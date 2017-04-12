package code.vera.myblog.config;

/**
 * Created by vera on 2017/1/18 0018.
 */

public class NetWorkConfig {

    public static final String BASE_URL="https://api.weibo.com/2";
    //获取当前登录用户及其所关注（授权）用户的最新微博
    public static final String HOME_TIME_LING=BASE_URL+"/statuses/home_timeline.json";
    //获取双向关注的分享列表
    public static final String BILATERAL_TIME_LING=BASE_URL+"/statuses/bilateral_timeline.json";
    //获取公共列表
    public static final String PUBLIC_TIME_LING=BASE_URL+"/statuses/public_timeline.json";
    //获取某个用户最新发表的微博列表
    public static final String USER_TIME_LING=BASE_URL+"/statuses/user_timeline.json";
    //获取uid
    public static final String USER_UID=BASE_URL+"/account/get_uid.json";
    //用户信息
    public static final String USER_INFO=BASE_URL+"/users/show.json";
    //搜索用户时的联想搜索建议
    public static final String SEARCH_USER=BASE_URL+"/search/suggestions/users.json";
    //话题下的所有信息
    public static final String SEARCH_GET_TOPICS=BASE_URL+"/search/topics.json";
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
    public static final String COMMENT_TO_ME=BASE_URL+ "/comments/to_me.json";
    //获取定微博的转发微博列表
    public static final String REPOSTS_INFO=BASE_URL+  "/statuses/repost_timeline.json";


    //获取双向关注列表，即互粉列表
    public static final String GET_FRIENDSHIP=BASE_URL+"/friendships/friends/bilateral.json";
    //关注一个用户
    public static final String CREATE_FRIENDSHIP=BASE_URL+"/friendships/friends/create.json";
    //取消关注一个用户
    public static final String DESTROY_FRIENDSHIP=BASE_URL+"/friendships/friends/destroy.json";

    //获取粉丝列表
    public static final String GET_FOLLOWERES=BASE_URL+"/friendships/followers.json";
    //获取关注列表
    public static final String GET_CONCERNES=BASE_URL+"/friendships/friends.json";

    //短链接还原成原始的长链接
    public static final String SHORT_URL_EXPAND=BASE_URL+"/short_url/expand.json";

    //未读数量
    public static final String UNREAD_COUNT="https://rm.api.weibo.com/2/remind/unread_count.json";
    //对未读消息进行清零
    public static final String CLEAR_UNREAD_COUNT="https://rm.api.weibo.com/2/remind/set_count.json";

    //收藏
    public static final String CREATE_FAVORITES=BASE_URL+"/favorites/create.json";
    //取消收藏
    public static final String DESTROY_FAVORITES=BASE_URL+"/favorites/destroy.json";
    //收藏列表
    public static final String FAVORITES_LIST=BASE_URL+"/favorites.json";
}
