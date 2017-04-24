package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.utils.PictureUtils;
import code.vera.myblog.utils.ToastUtil;
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
                Bitmap bitmap=view.getLoadBitmap();
                if (bitmap!=null){
                    PictureUtils.savePic(bitmap,mContext);
                }else {
                    ToastUtil.showToast(mContext,"保存失败");
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
    public static void start(Context context, Bundle bundle){
        Intent intent=new Intent(context,BigPhotoActivity.class);
        intent.putExtra(PARAM_PHOTO,bundle.getString(PARAM_PHOTO));
        context.startActivity(intent);
    }

}
