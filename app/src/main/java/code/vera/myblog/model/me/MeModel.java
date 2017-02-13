package code.vera.myblog.model.me;

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
 * Created by vera on 2017/2/8 0008.
 */

public class MeModel implements IModel {
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
}
