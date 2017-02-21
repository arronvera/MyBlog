package code.vera.myblog.view.pic;

import android.support.annotation.NonNull;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.view.base.BaseView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by vera on 2017/2/20 0020.
 */

public class PictureView extends BaseView {
    @BindView(R.id.photoview)
    PhotoView photoView;

    private PhotoViewAttacher photoViewAttacher;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        photoViewAttacher=new PhotoViewAttacher(photoView,true);
    }
    public void showPic(String url){
        ImageLoader.getInstance().displayImage(url, photoView, BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_user_default));
    }
}
