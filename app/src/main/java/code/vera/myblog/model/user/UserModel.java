package code.vera.myblog.model.user;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import code.vera.myblog.api.HomeApi;
import code.vera.myblog.api.UserApi;
import code.vera.myblog.bean.UnReadBean;
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
     * 获取用户信息
     * @param context
     * @param transformer
     * @param subscriber
     */
    public void getUserInfo(Context context, Observable.Transformer
            transformer, Subscriber<UserInfoBean> subscriber){
        HomeApi.getUserInfo(context,"") .map(new Func1<String, UserInfoBean>() {
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
}
