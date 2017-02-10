package code.vera.myblog.presenter.fragment.tab;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.CommentAdapter;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.model.tab.TabCommentDetailModel;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.tab.TabCommentView;
import ww.com.core.Debug;

/**
 *评论
 * Created by vera on 2017/2/9 0009.
 */

public class TabCommentFragment  extends PresenterFragment<TabCommentView, TabCommentDetailModel> {
    private CommentAdapter adapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_comment;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setAdapter();
        getComments();
    }

    private void setAdapter() {
        adapter=new CommentAdapter(getContext());
        view.setAdapter(adapter);
    }

    public void getComments(){
        model.getComments(getContext(), CommentDetailActivity.id,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<CommentUserBean>>(mContext,true){
            @Override
            public void onNext(List<CommentUserBean> commentUserBeen) {
                super.onNext(commentUserBeen);
                Debug.d("size="+commentUserBeen.size());
                adapter.addList(commentUserBeen);
            }
        });
    }
}
