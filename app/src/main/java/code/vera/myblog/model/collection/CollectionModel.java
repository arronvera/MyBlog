package code.vera.myblog.model.collection;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import code.vera.myblog.api.CollectionApi;
import code.vera.myblog.bean.CollectionBean;
import code.vera.myblog.model.base.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/4/12 0012.
 */

public class CollectionModel implements IModel {
    @Override
    public void onAttach() {

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
}
