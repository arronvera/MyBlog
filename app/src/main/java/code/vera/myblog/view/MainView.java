package code.vera.myblog.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on 2017/3/2 0002.
 */

public class MainView extends BaseView {
    @BindView(R.id.iv_main_photo)
    ImageView ivPhoto;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);

    }

    public void showUser(UserInfoBean userInfoBean) {
        ImageLoader.getInstance().displayImage(userInfoBean.getProfile_image_url(),ivPhoto);
    }
}
