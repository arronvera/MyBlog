package code.vera.myblog.presenter.fragment.draft;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.DraftAdapter;
import code.vera.myblog.bean.CommentRequestBean;
import code.vera.myblog.bean.PostBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.db.PostDao;
import code.vera.myblog.listener.OnItemDeleteClickListener;
import code.vera.myblog.listener.OnItemSendListener;
import code.vera.myblog.model.PostModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.DialogUtils;
import code.vera.myblog.view.draft.DraftView;
import ww.com.core.Debug;

/**
 * 草稿箱
 * Created by vera on 2017/2/13 0013.
 */

public class DraftFragment extends PresenterFragment<DraftView, PostModel>
        implements OnItemDeleteClickListener, OnItemSendListener {
    private PostDao postDao;
    private DraftAdapter adapter;
    private List<PostBean> postBeanList;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_draft;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        initData();
        setAdapter();
        addListener();
        getDraft();
    }

    private void addListener() {
        adapter.setOnItemDeleteClickListener(this);
        adapter.setOnItemSendListener(this);

    }

    private void setAdapter() {
        adapter = new DraftAdapter(getContext());
        view.setAdapter(adapter);
    }

    private void initData() {
        postDao = PostDao.getInstance(getContext());
    }

    private void getDraft() {
        postBeanList = postDao.getAll();
        if (postBeanList != null) {
            Debug.d("size=" + postBeanList.size());
            adapter.addList(postBeanList);
        }
    }

    @Override
    public void onItemDeleteClickListener(View view, final int pos) {
        DialogUtils.showDialog(getContext(), "", "你确定要删除这个草稿", "是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //删除
                postDao.delete(postBeanList.get(pos));
                getDraft();
                //更新
                adapter.notifyDataSetChanged();
            }
        }, "否", null);

    }

    @Override
    public void onItemSendListener(View view, int pos, final PostBean postBean) {
        int type=postBean.getPostType();
        switch (type) {
            case Constants.POST_TYPE_NEW:
                //todo
                break;
            case Constants.POST_TYPE_COMMENT://评论
                CommentRequestBean requestBean=new CommentRequestBean();
                requestBean.setId(postBean.getId());
                requestBean.setComment(postBean.getStatus());
                model.commentMessage(mContext,requestBean,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<String>(mContext,true){
                    @Override
                    public void onNext(String s) {
                        super.onNext(s);
                        if (!TextUtils.isEmpty(s)){
                            showToast("发布成功");
                            postDao.delete(postBean);
                            getDraft();
                            adapter.notifyDataSetChanged();
                        }else {
                            showToast("发布失败");
                        }
                    }
                });
                break;
            case Constants.POST_TYPE_REPOST://转发
                model.repostMessage(mContext,postBean.getId()+"",postBean.getStatus(),bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<String>(mContext,true){
                    @Override
                    public void onNext(String s) {
                        super.onNext(s);
                        if (!TextUtils.isEmpty(s)){
                            showToast("发布成功");
                            postDao.delete(postBean);
                            getDraft();
                            adapter.notifyDataSetChanged();
                        }else {
                            showToast("发布失败");
                        }
                    }
                });
                break;
        }

    }
}
