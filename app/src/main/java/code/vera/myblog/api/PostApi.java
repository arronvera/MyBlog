package code.vera.myblog.api;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.http.core.AjaxParams;

import static code.vera.myblog.api.BaseApi.onGet;

/**
 *
 * Created by vera on 2017/2/9 0009.
 */

public class PostApi {
    /**
     *上传发布
     * @param context
     * @return
     */
    public static Observable<String > UploadMsg(Context context,String status,int visible) {
        String url = NetWorkConfig.USER_UID;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        try {
            params.addParameters("status", URLEncoder.encode(status,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //微博的可见性，0：所有人能看，1：仅自己可见，2：密友可见，3：指定分组可见，默认为0。
        params.addParameters("visible", visible+"");
//        params.addParameters("list_id", list_id+"");
        //要上传的图片，仅支持JPEG、GIF、PNG格式，图片大小小于5M。
        params.addParameters("pic", visible+"");

        return onGet(url, params);
    }
}
