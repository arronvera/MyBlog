package code.vera.myblog.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import code.vera.myblog.api.HomeApi;
import code.vera.myblog.model.base.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class CommentDetailModel implements IModel {
    @Override
    public void onAttach() {

    }
    /**
     * 收藏
     * @param context
     * @param transformer
     * @param subscriber
     */
    public void createFavorites(String weiboId, Context context, Observable.Transformer
            transformer, Subscriber<String> subscriber){
        HomeApi.createFavorites(weiboId,context) .map(new Func1<String, String>() {
            @Override
            public  String call(String s) {
                JSONObject result= JSON.parseObject(s);
                if (result.getString("error")!=null){
                    return null;
                }
                String time=result.getString("favorited_time");
                return time;
            }
        }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
    /**
     * 取消收藏
     * @param context
     * @param transformer
     * @param subscriber
     */
    public void destroyFavorites( String weiboId,Context context,Observable.Transformer
            transformer,  Subscriber<String> subscriber){
        HomeApi.destroyFavorites(weiboId,context) .map(new Func1<String, String>() {
            @Override
            public  String call(String s) {
                JSONObject result=JSON.parseObject(s);
                if (result.getString("error")!=null){
                    return null;
                }
                String time=result.getString("favorited_time");
                return time;
            }
        }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

}
