package code.vera.myblog.model.other;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.api.HomeApi;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.model.base.IModel;
import code.vera.myblog.utils.ToastUtil;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/3/21 0021.
 */

public class TopicModel implements IModel {
    @Override
    public void onAttach() {

    }
    /**
     * 获取某一话题下的分享信息
     */
    public void getTopicStatus(String q, final Context context, Observable.Transformer
            transformer, Subscriber<List<StatusesBean>> subscriber){
        HomeApi.getTopicStatus(q,context)
                .map(new Func1<String, List<StatusesBean>>() {
                    @Override
                    public  List<StatusesBean> call(String s) {
                        List<StatusesBean>beanList=new ArrayList<>();
                        JSONObject result= JSON.parseObject(s);
                        if (result.getString("error")!=null){
                            ToastUtil.showToast(context,result.getString("error"));
                        }
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
}
