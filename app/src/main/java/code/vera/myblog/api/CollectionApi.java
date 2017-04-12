package code.vera.myblog.api;

import android.content.Context;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by vera on 2017/4/12 0012.
 */

public class CollectionApi extends BaseApi {
    /**
     * 收藏列表
     * @param context
     * @return
     */
    public static Observable<String > getFavorites(int count,int page,Context context) {
        String url = NetWorkConfig.FAVORITES_LIST;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("count", count+"");
        params.addParameters("page", page+"");
        return onGet(url, params);
    }
    /**
     * 收藏
     * @param context
     * @return
     */
    public static Observable<String > createFavorites(String wiboId, Context context) {
        String url = NetWorkConfig.CREATE_FAVORITES;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("id", wiboId);
        return onPost(url, params);
    }
    /**
     * 取消收藏
     * @param context
     * @return
     */
    public static Observable<String > destroyFavorites(String wiboId,Context context) {
        String url = NetWorkConfig.DESTROY_FAVORITES;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("id", wiboId);
        return onPost(url, params);
    }
}
