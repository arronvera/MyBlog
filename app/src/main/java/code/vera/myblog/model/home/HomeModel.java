package code.vera.myblog.model.home;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.api.HomeApi;
import code.vera.myblog.bean.UrlBean;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.model.base.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.core.Debug;
import ww.com.http.rx.RxHelper;

/**
* Created by vera on 2017/1/18 0018.
        */

public class HomeModel implements IModel{
    @Override
    public void onAttach() {
    }
    /**
     * 获取当前登录用户及其所关注（授权）用户的最新微博
     */
    public void getHomeTimeLine(HomeRequestBean bean, Context context,Observable.Transformer
                                       transformer,  Subscriber<List<StatusesBean>> subscriber){
        HomeApi.getHomeTimeLine(bean,context)
                .map(new Func1<String, List<StatusesBean>>() {
                    @Override
                    public  List<StatusesBean> call(String s) {
                        List<StatusesBean>beanList=new ArrayList<>();
                        JSONObject result= JSON.parseObject(s);
                        if (result!=null){
                          beanList= JSON.parseArray(result.getString("statuses"),StatusesBean.class);
//                            Debug.d("beanlist.size="+beanList.size());
                        }
                        return beanList;
                    }
                }).compose(RxHelper.<List<StatusesBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
    public void getUid( Context context,Observable.Transformer
            transformer,  Subscriber<Boolean> subscriber){
        HomeApi.getUid(context) .map(new Func1<String, String>() {
            @Override
            public  String call(String s) {
                String uid = "";
                JSONObject result= JSON.parseObject(s);
                if (result!=null){
                    uid=result.getString("uid");
                }
                return uid;
            }
        }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

    /**
     * 短链接转成长链接
     * @param context
     * @param url_short
     * @param subscriber
     */
    public static void shortUrlExpand( Context context,String url_short,  Subscriber<List<UrlBean> > subscriber){
        HomeApi.shortUrlExpand(context,url_short) .map(new Func1<String, List<UrlBean>>() {
            @Override
            public List<UrlBean> call(String s) {
                Debug.d(s);
                JSONObject result= JSON.parseObject(s);
                List<UrlBean>list=null;
                if (result!=null){
                    list=JSON.parseArray(result.getString("urls"),UrlBean.class);
                }
                return list;
            }
        }).compose(RxHelper.<  List<UrlBean> >cutMain())
                .subscribe(subscriber);
    }

    /**
     * 关注
     * @param context
     * @param uid
     * @param transformer
     * @param subscriber
     */
    public void createFriendShip( Context context,String uid,Observable.Transformer
            transformer,  Subscriber<String> subscriber){
        HomeApi.createFriendShip(context,uid) .map(new Func1<String, String>() {
            @Override
            public  String call(String s) {
                JSONObject result=JSON.parseObject(s);
                String id=null;
                if (result!=null){
                     id=result.getString("id");
                }
                return id;
            }
        }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

    /**
     * 取消关注
     * @param context
     * @param uid
     * @param transformer
     * @param subscriber
     */
    public void destroyFriendShip( Context context,String uid,Observable.Transformer
            transformer,  Subscriber<String> subscriber){
        HomeApi.destroyFriendShip(context,uid) .map(new Func1<String, String>() {
            @Override
            public  String call(String s) {
                JSONObject result=JSON.parseObject(s);
                String id=null;
                if (result!=null){
                    id=result.getString("id");
                }
                return id;
            }
        }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

}
