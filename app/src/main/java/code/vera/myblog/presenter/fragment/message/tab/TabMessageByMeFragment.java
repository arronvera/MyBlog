package code.vera.myblog.presenter.fragment.message.tab;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.MessageBymeAdapter;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemDeleteClickListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemOriginalListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.message.TabMessageModel;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.activity.TopicActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.DialogUtils;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.message.tab.TabMessageByMeView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

import static code.vera.myblog.presenter.activity.PersonalityActivity.BUNDLER_PARAM_USER;
import static code.vera.myblog.presenter.activity.TopicActivity.BUNDLER_PARAM_TOPIC;

/**
 * 我发出的评论
 * Created by vera on 2017/1/20 0020.
 */

public class TabMessageByMeFragment extends PresenterFragment<TabMessageByMeView,TabMessageModel>
        implements OnItemLinkListener,OnItemAtListener,OnItemTopicListener,OnItemOriginalListener
,OnItemDeleteClickListener{
    private MessageBymeAdapter adapter;
    private int page=1;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_message_by_me;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setAdapter();
        addListener();
        getMessageByMe(true);
    }

    private void addListener() {
        adapter.setOnItemLinkListener(this);
        adapter.setOnItemTopicListener(this);
        adapter.setOnItemAtListener(this);
        adapter.setOnItemOriginalListener(this);
        adapter.setOnItemDeleteClickListener(this);
        view.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page=1;
                getMessageByMe(false);
            }

            @Override
            public void onFooterRefreshing() {
                page++;
                getMessageByMe(false);
            }
        });
    }

    private void getMessageByMe(boolean isDialog) {
        model.getCommentByMe(page,getContext(),bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<CommentUserBean>>(mContext,isDialog){
            @Override
            public void onNext(List<CommentUserBean> commentUserBeen) {
                super.onNext(commentUserBeen);
                if (page==1){
                    adapter.addList(commentUserBeen);
                }else {
                    adapter.appendList(commentUserBeen);
                }
                view.refreshFinished();
            }
        });
    }

    private void setAdapter() {
        adapter=new MessageBymeAdapter(mContext);
        view.setAdapter(adapter);
    }

    @Override
    public void onItemOriginalListener(View v, int pos) {

    }

    @Override
    public void onItemAtListener(View v, int pos, String str) {
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLER_PARAM_USER,str.substring(str.indexOf("@") + 1, str.length()));
        PersonalityActivity.start(mContext,bundle);
    }

    @Override
    public void onItemTopicListener(View v, int pos, String str) {
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLER_PARAM_TOPIC,str.substring(1, str.length() - 1));
        TopicActivity.start(mContext,bundle);
    }

    @Override
    public void onItemLinkListener(View v, int pos, String str, int type) {

    }

    @Override
    public void onItemDeleteClickListener(View view, final int pos) {
        DialogUtils.showDialog(mContext, "", "你确定要删除这条评论", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteComment(pos);
            }
        },"取消",null);
    }

    private void deleteComment(final int pos) {
        long cid=adapter.getItem(pos).getId();
        model.deleteComment(cid+"",mContext,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<Boolean>(mContext,false){
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean){
                    adapter.removeItem(adapter.getItem(pos));
                    adapter.notifyDataSetChanged();
                }else {
                    ToastUtil.showToast(mContext,"删除失败，请检查当前网络");
                }
            }
        });
    }
}
