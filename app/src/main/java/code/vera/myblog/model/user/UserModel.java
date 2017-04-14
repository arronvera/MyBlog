package code.vera.myblog.model.user;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.api.CollectionApi;
import code.vera.myblog.api.HomeApi;
import code.vera.myblog.api.UserApi;
import code.vera.myblog.bean.CollectionBean;
import code.vera.myblog.bean.UnReadBean;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.base.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/3/20 0020.
 */

public class UserModel implements IModel {
    @Override
    public void onAttach() {
    }
    /**
     * 获取用户信息(uid)
     * @param context
     * @param transformer
     * @param subscriber
     */
    public void getUserInfo(Context context,String uid, Observable.Transformer
            transformer, Subscriber<UserInfoBean> subscriber){
        HomeApi.getUserInfo(context,uid) .map(new Func1<String, UserInfoBean>() {
            @Override
            public  UserInfoBean call(String s) {
                UserInfoBean userInfoBean=new UserInfoBean();
                if (!TextUtils.isEmpty(s)){
                    userInfoBean= JSON.parseObject(s,UserInfoBean.class);
//                    Debug.d("userinfobean="+userInfoBean.toString());
                }
                return userInfoBean;
            }
        }).compose(RxHelper.<UserInfoBean>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

    /**
     * 获取用户信息(名字)
     * @param screen_name
     * @param transformer
     * @param subscriber
     */
    public void getUserInfoByName(Context context,String screen_name, Observable.Transformer
            transformer, Subscriber<UserInfoBean> subscriber){
        HomeApi.getUserInfoByName(context,screen_name) .map(new Func1<String, UserInfoBean>() {
            @Override
            public  UserInfoBean call(String s) {
                UserInfoBean userInfoBean=new UserInfoBean();
                if (!TextUtils.isEmpty(s)){
                    userInfoBean= JSON.parseObject(s,UserInfoBean.class);
                }
                return userInfoBean;
            }
        }).compose(RxHelper.<UserInfoBean>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

    /**
     * 获取未读消息数
     * @param context
     * @param uid
     * @param transformer
     * @param subscriber
     */
    public void getUnreadCount(Context context,String uid, Observable.Transformer
            transformer, Subscriber<UnReadBean> subscriber){
        UserApi.getUnreadCount(context,uid) .map(new Func1<String, UnReadBean>() {
            @Override
            public  UnReadBean call(String s) {
                UnReadBean unReadBean=new UnReadBean();
                if (!TextUtils.isEmpty(s)){
                    unReadBean= JSON.parseObject(s,UnReadBean.class);
                }
                return unReadBean;
            }
        }).compose(RxHelper.<UnReadBean>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
    /**
     * 获取某个用户最新发表的微博列表
     * @param context
     * @param bean
     * @param transformer
     * @param subscriber
     */
    public void getUserTimeLine(Context context, HomeRequestBean bean, Observable.Transformer
            transformer, Subscriber< List<StatusesBean>> subscriber){
        UserApi.getUserTimeLine(context,bean) .map(new Func1<String, List<StatusesBean>>() {
            @Override
            public   List<StatusesBean> call(String s) {
                List<StatusesBean>beanList=new ArrayList<>();
                JSONObject result= JSON.parseObject(s);
                if (result!=null){
                    beanList= JSON.parseArray(result.getString("statuses"),StatusesBean.class);
                }
                return beanList;
            }
        }).compose(RxHelper.< List<StatusesBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

    /**
     * 获取某个用户的粉丝
     * @param context
     * @param uid
     * @param name
     * @param transformer
     * @param subscriber
     */
    public void getUserFollowers(Context context, String uid,String name, Observable.Transformer
            transformer, Subscriber< List<UserInfoBean>> subscriber){
        UserApi.getUserFollowers(context,uid,name) .map(new Func1<String, List<UserInfoBean>>() {
            @Override
            public   List<UserInfoBean> call(String s) {
                List<UserInfoBean>beanList=new ArrayList<>();
                JSONObject result= JSON.parseObject(s);
                if (result!=null){
                    beanList= JSON.parseArray(result.getString("users"),UserInfoBean.class);
                }
                return beanList;
            }
        }).compose(RxHelper.< List<UserInfoBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
    /**
     * 获取某个关注列表
     * @param context
     * @param uid
     * @param name
     * @param transformer
     * @param subscriber
     */
    public void getUserConcernes(Context context, String uid,String name, Observable.Transformer
            transformer, Subscriber< List<UserInfoBean>> subscriber){
        UserApi.getUserConcernes(context,uid,name) .map(new Func1<String, List<UserInfoBean>>() {
            @Override
            public   List<UserInfoBean> call(String s) {
                List<UserInfoBean>beanList=new ArrayList<>();
                JSONObject result= JSON.parseObject(s);
                if (result!=null){
                    beanList= JSON.parseArray(result.getString("users"),UserInfoBean.class);
                }
                return beanList;
            }
        }).compose(RxHelper.< List<UserInfoBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
    public void getFavorites(int count,int page,Context context, Observable.Transformer
            transformer, Subscriber<List<CollectionBean>> subscriber){
        CollectionApi.getFavorites(count,page,context).map(new Func1<String, List<CollectionBean>>() {
            @Override
            public List<CollectionBean> call(String s) {
                JSONObject object= JSON.parseObject(s);
                if (object!=null){
                    List<CollectionBean>collectionBeanList= JSON.parseArray(object.getString("favorites"),CollectionBean.class);
                    return collectionBeanList;
                }
                return null;
            }
        }).compose(RxHelper.<List<CollectionBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

    public void destroyStatus(String id,Context context, Observable.Transformer
            transformer, Subscriber<Boolean> subscriber){
        UserApi.deleteStatus(context,id).map(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                JSONObject object= JSON.parseObject(s);
                if (object!=null){
                    if (object.getString("user")!=null){
                        return  true;
                    }
                }
                return false;
            }
        }).compose(RxHelper.<Boolean>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }

}
