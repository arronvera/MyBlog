package code.vera.myblog.config;

/**
 * Created by vera on 2017/1/12 0012.
 */

public class Constants {
    public static final String APP_KEY      = "543417955";		   // 应用的APP_KEY
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";// 应用的回调页
    public static final String SCOPE = 							   // 应用申请的高级权限
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
    public static final int COMMENT_TYPE=1;
    public static final int REPOST_TYPE=2;

    //--------------发送状态status-------------------(0-新建,1-失败,2-草稿,,3-正在发送,4-等待发送)
    public static final int POST_STATUS_NEW=0;//新建
    public static final int POST_STATUS_FAILED=1;//失败
    public static final int POST_STATUS_DRAFT=2;//草稿
    public static final int POST_STATUS_SENDING=3;//正在发送
    public static final int POST_STATUS_WAITING=4;//等待发送

    //--------------发送类型type-------------------(0-新建信息,1-新建评论,2-回复评论,,3-转发)
    public static final int POST_TYPE_NEW=0;//新建信息
    public static final int POST_TYPE_NEW_COMMENT=1;//新建评论
    public static final int POST_TYPE_REPLY_COMMENT=2;//回复评论
    public static final int POST_TYPE_REPOST=3;//转发



    public static final int TAB_REPOST=0;
    public static final int TAB_COMMENT=1;
    public static final int TAB_LIKE=2;

}
