package code.vera.myblog.model.home;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.api.HomeApi;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.HomeWeiboBean;
import code.vera.myblog.model.IModel;
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
                                       transformer,  Subscriber<Boolean> subscriber){
        HomeApi.getHomeTimeLine(bean,context)
                .map(new Func1<String, List<HomeWeiboBean>>() {
                    @Override
                    public List<HomeWeiboBean> call(String s) {
                        List<HomeWeiboBean>beanList=new ArrayList<>();
                        JSONObject result= JSON.parseObject(s);
                        if (result!=null){
                          beanList= JSONArray.parseArray(result.getString("statuses"),HomeWeiboBean.class);
                        }
                        Debug.d("beanlist.size="+beanList.size());
                        return beanList;
                    }
                }).compose(RxHelper.< List<HomeWeiboBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
}
