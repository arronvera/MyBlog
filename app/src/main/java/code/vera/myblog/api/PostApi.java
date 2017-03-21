package code.vera.myblog.api;

import android.content.Context;

import java.io.File;
import java.util.List;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.bean.CommentRequestBean;
import code.vera.myblog.bean.PostBean;
import code.vera.myblog.config.NetWorkConfig;
import rx.Observable;
import ww.com.http.core.AjaxParams;

import static code.vera.myblog.api.BaseApi.onGet;
import static code.vera.myblog.api.BaseApi.onPost;

/**
 *
 * Created by vera on 2017/2/9 0009.
 */

public class PostApi {
    /**
     *上传发布带图片
     * @param context
     * @param pictureList
     * @return
     */
    public static Observable<String > uploadMsg(Context context, PostBean bean, List<MediaBean> pictureList) {
        String url = NetWorkConfig.UPLOAD_WEIB;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
//        try {
//            params.addParameters("status", URLEncoder.encode(bean.getStatus(),"utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        params.addParameters("status", bean.getStatus());
        //微博的可见性，0：所有人能看，1：仅自己可见，2：密友可见，3：指定分组可见，默认为0。
        params.addParameters("visible", bean.getVisible()+"");
//        params.addParameters("list_id", bean.get+"");
        //要上传的图片，仅支持JPEG、GIF、PNG格式，图片大小小于5M。
//        params.addParameters("pic", bean.getPic()+"");
        if (pictureList!=null){
            for (int i=0;i<pictureList.size();i++){
                params.addParametersJPG("pic",new File(pictureList.get(i).getOriginalPath()));
            }
        }
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
    public static Observable<String > updateMsg(Context context, PostBean bean) {
        String url = NetWorkConfig.UPDATE_WEIB;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
//        try {
//            params.addParameters("status", URLEncoder.encode(bean.getStatus(),"utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        params.addParameters("status", bean.getStatus());

        //微博的可见性，0：所有人能看，1：仅自己可见，2：密友可见，3：指定分组可见，默认为0。
        params.addParameters("visible", bean.getVisible()+"");
//        params.addParameters("list_id", bean.getList_id()+"");
        params.addParameters("lat", bean.getLat()+"");
        params.addParameters("long", bean.getLon()+"");
        params.addParameters("annotations", bean.getAnnotations());
        params.addParameters("rip", bean.getRip());
        return onPost(url, params);
    }

    /**
     * 评论
     * @param context
     * @param bean
     * @return
     */
    public static Observable<String > commentMsg(Context context, CommentRequestBean bean) {
        String url = NetWorkConfig.COMMENT_INFO;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
//        try {
//            params.addParameters("comment", URLEncoder.encode(bean.getComment(),"utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        params.addParameters("comment", bean.getComment());
        params.addParameters("id", bean.getId()+"");
        params.addParameters("comment_ori", bean.getComment_ori()+"");
        params.addParameters("rip", bean.getRip());
        return onPost(url, params);
    }

    /**
     * 获取评论列表
     * @param context
     * @param id
     * @return
     */
    public static Observable<String > getComments(Context context, long id) {
        String url = NetWorkConfig.COMMENT_INFO_BY_ID;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("id", id+"");
//        params.addParameters("since_id", "");
//        params.addParameters("max_id", "");
        //  todo
        params.addParameters("count", "20");
        params.addParameters("page","1");
        params.addParameters("filter_by_author","0");//	作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
        return onGet(url, params);
    }
    public static  Observable<String > getFriendShip(Context context) {
        String url = NetWorkConfig.GET_FRIENDSHIP;
        AjaxParams params = new AjaxParams();
        params.addParameters("access_token", AccessTokenKeeper.readAccessToken(context).getToken());
        params.addParameters("uid", AccessTokenKeeper.readAccessToken(context).getUid());
        params.addParameters("count", "50");
        params.addParameters("page","1");
        params.addParameters("sort","0");//	排序
        return onGet(url, params);
    }
}
