package code.vera.myblog.model.tab;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.api.PostApi;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.model.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class TabCommentDetailModel implements IModel {
    @Override
    public void onAttach() {

    }

    /**
     * 获取评论
     * @param context
     * @param id id
     * @param transformer
     * @param subscriber
     */
    public void getComments(Context context, long id, Observable.Transformer
            transformer, Subscriber<List<CommentUserBean>> subscriber){
        PostApi.getComments(context,id) .map(new Func1<String, List<CommentUserBean>>() {
            @Override
            public  List<CommentUserBean> call(String s) {
                List<CommentUserBean> userBeen=new ArrayList<>();
                JSONObject result= JSON.parseObject(s);
                if (result!=null){
                    userBeen=JSON.parseArray(result.getString("comments"),CommentUserBean.class);
                }
                return userBeen;
            }
        }).compose(RxHelper.<List<CommentUserBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
}