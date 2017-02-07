package code.vera.myblog.model.home;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import code.vera.myblog.api.HomeApi;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.HomeWeiboBean;
import code.vera.myblog.model.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
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
                                       transformer,  Subscriber<Boolean> subscriber){
        HomeApi.getHomeTimeLine(bean,context)
                .map(new Func1<String, HomeWeiboBean>() {
                    @Override
                    public HomeWeiboBean call(String s) {
//                        List<HomeWeiboBean>beanList=new ArrayList<>();
                        JSONObject result= JSON.parseObject(s);
                        HomeWeiboBean homeWeiboBean=new HomeWeiboBean();
                        if (result!=null){
                             homeWeiboBean=JSON.parseObject(s,HomeWeiboBean.class);
//                          beanList= JSONArray.parseArray(result.getString("statuses"),HomeWeiboBean.class);
                        }
                        return homeWeiboBean;
                    }
                }).compose(RxHelper.< HomeWeiboBean>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
}
