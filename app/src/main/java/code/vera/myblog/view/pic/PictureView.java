package code.vera.myblog.view.pic;

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
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by vera on 2017/2/20 0020.
 */

public class PictureView extends BaseView {
    @BindView(R.id.photoview)
     PhotoView photoView;//图片
    @BindView(R.id.iv_progress)
    ImageView ivProgress;//进度条
    private PhotoViewAttacher photoViewAttacher;
    private Bitmap loadBitmap;//加载图片

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        photoViewAttacher=new PhotoViewAttacher(photoView,true);
    }

    /**
     * 显示图片
     * @param url
     */
    public void showPic(String url){
        if (url.endsWith(".gif")){

        }
        ImageLoader.getInstance().displayImage(url, photoView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                //开始加载
                ivProgress.setVisibility(View.VISIBLE);
                ivProgress.setImageResource(R.drawable.loading_anim);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                ivProgress.setVisibility(View.GONE);
//                photoView.setImageResource(R.drawable.no_pic);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                //加载完成
                ivProgress.setVisibility(View.GONE);
                photoView.setImageBitmap(bitmap);
                loadBitmap=bitmap;
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                ivProgress.setVisibility(View.GONE);

            }
        });
    }

    /**
     * 获取加载的图片
     * @return
     */
    public Bitmap getLoadBitmap() {
        return loadBitmap;
    }
}
