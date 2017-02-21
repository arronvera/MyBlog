package code.vera.myblog.presenter.fragment.message.tab;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.MessageAtmeAdapter;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.model.message.TabMessageModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.message.tab.TabMessageByMeView;
import ww.com.core.Debug;

/**
 * 关于我
 * Created by vera on 2017/1/20 0020.
 */

public class TabMessageByMeFragment extends PresenterFragment<TabMessageByMeView,TabMessageModel> {
    private MessageAtmeAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_message_by_me;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setAdapter();
        getMessageByMe();
    }

    private void getMessageByMe() {
        model.getCommentByMe(1,getContext(),bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<CommentUserBean>>(mContext,true){
            @Override
            public void onNext(List<CommentUserBean> commentUserBeen) {
                super.onNext(commentUserBeen);
                Debug.d("size="+commentUserBeen.size());
                adapter.addList(commentUserBeen);
            }
        });
    }

    private void setAdapter() {
        adapter=new MessageAtmeAdapter(getContext());
        view.setAdapter(adapter);
    }
}
