package code.vera.myblog.api;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.bean.UploadRequestBean;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.http.core.AjaxParams;

import static code.vera.myblog.api.BaseApi.onPost;

/**
 *
 * Created by vera on 2017/2/9 0009.
 */

public class PostApi {
    /**
     *上传发布带图片
     * @param context
     * @return
     */
    public static Observable<String > uploadMsg(Context context, UploadRequestBean bean) {
        String url = NetWorkConfig.UPLOAD_WEIB;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        try {
            params.addParameters("status", URLEncoder.encode(bean.getStatus(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //微博的可见性，0：所有人能看，1：仅自己可见，2：密友可见，3：指定分组可见，默认为0。
        params.addParameters("visible", bean.getVisible()+"");
//        params.addParameters("list_id", bean.get+"");
        //要上传的图片，仅支持JPEG、GIF、PNG格式，图片大小小于5M。
        params.addParameters("pic", bean.getPic()+"");
        params.addParameters("lat", bean.getLat()+"");
        params.addParameters("long", bean.getLon()+"");
        params.addParameters("annotations", bean.getAnnotations());
        params.addParameters("rip", bean.getRip());
        return onPost(url, params);
    }
    /**
     *上传文字不带图片
     * @param context
     * @return
     */
    public static Observable<String > updateMsg(Context context, UploadRequestBean bean) {
        String url = NetWorkConfig.UPDATE_WEIB;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        try {
            params.addParameters("status", URLEncoder.encode(bean.getStatus(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //微博的可见性，0：所有人能看，1：仅自己可见，2：密友可见，3：指定分组可见，默认为0。
        params.addParameters("visible", bean.getVisible()+"");
//        params.addParameters("list_id", bean.getList_id()+"");
        params.addParameters("lat", bean.getLat()+"");
        params.addParameters("long", bean.getLon()+"");
        params.addParameters("annotations", bean.getAnnotations());
        params.addParameters("rip", bean.getRip());
        return onPost(url, params);
    }
}
