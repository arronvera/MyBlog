package code.vera.myblog.api;

import android.content.Context;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.http.core.AjaxParams;

import static code.vera.myblog.api.BaseApi.onGet;

/**
 * Created by vera on 2017/2/8 0008.
 */

public class SearchApi {
    /**
     * 搜索用户
     * @param q
     * @param context
     * @return
     */
    public static Observable<String > searchUsers(String  q, Context context) {
        String url = NetWorkConfig.SEARCH_USER;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        //搜索的关键字
        params.addParameters("q",q);
        //条数 默认为10
        params.addParameters("count","10");
        return onGet(url, params);
    }
}
