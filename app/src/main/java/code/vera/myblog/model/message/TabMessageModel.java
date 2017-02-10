package code.vera.myblog.model.message;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.api.MessageApi;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.model.IModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by vera on 2017/2/10 0010.
 */

public class TabMessageModel implements IModel {
    @Override
    public void onAttach() {

    }
    /**
     * 获取@我的评论
     */
    public void getCommentMentions(int page, Context context, Observable.Transformer
            transformer, Subscriber<List<CommentUserBean>> subscriber){
        MessageApi.getMentions(context,page)
                .map(new Func1<String, List<CommentUserBean>>() {
                    @Override
                    public  List<CommentUserBean> call(String s) {
                        List<CommentUserBean>beanList=new ArrayList<>();
                        JSONObject result= JSON.parseObject(s);
                        if (result!=null){
                            beanList= JSON.parseArray(result.getString("comments"),CommentUserBean.class);
//                            Debug.d("beanlist.size="+beanList.size());
                        }
                        return beanList;
                    }
                }).compose(RxHelper.<List<CommentUserBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
}
