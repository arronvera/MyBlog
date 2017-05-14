package code.vera.myblog.presenter.fragment.collection;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.CollectionAdapter;
import code.vera.myblog.bean.CollectionBean;
import code.vera.myblog.bean.StatusesBean;
import code.vera.myblog.callback.ChangeScreenCallBack;
import code.vera.myblog.callback.MenuConcernCallback;
import code.vera.myblog.callback.MenuCopyCallback;
import code.vera.myblog.callback.MenuShareCallback;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemCommentListener;
import code.vera.myblog.listener.OnItemFavoriteListener;
import code.vera.myblog.listener.OnItemLikeListener;
import code.vera.myblog.listener.OnItemMenuListener;
import code.vera.myblog.listener.OnItemRepostListener;
import code.vera.myblog.model.collection.CollectionModel;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ScreenUtils;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.collection.CollectionView;
import ww.com.core.Debug;
import ww.com.core.widget.CustomSwipeRefreshLayout;

import static code.vera.myblog.presenter.activity.PostActivity.PARAM_POST_TYPE;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_STATUS_BEAN;

/**
 * 收藏
 * Created by vera on 2017/3/20 0020.
 */

public class CollectionFragment extends PresenterFragment<CollectionView, CollectionModel>
implements OnItemLikeListener,OnItemCommentListener,OnItemMenuListener,OnItemRepostListener
,OnItemFavoriteListener,ChangeScreenCallBack,MenuCopyCallback,MenuConcernCallback,MenuShareCallback{
    private int count=15;
    private int page=1;
    private CollectionAdapter adapter;
    private int index;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_collection;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setAdapter();
        getFavorites(true);
        addListener();
    }

    private void addListener() {
        adapter.setOnItemMenuListener(this);
        adapter.setOnItemCommentListener(this);
        adapter.setOnItemRepostListener(this);
        adapter.setOnItemLikeListener(this);
        view.setOnItemFavoriteListener(this);
        view.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page=1;
                getFavorites(false);
            }

            @Override
            public void onFooterRefreshing() {
                page++;
                getFavorites(false);

            }
        });
        view.setChangeScreenCallBack(this);//屏幕亮度
        view.setMenuCopyCallback(this);//复制到粘贴板
        view.setMenuConcernCallback(this);//关注
        view.setMenuShareCallback(this);
    }

    private void setAdapter() {
        adapter=new CollectionAdapter(mContext);
        view.setAdapter(adapter);
    }

    private void getFavorites(boolean isDialog) {
        model.getFavorites(count,page,mContext,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<CollectionBean>>(mContext,isDialog){
            @Override
            public void onNext(List<CollectionBean> collectionBeen) {
                Debug.d("收藏------"+collectionBeen.size());
                super.onNext(collectionBeen);
                if (page==1){
                    adapter.addList(collectionBeen);
                }else {
                    adapter.appendList(collectionBeen);
                }
                view.refreshFinished();
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
        Debug.d("弹出弹出框----------");
        index=pos;
        view.showMenuPopwindow(v);
        ScreenUtils.backgroundAlpaha(getActivity(), 0.5f);
    }

    @Override
    public void onItemRepostListener(View v, int pos) {
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM_POST_TYPE,Constants.POST_TYPE_REPOST);
        bundle.putSerializable(PARAM_STATUS_BEAN,adapter.getItem(pos).getStatusesBean());
        PostActivity.start(mContext,bundle);
    }

    @Override
    public void onItemFavorite(View v) {
        destroyFavorites(adapter.getItem(index).getStatusesBean().getId()+"");
    }
    private void destroyFavorites(String weibId) {
        model.destroyFavorites(weibId,mContext,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<Boolean>(mContext,true){
            @Override
            public void onNext(Boolean b) {
                super.onNext(b);
                if (b){
                    ToastUtil.showToast(mContext,"取消收藏成功");
                    view.dismissPop();
                    //取消收藏 更新
                    adapter.getItem(index).getStatusesBean().setFavorited(false);
                    adapter.notifyItemRemoved(index);
                    ScreenUtils.backgroundAlpaha(getActivity(), 1.0f);
                }else {
                    ToastUtil.showToast(mContext,"取消收藏失败");
                }
            }
        });
    }

    @Override
    public void changeScreen() {//更改屏幕亮度
        ScreenUtils.backgroundAlpaha(getActivity(), 1.0f);
    }

    @Override
    public void copy() {//复制到粘贴板
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(adapter.getItem(index).getStatus());
        ToastUtil.showToast(getContext(), "复制成功");
        view.dismissPop();
    }

    @Override
    public void concern() {//关注
        ToastUtil.showToast(mContext,getString(R.string.api_not_found));
        view.dismissPop();
    }

    @Override
    public void share() {//分享
        //todo
        ToastUtil.showToast(mContext,getString(R.string.api_not_found));
        view.dismissPop();
    }
}
