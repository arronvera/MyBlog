package code.vera.myblog.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import code.vera.myblog.api.PostApi;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.model.base.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/2/23 0023.
 */

public class AtSomebodyModel implements IModel {
    @Override
    public void onAttach() {

    }
    public void getConcerns(Context context, Observable.Transformer
            transformer, Subscriber<List<UserInfoBean>> subscriber){
        PostApi.getConcerns(context)
                .map(new Func1<String, List<UserInfoBean>>() {
            @Override
            public List<UserInfoBean> call(String s) {
                JSONObject object=JSON.parseObject(s);
                if (object!=null){
                    List<UserInfoBean>userInfoBeen= JSON.parseArray(object.getString("users"),UserInfoBean.class);
                    return userInfoBeen;
                }
                return null;
            }
        }).compose(RxHelper.<List<UserInfoBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
    public void getFriendShip(Context context, Observable.Transformer
            transformer, Subscriber<List<UserInfoBean>> subscriber){
        PostApi.getFriendShip(context).map(new Func1<String, List<UserInfoBean>>() {
            @Override
            public List<UserInfoBean> call(String s) {
                JSONObject object=JSON.parseObject(s);
                if (object!=null){
                    List<UserInfoBean>userInfoBeen= JSON.parseArray(object.getString("users"),UserInfoBean.class);
                    return userInfoBeen;
                }
                return null;
            }
        }).compose(RxHelper.<List<UserInfoBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
}
