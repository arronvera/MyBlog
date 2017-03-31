package code.vera.myblog.presenter.activity;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.other.BigPhotoView;

/**
 * 查看大图
 */
public class BigPhotoActivity extends PresenterActivity<BigPhotoView, VoidModel> {
    public static final String PARAM_PHOTO="big_photo";
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_big_photo;
    }

    @Override
    public void onAttach(View viRoot) {
        super.onAttach(viRoot);
        Intent intent=getIntent();
        String url=intent.getStringExtra(PARAM_PHOTO);
        view.showBigPhoto(url);
    }
    @OnClick({R.id.iv_photo,R.id.btn_save,R.id.btn_cancel})
    public void doClick(View v){
        switch (v.getId()){
            case R.id.iv_photo:
                finish();
                break;
            case R.id.btn_save:
                //todo
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

}