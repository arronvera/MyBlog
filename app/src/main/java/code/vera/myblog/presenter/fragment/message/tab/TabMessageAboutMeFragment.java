package code.vera.myblog.presenter.fragment.message.tab;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.MessageAtmeAdapter;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemOriginalListener;
import code.vera.myblog.listener.OnItemReplyListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.message.TabMessageModel;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.message.tab.TabMessageAboutMeView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * 关于我的评论
 * Created by vera on 2017/1/20 0020.
 */

public class TabMessageAboutMeFragment extends PresenterFragment<TabMessageAboutMeView,TabMessageModel>
        implements OnItemLinkListener,OnItemAtListener,OnItemTopicListener,OnItemOriginalListener,OnItemReplyListener {
    private MessageAtmeAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_message_about_me;
    }
    @Override
    protected void onAttach() {
        super.onAttach();
        setAdapter();
        getMessageAboutMe(true);
        addListener();
    }

    private void addListener() {
        adapter.setOnItemLinkListener(this);
        adapter.setOnItemTopicListener(this);
        adapter.setOnItemAtListener(this);
        adapter.setOnItemOriginalListener(this);
        adapter.setOnItemReplyListener(this);
        view.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                getMessageAboutMe(false);
            }

            @Override
            public void onFooterRefreshing() {

            }
        });
    }

    private void setAdapter() {
        adapter=new MessageAtmeAdapter(getContext());
        view.setAdapter(adapter);
    }

    private void getMessageAboutMe(boolean isDialog) {
        model.getCommentMentions(1,getContext(),bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<CommentUserBean>>(mContext,isDialog){
            @Override
            public void onNext(List<CommentUserBean> commentUserBeen) {
                super.onNext(commentUserBeen);
                adapter.addList(commentUserBeen);
            }
        });
    }

    @Override
    public void onItemAtListener(View v, int pos, String str) {
        //todo at
    }


    @Override
    public void onItemTopicListener(View v, int pos, String str) {
        //todo topic
    }

    @Override
    public void onItemOriginalListener(View v, int pos) {
        //todo original
    }

    @Override
    public void onItemLinkListener(View v, int pos, String str, int type) {

    }

    @Override
    public void onItemReply(View v, int pos) {
        Bundle bundle=new Bundle();

        PostActivity.start(mContext,bundle);
    }
}
