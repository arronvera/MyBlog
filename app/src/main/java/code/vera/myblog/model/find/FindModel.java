package code.vera.myblog.model.find;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.api.FindApi;
import code.vera.myblog.api.HomeApi;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.model.base.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/3/21 0021.
 */

public class FindModel implements IModel {
    @Override
    public void onAttach() {

    }
    /**
     * 获取公共分享
     */
    public void getPublic(HomeRequestBean bean, Context context, Observable.Transformer
            transformer, Subscriber<List<StatusesBean>> subscriber){
        FindApi.getPublic(bean,context)
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
