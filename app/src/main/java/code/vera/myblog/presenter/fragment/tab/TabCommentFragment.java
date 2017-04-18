package code.vera.myblog.presenter.fragment.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.CommentAdapter;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemLikeListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemReplyListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.tab.TabCommentDetailModel;
import code.vera.myblog.presenter.activity.BrowserActivity;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.activity.TopicActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.tab.TabCommentView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

import static code.vera.myblog.presenter.activity.CommentDetailActivity.ACTION_UPDATE_COMMENT_NUM;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_COMMENT_CID;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_COMMENT_WEIB_ID;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_POST_TYPE;
import static code.vera.myblog.presenter.activity.TopicActivity.BUNDLER_PARAM_TOPIC;

/**
 * 评论
 * Created by vera on 2017/2/9 0009.
 */

public class TabCommentFragment extends PresenterFragment<TabCommentView, TabCommentDetailModel>
        implements OnItemLinkListener, OnItemTopicListener, OnItemAtListener,
        OnItemLikeListener,OnItemReplyListener {
    private CommentAdapter adapter;
    private int page = 1;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_comment;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setAdapter();
        getComments(true);
        addListener();
    }

    private void addListener() {
        adapter.setOnItemAtListener(this);
        adapter.setOnItemTopicListener(this);
        adapter.setOnItemLinkListener(this);
        adapter.setOnItemLikeListener(this);
        adapter.setOnItemReplyListener(this);
        view.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page=1;
                getComments(false);
                mContext.sendBroadcast(new Intent(ACTION_UPDATE_COMMENT_NUM));
            }

            @Override
            public void onFooterRefreshing() {
                page++;
                getComments(false);
            }
        });
    }

    private void setAdapter() {
        adapter = new CommentAdapter(getContext());
        view.setAdapter(adapter);
    }


    public void getComments(boolean isDialog) {
        model.getComments(getContext(), CommentDetailActivity.id, page, bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<List<CommentUserBean>>(mContext, isDialog) {
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

    @Override
    public void onItemAtListener(View v, int pos, String str) {
        Intent intent = new Intent(getActivity(), PersonalityActivity.class);
        intent.putExtra("user_name", str.substring(str.indexOf("@") + 1, str.length()));
        startActivity(intent);
    }

    @Override
    public void onItemTopicListener(View v, int pos, String str) {
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLER_PARAM_TOPIC,str.substring(1, str.length() - 1));
        TopicActivity.start(mContext,bundle);
    }

    @Override
    public void onItemLinkListener(View v, int pos, String str, int type) {
        //链接
        Intent intent = new Intent(getActivity(), BrowserActivity.class);
        intent.putExtra("link", str);
        startActivity(intent);
    }

    @Override
    public void onItemLikeListener(View v, ImageView imageView, int pos) {
        view.setLikeView(imageView);
        //todo 数字加1
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
