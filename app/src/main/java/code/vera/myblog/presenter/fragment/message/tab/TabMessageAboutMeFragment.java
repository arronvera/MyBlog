package code.vera.myblog.presenter.fragment.message.tab;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.MessageAtmeAdapter;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.config.Constants;
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

import static code.vera.myblog.presenter.activity.PostActivity.PARAM_COMMENT_CID;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_COMMENT_WEIB_ID;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_POST_TYPE;

/**
 * 关于我的评论
 * Created by vera on 2017/1/20 0020.
 */

public class TabMessageAboutMeFragment extends PresenterFragment<TabMessageAboutMeView,TabMessageModel>
        implements OnItemLinkListener,OnItemAtListener,OnItemTopicListener,OnItemOriginalListener,OnItemReplyListener {
    private MessageAtmeAdapter adapter;
    private int page=1;

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
                page=1;
                getMessageAboutMe(false);
            }

            @Override
            public void onFooterRefreshing() {
                page++;
                getMessageAboutMe(false);
            }
        });
    }

    private void setAdapter() {
        adapter=new MessageAtmeAdapter(getContext());
        view.setAdapter(adapter);
    }

    private void getMessageAboutMe(boolean isDialog) {
        model.getCommentMentions(page,getContext(),bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<CommentUserBean>>(mContext,isDialog){
            @Override
            public void onNext(List<CommentUserBean> commentUserBeen) {
                super.onNext(commentUserBeen);
                if (page==1){
                    adapter.addList(commentUserBeen);
                    view.refreshFinished();
                }else {
                    adapter.appendList(commentUserBeen);
                    view.refreshFinished();
                }
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
        CommentUserBean commentUserBean=adapter.getItem(pos);
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM_POST_TYPE, Constants.POST_TYPE_REPLY_COMMENT);
        bundle.putLong(PARAM_COMMENT_CID,commentUserBean.getId());
        bundle.putLong(PARAM_COMMENT_WEIB_ID,commentUserBean.getStatusesBean().getId());

        PostActivity.start(mContext,bundle);
    }
}
