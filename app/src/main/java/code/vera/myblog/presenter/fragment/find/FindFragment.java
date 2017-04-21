package code.vera.myblog.presenter.fragment.find;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.FindAdapter;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemCommentListener;
import code.vera.myblog.listener.OnItemConcernListener;
import code.vera.myblog.listener.OnItemHeadPhotoListener;
import code.vera.myblog.listener.OnItemLikeListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemRepostListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.find.FindModel;
import code.vera.myblog.presenter.activity.BrowserActivity;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.activity.TopicActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.find.FindView;
import ww.com.core.Debug;
import ww.com.core.widget.CustomSwipeRefreshLayout;

import static code.vera.myblog.presenter.activity.BrowserActivity.BUNDLER_PARAM_LINK;
import static code.vera.myblog.presenter.activity.CommentDetailActivity.BUNDLE_PARAM_STATUS;
import static code.vera.myblog.presenter.activity.PersonalityActivity.BUNDLER_PARAM_USER;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_POST_TYPE;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_STATUS_BEAN;
import static code.vera.myblog.presenter.activity.TopicActivity.BUNDLER_PARAM_TOPIC;

/**
 * Created by vera on 2017/1/20 0020.
 */

public class FindFragment extends PresenterFragment<FindView, FindModel>
        implements OnItemConcernListener,
        OnItemRepostListener, OnItemCommentListener
        , OnItemLikeListener, OnItemHeadPhotoListener,
        OnItemAtListener, OnItemTopicListener, OnItemLinkListener {
    private FindAdapter adapter;
    private HomeRequestBean requestBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        requestBean = new HomeRequestBean();
        setAdapter();
        getTimeLine(true);
        addListener();
    }

    private void addListener() {
        adapter.setOnItemConcernListener(this);
        adapter.setOnItemCommentListener(this);
        adapter.setOnItemRepostListener(this);
        adapter.setOnItemLikeListener(this);
        adapter.setOnItemAtListener(this);
        adapter.setOnItemHeadPhotoListener(this);
        view.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                requestBean.page = "1";
                getTimeLine(false);
            }

            @Override
            public void onFooterRefreshing() {
                requestBean.page = (Integer.parseInt(requestBean.page) + 1) + "";
                getTimeLine(false);
            }
        });
    }

    private void getTimeLine(boolean isDialog) {
        model.getPublic(requestBean, mContext, bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<List<StatusesBean>>(mContext, isDialog) {
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                if ("1".equals(requestBean.page)) {
                    adapter.addList(statusesBeen);
                } else {
                    adapter.appendList(statusesBeen);
                }
            }
        });
    }

    private void setAdapter() {
        adapter = new FindAdapter(mContext);
        view.setAdapter(adapter);
    }

    @Override
    public void onItemConcern(final View view, final int pos) {
        //关注
        long id = adapter.getItem(pos).getId();
        model.createFriendShip(mContext, id + "", bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    if (!TextUtils.isEmpty(s)) {
                        ToastUtil.showToast(getContext(), getString(R.string.concern_success));
                        //更新
                        adapter.getItem(pos).getUserBean().setFollowing(true);
                        adapter.notifyItemChanged(pos);
                    }
                }
            }
        });
    }

    @Override
    public void onItemCommentListener(View v, int pos) {
        //评论
        StatusesBean statusesBean = adapter.getItem(pos);
        if (statusesBean.getComments_count() == 0) {//如果没有评论数，直接跳到发布评论
            Bundle bundle = new Bundle();
            bundle.putInt(PARAM_POST_TYPE, Constants.POST_TYPE_COMMENT);
            bundle.putSerializable(PARAM_STATUS_BEAN, statusesBean);
            PostActivity.start(mContext, bundle);
        } else {//跳转到评论详情
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_PARAM_STATUS, statusesBean);
            CommentDetailActivity.start(getContext(), bundle);
        }
    }

    @Override
    public void onItemRepostListener(View v, int pos) {
        //转发
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM_POST_TYPE, Constants.POST_TYPE_REPOST);
        bundle.putSerializable(PARAM_STATUS_BEAN, adapter.getItem(pos));
        PostActivity.start(mContext, bundle);
    }

    @Override
    public void onItemLikeListener(View v, ImageView imageView, int pos) {
        view.setLikeView(imageView);
    }

    @Override
    public void onItemAtListener(View v, int pos, String str) {
        Debug.d("点击@某人" + str);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLER_PARAM_USER, str.substring(str.indexOf("@") + 1, str.length()));
        PersonalityActivity.start(mContext, bundle);
    }

    @Override
    public void onItemHeadPhotoListener(View v, int pos) {
        //点击头头像，跳转到个人界面
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLER_PARAM_USER, adapter.getItem(pos).getUserBean());
        PersonalityActivity.start(mContext, bundle);
    }

    @Override
    public void onItemLinkListener(View v, int pos, String str, int type) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLER_PARAM_LINK, str);
        BrowserActivity.start(mContext, bundle);
    }

    @Override
    public void onItemTopicListener(View v, int pos, String str) {
        Debug.d("点击topic话题" + str);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLER_PARAM_TOPIC, str.substring(1, str.length() - 1));
        TopicActivity.start(mContext, bundle);
    }
}
