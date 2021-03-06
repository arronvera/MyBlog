package code.vera.myblog.model;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import code.vera.myblog.api.PostApi;
import code.vera.myblog.bean.CommentRequestBean;
import code.vera.myblog.bean.GeoBean;
import code.vera.myblog.bean.PostBean;
import code.vera.myblog.model.base.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.core.Debug;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class PostModel implements IModel {
    @Override
    public void onAttach() {

    }

    /**
     * 发布文字带图片
     * @param context
     * @param bean
     * @param pictureList
     * @param transformer
     * @param subscriber
     */
    public void uploadMessage(Context context, PostBean bean, List<MediaBean> pictureList, Observable.Transformer
            transformer, Subscriber<String> subscriber){
        PostApi.uploadMsg(context,bean,pictureList) .map(new Func1<String, String>() {
            @Override
            public  String call(String s) {
                Debug.d(s);
                return null;
            }
        }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
    /**
     * 发布文字不带图片
     * @param context
     * @param bean
     * @param transformer
     * @param subscriber
     */
    public void updateMessage(Context context, PostBean bean, Observable.Transformer
            transformer, Subscriber<String> subscriber){
            PostApi.updateMsg(context,bean) .map(new Func1<String, String>() {
            @Override
            public  String call(String s) {
                JSONObject result= JSON.parseObject(s);
                if (result!=null){
                    if (TextUtils.isEmpty(result.getString("error"))){
                        long id=result.getLong("id");
                        return id+"";
                    }else
                        return null;
                }
                return null;
            }
        }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

    /**
     * 创建一条评论
     * @param context
     * @param bean
     * @param transformer
     * @param subscriber
     */
    public void commentMessage(Context context, CommentRequestBean bean, Observable.Transformer
            transformer, Subscriber<String> subscriber){
        PostApi.commentMsg(context,bean) .map(new Func1<String, String>() {
            @Override
            public  String call(String s) {
                JSONObject result= JSON.parseObject(s);
                if (result!=null){
                    if (TextUtils.isEmpty(result.getString("error"))){
                        long id=result.getLong("id");
                        return id+"";
                    }else
                        return null;
                }
                return null;
            }
        }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
    /**
     * 转发
     * @param context
     * @param id
     * @param transformer
     * @param subscriber
     */
    public void repostMessage(Context context, String id,String status, Observable.Transformer
            transformer, Subscriber<String> subscriber){
        PostApi.repost(context,id,status) .map(new Func1<String, String>() {
            @Override
            public  String call(String s) {
                JSONObject result= JSON.parseObject(s);
                if (result!=null){
                    if (TextUtils.isEmpty(result.getString("error"))){
                        long id=result.getLong("id");
                        return id+"";
                    }else
                        return null;
                }
                return null;
            }
        }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

    /**
     * gps
     * @param context
     * @param transformer
     * @param subscriber
     */
    public void gpsToOffSet(Context context,double longitude,double latitude,Observable.Transformer
            transformer, Subscriber<List<GeoBean>> subscriber){
        PostApi.gpsToOffset(context,longitude,latitude) .map(new Func1<String, List<GeoBean>>() {
            @Override
            public  List<GeoBean> call(String s) {
                JSONObject result= JSON.parseObject(s);
                List<GeoBean> geoBeanList=JSON.parseArray(result.getString("geos"),GeoBean.class);
                return geoBeanList;
            }
        }).compose(RxHelper.<List<GeoBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }


    /**
     * 回复
     * @param context
     * @param id
     * @param transformer
     * @param subscriber
     */
    public void reply(Context context,String cid, String id,String comment,int comment_ori, Observable.Transformer
            transformer, Subscriber<String> subscriber){
        PostApi.reply(context,cid,id,comment,comment_ori) .map(new Func1<String, String>() {
            @Override
            public  String call(String s) {
                JSONObject result= JSON.parseObject(s);
                if (result!=null){
                    if (TextUtils.isEmpty(result.getString("error"))){
                        long id=result.getLong("id");
                        return id+"";
                    }else
                        return null;
                }
                return null;
            }
        }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
}
