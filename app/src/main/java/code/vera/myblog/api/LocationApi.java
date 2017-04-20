package code.vera.myblog.api;

import android.content.Context;
import android.text.TextUtils;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.http.core.AjaxParams;

import static code.vera.myblog.api.BaseApi.onGet;

/**
 * Created by vera on 2017/4/20 0020.
 */

public class LocationApi {
    public static Observable<String> getNearByPlaces(double lat, double lon, String q,
                                                     int count, int page,
                                                     Context context) {
        String url = NetWorkConfig.NEARBY_PLACES;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("lat", lat + "");
        params.addParameters("long", lon + "");
        if (!TextUtils.isEmpty(q)){
            params.addParameters("q", q);
        }
        params.addParameters("count", count + "");
        params.addParameters("page", page + "");
        return onGet(url, params);
    }
}
