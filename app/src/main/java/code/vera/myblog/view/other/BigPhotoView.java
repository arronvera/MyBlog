package code.vera.myblog.view.other;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on 2017/3/30 0030.
 */

public class BigPhotoView extends BaseView {
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    private Bitmap loadBitmap;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);

    }

    public void showBigPhoto(String url) {
        ImageLoader.getInstance().displayImage(url, ivPhoto, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                //开始加载
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
//                photoView.setImageResource(R.drawable.no_pic);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                //加载完成
                ivPhoto.setImageBitmap(bitmap);
                loadBitmap = bitmap;
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        ImageLoader.getInstance().displayImage(url,ivPhoto);
    }

    public Bitmap getLoadBitmap() {
        return loadBitmap;
    }

    public void setLoadBitmap(Bitmap loadBitmap) {
        this.loadBitmap = loadBitmap;
    }
}
