package code.vera.myblog.presenter.fragment.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.utils.PictureUtils;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.pic.PictureView;
import ww.com.core.Debug;

import static code.vera.myblog.presenter.activity.PicturesActivity.ACTION_SAVE_PIC;

/**
 * 图片显示
 * Created by vera on 2017/2/20 0020.
 */

public class PictureFragment extends PresenterFragment<PictureView, VoidModel> {
    private String url;
    private PictureSaveBroadCastReceiver broadCastReceiver;
    private byte[] bytes;
    private Handler handler;
    private static final int GIF_SAVE = 3;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_picture;
    }

    public static Fragment newInstance(String url) {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        args.putSerializable("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override

    protected void onAttach() {
        super.onAttach();
        broadCastReceiver = new PictureSaveBroadCastReceiver();
        broadCastReceiver.registRecevier();
        url = getArguments().getString("url");
        if (!TextUtils.isEmpty(url)) {
            view.showPic(url);
        }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GIF_SAVE:
                        PictureUtils.saveGif(bytes, mContext);
                        break;

                }
            }
        };
    }

    class PictureSaveBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //保存图片
            Debug.d("接收---");
            if (url.endsWith(".gif")) {
                //保存gif图片
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            bytes = Glide.with(mContext)
                                    .load(url)
                                    .asGif()
                                    .toBytes()
                                    .into(250, 250)
                                    .get();
                            handler.sendEmptyMessage(GIF_SAVE);
//                            PictureUtils.saveGif(bytes, mContext);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            } else {
                Bitmap bitmap = view.getLoadBitmap();
                if (bitmap == null) {
                    ToastUtil.showToast(mContext, "图片正在加载中哦~不能保存");
                } else {
                    PictureUtils.savePic(bitmap, mContext);
                }
            }

        }

        public void registRecevier() {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_SAVE_PIC);
            mContext.registerReceiver(this, filter);
        }

        public void unRgistRecevier() {
            mContext.unregisterReceiver(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        broadCastReceiver.unRgistRecevier();
    }
}
