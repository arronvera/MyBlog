package code.vera.myblog.model;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import code.vera.myblog.api.HomeApi;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.base.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/2/7 0007.
 */

public class PersonalityModel implements IModel {
    @Override
    public void onAttach() {

    }

    /**
     * 获取用户信息(名字)
     * @param screen_name
     * @param transformer
     * @param subscriber
     */
    public void getUserInfoByName(Context context, String screen_name, Observable.Transformer
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
     * 关注
     * @param context
     * @param uid
     * @param transformer
     * @param subscriber
     */
    public void createFriendShip(Context context, String uid, Observable.Transformer
            transformer, Subscriber<UserInfoBean> subscriber){
        HomeApi.createFriendShip(context,uid) .map(new Func1<String, UserInfoBean>() {
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
}
