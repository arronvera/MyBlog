package code.vera.myblog.model.message;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import code.vera.myblog.api.MessageApi;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.model.base.IModel;
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
                        }
                        return beanList;
                    }
                }).compose(RxHelper.<List<CommentUserBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
    /**
     * 获取发出的评论列表评论
     */
    public void getCommentByMe(int page, Context context, Observable.Transformer
            transformer, Subscriber<List<CommentUserBean>> subscriber){
        MessageApi.getCommentsByMe(context,page)
                .map(new Func1<String, List<CommentUserBean>>() {
                    @Override
                    public  List<CommentUserBean> call(String s) {
                        List<CommentUserBean>beanList=new ArrayList<>();
                        JSONObject result= JSON.parseObject(s);
                        if (result!=null){
                            beanList= JSON.parseArray(result.getString("comments"),CommentUserBean.class);
                        }
                        return beanList;
                    }
                }).compose(RxHelper.<List<CommentUserBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
    /**
     * 获取收到的的评论列表评论
     */
    public void getCommentToMe(int page, Context context, Observable.Transformer
            transformer, Subscriber<List<CommentUserBean>> subscriber){
        MessageApi.getCommentToMe(context,page)
                .map(new Func1<String, List<CommentUserBean>>() {
                    @Override
                    public  List<CommentUserBean> call(String s) {
                        List<CommentUserBean>beanList=new ArrayList<>();
                        JSONObject result= JSON.parseObject(s);
                        if (result!=null){
                            beanList= JSON.parseArray(result.getString("comments"),CommentUserBean.class);
                        }
                        return beanList;
                    }
                }).compose(RxHelper.<List<CommentUserBean>>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }


    /**
     * 删除评论
     */
    public void deleteComment(String cid, Context context, Observable.Transformer
            transformer, Subscriber<Boolean> subscriber){
        MessageApi.deleteComment(context,cid)
                .map(new Func1<String, Boolean>() {
                    @Override
                    public  Boolean call(String s) {
                        JSONObject result= JSON.parseObject(s);
                        if (result!=null){
                            if (result.getString("user")!=null){
                                return true;
                            }
                        }
                        return false;
                    }
                }).compose(RxHelper.<Boolean>cutMain())
                .compose(transformer)
                .subscribe(subscriber);
    }
}
