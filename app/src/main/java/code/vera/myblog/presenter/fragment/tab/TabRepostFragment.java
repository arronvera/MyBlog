package code.vera.myblog.presenter.fragment.tab;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.RepostAdapter;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.model.tab.TabCommentDetailModel;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.tab.TabRepostView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * 转发
 * Created by vera on 2017/2/9 0009.
 */

public class TabRepostFragment extends PresenterFragment<TabRepostView, TabCommentDetailModel> {
    private int page=1;
    private RepostAdapter adapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_repost;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setAdapter();
        getReposts(true);
        addListener();
    }

    private void addListener() {
        view.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page=1;
                getReposts(false);
            }

            @Override
            public void onFooterRefreshing() {
                page++;
                getReposts(false);
            }
        });
    }

    private void setAdapter() {
        adapter=new RepostAdapter(mContext);
        view.setAdapter(adapter);
    }

    private void getReposts(boolean isDialog) {
        model.getReposts(mContext, CommentDetailActivity.id,page,
                bindUntilEvent(FragmentEvent.DESTROY),
                new CustomSubscriber<List<CommentUserBean>>(mContext,isDialog){
            @Override
            public void onNext(List<CommentUserBean> commentUserBeen) {
                super.onNext(commentUserBeen);
                adapter.addList(commentUserBeen);
            }
        });
    }
}
