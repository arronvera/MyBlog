package code.vera.myblog.view.other;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on 2017/3/30 0030.
 */

public class BigPhotoView extends BaseView {
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);

    }

    public void showBigPhoto(String url) {
        ImageLoader.getInstance().displayImage(url,ivPhoto);
    }
}
