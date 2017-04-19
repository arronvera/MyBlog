package code.vera.myblog.view.pic;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.view.base.BaseView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import ww.com.core.Debug;

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
    private Context context;
    private int duration = 0;
    private Handler handler;
    private static final int WHAT_GIF_FINISH = 0;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        photoViewAttacher = new PhotoViewAttacher(photoView, true);
        context = view.getContext();
//        handler = new Handler(getMainLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case WHAT_GIF_FINISH:
//                        //动画结束
//                        break;
//                }
//            }
//        };
    }

    /**
     * 显示图片
     *
     * @param url
     */
    public void showPic(String url) {
        Debug.d("url=" + url);
        if (url.endsWith(".gif")) {
            Glide.with(context).load(url).asGif().listener(new RequestListener<String, GifDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                    Debug.d(e.getMessage());
                    return false;
                }

                @Override
                public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    //计算动画时长
                    GifDrawable drawable = resource;
                    GifDecoder decoder = drawable.getDecoder();
                    for (int i = 0; i < drawable.getFrameCount(); i++) {
                        duration += decoder.getDelay(i);
                    }
                    //发送延时消息，通知动画结束
//                    handler.sendEmptyMessageDelayed(WHAT_GIF_FINISH, duration);
                    return false;
                }
            }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(photoView);
        } else {
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
                    loadBitmap = bitmap;
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                    ivProgress.setVisibility(View.GONE);

                }
            });
        }

    }

    /**
     * 获取加载的图片
     *
     * @return
     */
    public Bitmap getLoadBitmap() {
        return loadBitmap;
    }
}
