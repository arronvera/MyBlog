package code.vera.myblog.api;

import okhttp3.ResponseBody;
import rx.Observable;
import ww.com.http.OkHttpRequest;
import ww.com.http.core.AjaxParams;
import ww.com.http.core.RequestMethod;
import ww.com.http.rx.RxHelper;
import ww.com.http.rx.StringFunc;

/**
 * Created by vera on 2017/1/19 0019.
 */

public class BaseApi {
    //get请求
    public static Observable<String> onGet(String url, AjaxParams params) {

        return get(url, params)
                .compose(RxHelper.<ResponseBody>cutMain())
                .map(new StringFunc());
    }
    public static final Observable<ResponseBody> get(String url, AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.GET);
        return OkHttpRequest.newObservable(params);
    }
    //post请求
    public static Observable<String> onPost(String url, AjaxParams params) {

        return post(url, params)
                .map(new StringFunc());
    }

    /**
     * @param url    请求的地址
     * @param params 请求的参数
     * @return
     */
    public static final Observable<ResponseBody> post(String url,
                                                      AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.POST);
        return OkHttpRequest.newObservable(params);
    }
//    protected static final AjaxParams getAjaxParams(AjaxParams params) {
//        params.addHeaders("Content-Type", "multipart/form-data");
//
//        return params;
//    }
}
