package code.vera.myblog.adapter;

import android.content.Context;
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
import code.vera.myblog.view.CircleImageView;
import ww.com.core.Debug;

/**
 * Created by vera on 2017/2/7 0007.
 */

public class HomeAdapter extends RvAdapter<StatusesBean>{
    private Context context;
    private NineGridImageViewAdapter<PicBean>adapter;
    public HomeAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_home_weib;
    }

    @Override
    protected RvViewHolder<StatusesBean> getViewHolder(int viewType, View view) {
        return new HomeViewHolder(view);
    }
    class HomeViewHolder extends RvViewHolder<StatusesBean> {
        @BindView(R.id.tv_item_text)
        TextView tvContent;
        @BindView(R.id.tv_item_name)
        TextView tvName;
        @BindView(R.id.tv_item_time)
        TextView tvTime;
        @BindView(R.id.crv_item_photo)
        CircleImageView civPhoto;
        @BindView(R.id.nineGridImageView)
        NineGridImageView nineGridImageView;
        public HomeViewHolder(View itemView) {
            super(itemView);
            adapter=new NineGridImageViewAdapter<PicBean>() {
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, PicBean s) {
                    ImageLoader.getInstance().displayImage(s.getThumbnail_pic(), imageView, BaseApplication
                            .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
                }
            };
            nineGridImageView.setAdapter(adapter);
        }

        @Override
        public void onBindData(int position, StatusesBean bean) {
            tvContent.setText(bean.getText());//内容
            //todo 时间解析
            tvTime.setText(bean.getCreated_at());
            Debug.d("bean="+bean.toString());
            if (bean.getUserBean()!=null){
                tvName.setText(bean.getUserBean().getName());//用户名
                ImageLoader.getInstance().displayImage(bean.getUserBean().getProfile_image_url(), civPhoto, BaseApplication
                        .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
            }else {
                civPhoto.setImageResource(R.mipmap.ic_user_default);
                tvName.setText("未知");
            }
            //九宫格图片
            if (bean.getPic_list()!=null&&bean.getPic_list().size()!=0){
                nineGridImageView.setImagesData(bean.getPic_list());
            }else {
                nineGridImageView.setVisibility(View.GONE);
            }

        }
    }
}
