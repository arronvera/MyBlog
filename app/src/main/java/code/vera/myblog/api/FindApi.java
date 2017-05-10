package code.vera.myblog.api;

import android.content.Context;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.bean.HomeRequestBean;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.core.Debug;
import ww.com.http.core.AjaxParams;

/**
 * Created by vera on 2017/3/21 0021.
 */

public class FindApi extends BaseApi {
    /**
     * 获取当前登录用户及其所关注（授权）用户的最新微博
     * @param bean
     * @param context
     * @return
     */
    public static Observable<String > getPublic(HomeRequestBean bean, Context context) {
        String url = NetWorkConfig.PUBLIC_TIME_LING;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        Debug.d("token="+ AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("count",bean.getCount());
        params.addParameters("page",bean.getPage());
        return onGet(url, params);
    }
}
