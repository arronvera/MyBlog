package code.vera.myblog.presenter.fragment.home;

import android.content.Intent;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.HomeAdapter;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.model.home.HomeModel;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.home.HomeView;
import ww.com.core.Debug;
import ww.com.core.widget.CustomSwipeRefreshLayout;


/**
 * 首页
 * Created by vera on 2017/1/20 0020.
 */

public class HomeFragment  extends PresenterFragment<HomeView, HomeModel>implements
        HomeAdapter.OnItemCommentListener,HomeAdapter.OnItemRepostListener,HomeAdapter.OnItemLikeListener {
    private HomeRequestBean requestBean;
    private HomeAdapter adapter;//适配器
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        requestBean=new HomeRequestBean();
        setAdater();
        getData();
        //上下拉刷新
        view.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                //下拉刷新
                requestBean.setPage("1");
                getData();
            }

            @Override
            public void onFooterRefreshing() {
                //上拉加载
                int nextPage=Integer.parseInt(requestBean.getPage())+1;
                requestBean.setPage(nextPage+"");
                getData();
            }
        });
        addListener();
    }

    private void addListener() {
        adapter.setOnItemCommentListener(this);
        adapter.setOnItemRepostListener(this);
        adapter.setOnItemLikeListener(this);

    }

    private void setAdater() {
        adapter=new HomeAdapter(getContext());
        view.setAdapter(adapter);
    }


    /**
     * 获取数据
     */
    private void getData() {
        model.getHomeTimeLine(requestBean, getContext(), bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<List<StatusesBean>>(mContext,true){
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                if (statusesBeen!=null){
//                    Debug.d("size="+statusesBeen.size());
                    if (requestBean.getPage().equals("1")){
                        adapter.addList(statusesBeen);//清空加载进去
                    }else {//往后面追加
                        adapter.appendList(statusesBeen);
                    }
                }
            }
        });
    }

    @Override
    public void onItemCommentListener(View v, int pos) {
        //评论
//        ToastUtil.showToast(getContext(),"评论");
        Debug.d("count="+adapter.getItem(pos).getComments_count());
        if (adapter.getItem(pos).getComments_count()==0){//如果没有评论数，直接跳到发布评论
            Intent intent=new Intent(getActivity(), PostActivity.class);
            intent.putExtra("type", Constants.COMMENT_TYPE);
            intent.putExtra("id",adapter.getItem(pos).getId()+"");
            startActivity(intent);
        }else {//跳转到评论详情
            Intent intent=new Intent(getActivity(), CommentDetailActivity.class);
            intent.putExtra("status",adapter.getItem(pos));
            startActivity(intent);
        }

    }

    @Override
    public void onItemRepostListener(View v, int pos) {
        //转发
        ToastUtil.showToast(getContext(),"转发");

    }

    @Override
    public void onItemLikeListener(View v, int pos) {
        //喜欢
        ToastUtil.showToast(getContext(),"喜欢");

    }
}
