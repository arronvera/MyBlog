package code.vera.myblog.presenter.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.trello.rxlifecycle.ActivityEvent;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.bean.CommentRequestBean;
import code.vera.myblog.bean.UploadRequestBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.model.PostModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.PostView;

/**
 * 发布
 */
public class PostActivity extends PresenterActivity<PostView, PostModel> {

    private UploadRequestBean uploadRequestBean;
    private CommentRequestBean commentRequestBean;
    private String id;//id
    private int type;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_post;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Intent intent = getIntent();
        type = intent.getIntExtra("type", -1);
         id=intent.getStringExtra("id");
        if (type != -1) {
            view.showTitleAndHint(type);
        }
    }

    @OnClick({R.id.tv_cancle, R.id.btn_post})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle://取消
                finish();
                break;
            case R.id.btn_post://发送
                String msg=view.getEditStr();
                switch (type){
                    case Constants.COMMENT_TYPE:
                        comment();
                        break;
                    case Constants.REPOST_TYPE:
                        repost();
                        break;
                    default://发布新的
                        uploadRequestBean = new UploadRequestBean();
                        uploadRequestBean.setStatus(msg);
                        if (TextUtils.isEmpty(uploadRequestBean.getPic())) {
                            //仅发文字
                            upDate();
                        } else {
                            //带图片
                            upLoad();
                        }
                        break;
                }


                break;
        }
    }

    /**
     * 转发
     */
    private void repost() {
    }

    /**
     * 评论
     */
    private void comment() {
        model.commentMessage(this, commentRequestBean, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(PostActivity.this, "评论成功");
                    finish();
                } else {
                    ToastUtil.showToast(PostActivity.this, "评论失败");
                }
            }
        });
    }

    private void upLoad() {
        model.uploadMessage(this, uploadRequestBean, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(PostActivity.this, "发布成功");
                    finish();
                } else {
                    ToastUtil.showToast(PostActivity.this, "发布失败");
                }
            }
        });
    }

    private void upDate() {
        model.updateMessage(this, uploadRequestBean, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(PostActivity.this, "发布成功");
                    finish();
                } else {
                    ToastUtil.showToast(PostActivity.this, "发布失败");
                }
            }
        });
    }

}
