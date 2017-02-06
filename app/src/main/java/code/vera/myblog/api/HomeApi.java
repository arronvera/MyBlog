package code.vera.myblog.api;

import android.content.Context;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by vera on 2017/1/19 0019.
 */

public class HomeApi  extends BaseApi{

    public static Observable<String > getHomeTimeLine(HomeRequestBean bean, Context context) {
        String url = NetWorkConfig.HOME_TIME_LING;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("count",bean.getCount());
        params.addParameters("page",bean.getPage());
        return onGet(url, params);
    }


}
