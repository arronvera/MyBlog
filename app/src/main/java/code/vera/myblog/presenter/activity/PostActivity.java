package code.vera.myblog.presenter.activity;

import android.view.View;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.model.PostModel;
import code.vera.myblog.presenter.PresenterActivity;
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

    @OnClick({R.id.tv_cancle})
    public void doClick(View v){
        switch (v.getId()){
            case R.id.tv_cancle://取消
                finish();
                break;
            case R.id.btn_post://发送
//                model.Post
                break;
        }
    }

}
