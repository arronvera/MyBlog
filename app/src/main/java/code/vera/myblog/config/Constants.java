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

    //--------------发送状态status-------------------(0-新建,1-失败,2-草稿,,3-正在发送,4-等待发送)
    public static final int POST_STATUS_NEW=0;//新建
    public static final int POST_STATUS_FAILED=1;//失败
    public static final int POST_STATUS_DRAFT=2;//草稿
    public static final int POST_STATUS_SENDING=3;//正在发送
    public static final int POST_STATUS_WAITING=4;//等待发送

    //--------------发送类型type-------------------(0-新建信息,1-新建评论,,2-转发)
    public static final int POST_TYPE_NEW=0;//新建信息
    public static final int POST_TYPE_COMMENT=1;//新建评论
    public static final int POST_TYPE_REPOST=2;//转发
    public static final int POST_TYPE_REPLY_COMMENT=3;//回复评论

    //--------------关注和粉丝-------------------
    public static final int TYPE_CONCERN=1001;//关注
    public static final int TYPE_FOLLOWERES=1002;//粉丝

    //--------------可见-------------------
    public static final int VISIBLE_ALL=0;//公开
    public static final int VISIBLE_SELF=1;//自己
    public static final int VISIBLE_FRIENDS=2;//朋友圈
    //-----------------权限----------------
    public static final int LINK_TYPE_WEBSITE=0;//公开
    public static final int LINK_TYPE_VIDEO=1;//朋友圈
    public static final int LINK_TYPE_MUSIC=2;//自己


    //-------过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0----------
    public static final int FILTER_ALL=0;
    public static final int FILTER_ORIGI=1;
    public static final int FILTER_PHOTO=2;
    public static final int FILTER_MOVIE=3;
    public static final int FILTER_MUSIC=4;

    public static final int POST_SEND_DRAFT=4;//发送草稿

}
