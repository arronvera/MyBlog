package code.vera.myblog.api;

import android.content.Context;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.http.core.AjaxParams;

import static code.vera.myblog.api.BaseApi.onGet;

/**
 * Created by vera on 2016/12/19 0019.
 */

public class UserApi  {
    public static Observable<String> getUnreadCount(Context context, String uid){
        String url = NetWorkConfig.UNREAD_COUNT;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("uid",uid);
        return onGet(url, params);
    }
    public static Observable<String> getUserTimeLine(Context context, HomeRequestBean bean){
        String url = NetWorkConfig.USER_TIME_LING;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("uid",bean.getUid());
        params.addParameters("count","15");
        params.addParameters("feature",bean.getFeature()+"");
        return onGet(url, params);
    }
}
