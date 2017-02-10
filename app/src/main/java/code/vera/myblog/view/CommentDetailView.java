package code.vera.myblog.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.PicBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.utils.TimeUtils;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class CommentDetailView extends BaseView {
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.crv_photo)
    CircleImageView civPhoto;
    @BindView(R.id.nineGridImageView)
    NineGridImageView nineGridImageView;
    private NineGridImageViewAdapter<PicBean> adapter;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        adapter=new NineGridImageViewAdapter<PicBean>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, PicBean s) {
                ImageLoader.getInstance().displayImage(s.getThumbnail_pic(), imageView, BaseApplication
                        .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
            }
        };
        nineGridImageView.setAdapter(adapter);
    }
    public void showInfo(StatusesBean statusesBean){
        tvContent.setText(statusesBean.getText());
        tvName.setText(statusesBean.getUserBean().getName());
        tvTime.setText(TimeUtils.dateTransfer(statusesBean.getCreated_at()));
        ImageLoader.getInstance().displayImage(statusesBean.getUserBean().getProfile_image_url(), civPhoto, BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
        //九宫格图片
        if (statusesBean.getPic_list()!=null&&statusesBean.getPic_list().size()!=0){
            nineGridImageView.setImagesData(statusesBean.getPic_list());
            nineGridImageView.setVisibility(View.VISIBLE);
        }else {
            nineGridImageView.setVisibility(View.GONE);
        }
    }
}
