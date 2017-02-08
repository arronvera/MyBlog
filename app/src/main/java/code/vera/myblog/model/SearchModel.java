package code.vera.myblog.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.api.SearchApi;
import code.vera.myblog.bean.home.UserBean;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/2/8 0008.
 */

public class SearchModel implements IModel {
    @Override
    public void onAttach() {

    }
    /**
     * 搜索用户时的联想搜索建议
     */
    public void searchUsers(String key, Context context, Observable.Transformer
            transformer, Subscriber<List<UserBean>> subscriber){
        SearchApi.searchUsers(key,context)
                .map(new Func1<String, List<UserBean>>() {
                    @Override
                    public  List<UserBean> call(String s) {
                        List<UserBean>beanList=new ArrayList<>();
                        JSONObject result= JSON.parseObject(s);
                        if (result!=null){
                            beanList= JSON.parseArray(result.getString("statuses"),UserBean.class);
                        }
                        return beanList;
                    }
                }).compose(RxHelper.<List<UserBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
}
