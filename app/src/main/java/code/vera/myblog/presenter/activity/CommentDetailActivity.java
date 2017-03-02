package code.vera.myblog.presenter.activity;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.RetweetedStatusBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.model.CommentDetailModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.CommentDetailView;

/**
 * 评论详情
 */
public class CommentDetailActivity extends PresenterActivity<CommentDetailView, CommentDetailModel> {


    public static long id;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment_detail;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Intent intent = getIntent();
        //
        StatusesBean statusesBean= (StatusesBean) intent.getSerializableExtra("status");
        if (statusesBean!=null&&statusesBean.getId()!=0){
            id=statusesBean.getId();
            view.showInfo(statusesBean);
        }
        //
        RetweetedStatusBean retweetedStatusBean= (RetweetedStatusBean) intent.getSerializableExtra("retweeted_status");
        if (retweetedStatusBean!=null&&retweetedStatusBean.getId()!=0){
            id=retweetedStatusBean.getId();
            view.showInfo2(retweetedStatusBean);
        }
        view.setActivity(this);
        view.setAdapter();
    }
    @OnClick(R.id.tv_item_comment)
    public void doClick(View v){
        switch (v.getId()){
            case R.id.tv_item_comment://评论



                break;
            case R.id.tv_item_repost://转发



                break;
            case R.id.rl_item_like:

                break;
        }
    }

}
