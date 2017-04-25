package code.vera.myblog.api;

import android.content.Context;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.config.NetWorkConfig;
import code.vera.myblog.utils.IpUtils;
import rx.Observable;
import ww.com.core.Debug;
import ww.com.http.core.AjaxParams;

/**
 * Created by vera on 2017/1/19 0019.
 */

public class HomeApi extends BaseApi {
    /**
     * 获取当前登录用户及其所关注（授权）用户的最新微博
     *
     * @param bean
     * @param context
     * @return
     */
    public static Observable<String> getHomeTimeLine(HomeRequestBean bean, Context context) {
        String url = NetWorkConfig.HOME_TIME_LING;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        Debug.d("token=" + AccessTokenKeeper.readAccessToken(context).getToken());
//        Debug.d("uid="+ AccessTokenKeeper.readAccessToken(context).getUid());
        params.addParameters("count", bean.getCount());
        params.addParameters("page", bean.getPage());
        return onGet(url, params);
    }

    /**
     * 获取双向关注用户的最新微博
     *
     * @param bean
     * @param context
     * @return
     */
    public static Observable<String> getBilateralTimeLine(HomeRequestBean bean, Context context) {
        String url = NetWorkConfig.BILATERAL_TIME_LING;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("count", bean.getCount());
        params.addParameters("page", bean.getPage());
        return onGet(url, params);
    }

    /**
     * 某个用户最新发表的微博列表
     *
     * @param bean
     * @param context
     * @return
     */
    public static Observable<String> getUserTimeLine(HomeRequestBean bean, Context context) {
        String url = NetWorkConfig.HOME_TIME_LING;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        Debug.d("token=" + AccessTokenKeeper.readAccessToken(context).getToken());
//        Debug.d("uid="+ AccessTokenKeeper.readAccessToken(context).getUid());
        params.addParameters("count", bean.getCount());
        params.addParameters("page", bean.getPage());
        params.addParameters("feature", bean.getFeature() + "");
        params.addParameters("uid", bean.getUid());
        return onGet(url, params);
    }

    /**
     * 获取用户uid
     *
     * @param context
     * @return
     */
    public static Observable<String> getUid(Context context) {
        String url = NetWorkConfig.USER_UID;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        return onGet(url, params);
    }

    public static Observable<String> getUserInfo(Context context, String uid) {
        String url = NetWorkConfig.USER_INFO;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        if (TextUtils.isEmpty(uid)) {//如果u传入的uid为空，则获取自身的信息
            params.addParameters("uid", AccessTokenKeeper.readAccessToken(context).getUid());
        } else {
            params.addParameters("uid", uid);
        }
        //uid和screen_nam二选一
        //params.addParameters("",)
        return onGet(url, params);
    }

    public static Observable<String> getUserInfoByName(Context context, String screen_name) {
        String url = NetWorkConfig.USER_INFO;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("screen_name", screen_name);

        return onGet(url, params);
    }

    /**
     * 短链接变成长链接
     *
     * @param context
     * @param url_short
     * @return
     */
    public static Observable<String> shortUrlExpand(Context context, String url_short) {
        String url = NetWorkConfig.SHORT_URL_EXPAND;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        try {
            params.addParameters("url_short", URLEncoder.encode(url_short, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return onGet(url, params);
    }

    /**
     * 关注
     *
     * @param context
     * @param uid
     * @return
     */
    public static Observable<String> createFriendShip(Context context, String uid) {
        String url = NetWorkConfig.CREATE_FRIENDSHIP;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("uid", uid);
        params.addParameters("rip", IpUtils.getHostIp());
        return onGet(url, params);
    }

    /**
     * 取消关注
     *
     * @param context
     * @param uid
     * @return
     */
    public static Observable<String> destroyFriendShip(Context context, String uid) {
        String url = NetWorkConfig.DESTROY_FRIENDSHIP;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("uid", uid);
        params.addParameters("rip", IpUtils.getHostIp());
        return onGet(url, params);
    }

    /**
     * 获取某一话题下的分享信息
     *
     * @param q
     * @param context
     * @return
     */
    public static Observable<String> getTopicStatus(String q, Context context) {
        String url = NetWorkConfig.SEARCH_GET_TOPICS;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("q", q);
        return onGet(url, params);
    }

    /**
     * 清空未读
     *
     * @param q
     * @param context
     * @return
     */
    public static Observable<String> clearUnread(Context context) {
        String url = NetWorkConfig.CLEAR_UNREAD_COUNT;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        return onGet(url, params);
    }

    /**
     * 收藏
     *
     * @param context
     * @return
     */
    public static Observable<String> createFavorites(String wiboId, Context context) {
        String url = NetWorkConfig.CREATE_FAVORITES;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("id", wiboId);
        return onPost(url, params);
    }

    /**
     * 取消收藏
     *
     * @param context
     * @return
     */
    public static Observable<String> destroyFavorites(String wiboId, Context context) {
        String url = NetWorkConfig.DESTROY_FAVORITES;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("id", wiboId);
        return onPost(url, params);
    }

    /**
     * 分享
     *
     * @param context
     * @return
     */
    public static Observable<String> share(String status, List<String> pic,
                                           Context context) {
        String url = NetWorkConfig.SHARE_STATUS;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("status", status);
        if (pic != null) {
            for (int i = 0; i < pic.size(); i++) {
                params.addParameters("pic", pic.get(i));
            }
        }
        return onPost(url, params);
    }
}
