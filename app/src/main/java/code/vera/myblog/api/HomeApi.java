package code.vera.myblog.api;

import android.content.Context;
import android.text.TextUtils;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.core.Debug;
import ww.com.http.core.AjaxParams;

/**
 * Created by vera on 2017/1/19 0019.
 */

public class HomeApi  extends BaseApi{
    /**
     * 获取当前登录用户及其所关注（授权）用户的最新微博
     * @param bean
     * @param context
     * @return
     */
    public static Observable<String > getHomeTimeLine(HomeRequestBean bean, Context context) {
        String url = NetWorkConfig.HOME_TIME_LING;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        Debug.d("token="+ AccessTokenKeeper.readAccessToken(context).getToken());
//        Debug.d("uid="+ AccessTokenKeeper.readAccessToken(context).getUid());
        params.addParameters("count",bean.getCount());
        params.addParameters("page",bean.getPage());
        return onGet(url, params);
    }

    /**
     * 某个用户最新发表的微博列表
     * @param bean
     * @param context
     * @return
     */
    public static Observable<String > getUserTimeLine(HomeRequestBean bean, Context context) {
        String url = NetWorkConfig.HOME_TIME_LING;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        Debug.d("token="+ AccessTokenKeeper.readAccessToken(context).getToken());
//        Debug.d("uid="+ AccessTokenKeeper.readAccessToken(context).getUid());
        params.addParameters("count",bean.getCount());
        params.addParameters("page",bean.getPage());
        params.addParameters("feature",bean.getFeature()+"");
        params.addParameters("uid",bean.getUid());
        return onGet(url, params);
    }
    /**
     * 获取用户uid
     * @param context
     * @return
     */
    public static Observable<String > getUid(Context context) {
        String url = NetWorkConfig.USER_UID;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        return onGet(url, params);
    }
    public static Observable<String>getUserInfo(Context context,String uid){
        String url = NetWorkConfig.USER_INFO;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        if (TextUtils.isEmpty(uid)){//如果u传入的uid为空，则获取自身的信息
            params.addParameters("uid", AccessTokenKeeper.readAccessToken(context).getUid());
        }else {
            params.addParameters("uid", uid);
        }
        //uid和screen_nam二选一
        //params.addParameters("",)
        return onGet(url, params);
    }
    public static Observable<String>getUserInfoByName(Context context,String screen_name){
        String url = NetWorkConfig.USER_INFO;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("screen_name",screen_name);

        return onGet(url, params);
    }




}
