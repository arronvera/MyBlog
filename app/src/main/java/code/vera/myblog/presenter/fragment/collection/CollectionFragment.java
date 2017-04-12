package code.vera.myblog.presenter.fragment.collection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.CollectionAdapter;
import code.vera.myblog.bean.CollectionBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemCommentListener;
import code.vera.myblog.listener.OnItemLikeListener;
import code.vera.myblog.listener.OnItemMenuListener;
import code.vera.myblog.listener.OnItemRepostListener;
import code.vera.myblog.model.collection.CollectionModel;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.collection.CollectionView;
import code.vera.myblog.view.widget.MenuPopuWindow;
import ww.com.core.Debug;

import static code.vera.myblog.presenter.activity.PostActivity.PARAM_POST_TYPE;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_STATUS_BEAN;

/**
 * 收藏
 * Created by vera on 2017/3/20 0020.
 */

public class CollectionFragment extends PresenterFragment<CollectionView, CollectionModel>
implements OnItemLikeListener,OnItemCommentListener,OnItemMenuListener,OnItemRepostListener{
    private int count=15;
    private int page=1;
    private CollectionAdapter adapter;
    private MenuPopuWindow menuPopupWindow;//菜单
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_collection;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        menuPopupWindow=new MenuPopuWindow(mContext);
        setAdapter();
        getFavorites();
        addListener();
    }

    private void addListener() {
        adapter.setOnItemMenuListener(this);
        adapter.setOnItemCommentListener(this);
        adapter.setOnItemRepostListener(this);
        adapter.setOnItemLikeListener(this);
    }

    private void setAdapter() {
        adapter=new CollectionAdapter(mContext);
        view.setAdapter(adapter);
    }

    private void getFavorites() {
        model.getFavorites(count,page,mContext,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<CollectionBean>>(mContext,true){
            @Override
            public void onNext(List<CollectionBean> collectionBeen) {
                Debug.d("收藏------"+collectionBeen.size());
                super.onNext(collectionBeen);
                adapter.addList(collectionBeen);
            }
        });
    }

    @Override
    public void onItemCommentListener(View v, int pos) {
        StatusesBean statusesBean=adapter.getItem(pos).getStatusesBean();
        if (statusesBean.getComments_count() == 0) {//如果没有评论数，直接跳到发布评论
            Intent intent = new Intent(getActivity(), PostActivity.class);
            intent.putExtra("type", Constants.POST_TYPE_COMMENT);
            intent.putExtra("StatusesBean", statusesBean);
            startActivity(intent);
        } else {//跳转到评论详情
            Bundle bundle = new Bundle();
            bundle.putSerializable("status",statusesBean);
            CommentDetailActivity.start(getContext(), bundle);
        }
    }

    @Override
    public void onItemLikeListener(View v, ImageView imageView, int pos) {
        view.showLikeView(imageView);
    }

    @Override
    public void onItemMenuListener(View v, int pos) {
        menuPopupWindow.showAsDropDown(v);
    }

    @Override
    public void onItemRepostListener(View v, int pos) {
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM_POST_TYPE,Constants.POST_TYPE_REPOST);
        bundle.putSerializable(PARAM_STATUS_BEAN,adapter.getItem(pos).getStatusesBean());
        PostActivity.start(mContext,bundle);
    }
}
