package code.vera.myblog.presenter.fragment.message.tab;

import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.MessageAtmeAdapter;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemOriginalListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.message.TabMessageModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.message.tab.TabMessageToMeView;
import ww.com.core.Debug;

/**
 * 关于我
 * Created by vera on 2017/1/20 0020.
 */

public class TabMessageToMeFragment extends PresenterFragment<TabMessageToMeView,TabMessageModel>
        implements OnItemLinkListener,OnItemAtListener,OnItemTopicListener,OnItemOriginalListener {
    private MessageAtmeAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_message_to_me;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setAdapter();
        getMessageToMe();
        addListener();
    }
    private void addListener() {
        adapter.setOnItemLinkListener(this);
        adapter.setOnItemTopicListener(this);
        adapter.setOnItemAtListener(this);
        adapter.setOnItemOriginalListener(this);
    }
    private void getMessageToMe() {
        model.getCommentToMe(1,getContext(),bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<CommentUserBean>>(mContext,true){
            @Override
            public void onNext(List<CommentUserBean> commentUserBeen) {
                super.onNext(commentUserBeen);
                Debug.d("commentUseBeen.size="+commentUserBeen.size());
                adapter.addList(commentUserBeen);
            }
        });
    }

    private void setAdapter() {
        adapter=new MessageAtmeAdapter(getContext());
        view.setAdapter(adapter);
    }

    @Override
    public void onItemAtListener(View v, int pos, String str) {

    }

    @Override
    public void onItemLinkListener(View v, int pos, String str) {

    }

    @Override
    public void onItemOriginalListener(View v, int pos) {

    }

    @Override
    public void onItemTopicListener(View v, int pos, String str) {

    }
}
