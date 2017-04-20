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
import code.vera.myblog.listener.OnItemCommentListener;
import code.vera.myblog.listener.OnItemConcernListener;
import code.vera.myblog.listener.OnItemLikeListener;
import code.vera.myblog.listener.OnItemRepostListener;
import code.vera.myblog.model.find.FindModel;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.find.FindView;

import static code.vera.myblog.presenter.activity.CommentDetailActivity.BUNDLE_PARAM_STATUS;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_POST_TYPE;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_STATUS_BEAN;

/**
 * Created by vera on 2017/1/20 0020.
 */

public class FindFragment extends PresenterFragment<FindView, FindModel>
        implements OnItemConcernListener, OnItemRepostListener, OnItemCommentListener
        , OnItemLikeListener {
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
        getTimeLine();
        addListener();
    }

    private void addListener() {
        adapter.setOnItemConcernListener(this);
        adapter.setOnItemCommentListener(this);
        adapter.setOnItemRepostListener(this);
    }

    private void getTimeLine() {
        model.getPublic(requestBean, mContext, bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<List<StatusesBean>>(mContext, true) {
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                adapter.addList(statusesBeen);
            }
        });
    }

    private void setAdapter() {
        adapter = new FindAdapter(mContext);
        view.setAdapter(adapter);
    }

    @Override
    public void onItemConcern(final View view, int pos) {
        //关注
        long id = adapter.getItem(pos).getId();
        model.createFriendShip(mContext, id + "", bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    if (!TextUtils.isEmpty(s)) {
                        ToastUtil.showToast(getContext(), getString(R.string.concern_success));
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
}
