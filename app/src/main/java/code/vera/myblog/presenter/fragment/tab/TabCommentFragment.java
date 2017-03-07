package code.vera.myblog.presenter.fragment.tab;

import android.content.Intent;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.CommentAdapter;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.tab.TabCommentDetailModel;
import code.vera.myblog.presenter.activity.BrowserActivity;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.view.tab.TabCommentView;
import ww.com.core.Debug;

/**
 *评论
 * Created by vera on 2017/2/9 0009.
 */

public class TabCommentFragment  extends PresenterFragment<TabCommentView, TabCommentDetailModel>
implements OnItemLinkListener,OnItemTopicListener,OnItemAtListener{
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
        addListener();
    }

    private void addListener() {
        adapter.setOnItemAtListener(this);
        adapter.setOnItemTopicListener(this);
        adapter.setOnItemLinkListener(this);


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

    @Override
    public void onItemAtListener(View v, int pos, String str) {
        Intent intent=new Intent(getActivity(), PersonalityActivity.class);
        intent.putExtra("user_name",str.substring(str.indexOf("@")+1,str.length()));
        startActivity(intent);
    }

    @Override
    public void onItemLinkListener(View v, int pos, String str) {
        //链接
        Intent intent=new Intent(getActivity(),BrowserActivity.class);
        intent.putExtra("link",str);
        startActivity(intent);
    }

    @Override
    public void onItemTopicListener(View v, int pos, String str) {
        //todo 话题
    }
}
