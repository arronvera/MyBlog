package code.vera.myblog.view.other;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.view.base.BaseView;


/**
 * Created by vera on 2017/3/21 0021.
 */

public class SetView extends BaseView {
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;


    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }

    public void setCache(Double size) {
        tvCache.setText(size+"MB");
    }

    public void showUser(UserInfoBean userInfoBean) {
        ImageLoader.getInstance().displayImage(userInfoBean.getProfile_image_url(), ivPhoto, BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
    }
}
