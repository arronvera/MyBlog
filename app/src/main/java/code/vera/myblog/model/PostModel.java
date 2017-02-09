package code.vera.myblog.model;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import code.vera.myblog.api.PostApi;
import code.vera.myblog.bean.CommentRequestBean;
import code.vera.myblog.bean.UploadRequestBean;
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
     * @param transformer
     * @param subscriber
     */
    public void uploadMessage(Context context,UploadRequestBean bean, Observable.Transformer
            transformer, Subscriber<String> subscriber){
        PostApi.uploadMsg(context,bean) .map(new Func1<String, String>() {
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
    public void updateMessage(Context context,UploadRequestBean bean, Observable.Transformer
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
}
