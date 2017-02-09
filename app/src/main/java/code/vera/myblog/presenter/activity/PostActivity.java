package code.vera.myblog.presenter.activity;

import android.text.TextUtils;
import android.view.View;

import com.trello.rxlifecycle.ActivityEvent;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.bean.UploadRequestBean;
import code.vera.myblog.model.PostModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.PostView;

/**
 * 发布
 */
public class PostActivity extends PresenterActivity<PostView,PostModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_post;
    }
    @Override
    protected void onAttach() {
        super.onAttach();
    }

    @OnClick({R.id.tv_cancle,R.id.btn_post})
    public void doClick(View v){
        switch (v.getId()){
            case R.id.tv_cancle://取消
                finish();
                break;
            case R.id.btn_post://发送
                UploadRequestBean requestBean=new UploadRequestBean();
                requestBean.setStatus(view.getEditStr());
                if (TextUtils.isEmpty(requestBean.getPic())){
                    //仅发文字
                    model.updateMessage(this,requestBean,bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<String>(mContext,true){
                        @Override
                        public void onNext(String s) {
                            super.onNext(s);
                            if (TextUtils.isEmpty(s)){
                                ToastUtil.showToast(PostActivity.this,"发布成功");
                                finish();
                            }else {
                                ToastUtil.showToast(PostActivity.this,"发布失败");
                            }
                        }
                    });
                }else{
                    //带图片
                    model.uploadMessage(this,requestBean,bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<String>(mContext,true){
                        @Override
                        public void onNext(String s) {
                            super.onNext(s);
                            if (TextUtils.isEmpty(s)){
                                ToastUtil.showToast(PostActivity.this,"发布成功");
                                finish();
                            }else {
                                ToastUtil.showToast(PostActivity.this,"发布失败");
                            }
                        }
                    });
                }

                break;
        }
    }

}
