package code.vera.myblog.model.other;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import code.vera.myblog.api.LocationApi;
import code.vera.myblog.bean.PoiBean;
import code.vera.myblog.model.base.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/4/20 0020.
 */

public class LocationModel implements IModel {
    @Override
    public void onAttach() {
    }

    public void getNearByAddress(double lat, double lon, String q, int count, int page,
                                 Context context, Observable.Transformer
                                         transformer, Subscriber<List<PoiBean>> subscriber) {
        LocationApi.getNearByPlaces(lat, lon, q, count, page, context)
                .map(new Func1<String, List<PoiBean>>() {
                    @Override
                    public List<PoiBean> call(String s) {
                        JSONObject jsonObject = JSON.parseObject(s);
                        return JSON.parseArray(jsonObject.getString("pois"), PoiBean.class);
                    }
                }).compose(RxHelper.<List<PoiBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
}
