package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.PicBean;

/**
 * Created by vera on 2017/3/30 0030.
 */

public class SimpleRecyclerCardAdapter  extends RvAdapter<PicBean> {

    public SimpleRecyclerCardAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_photo;
    }

    @Override
    protected RvViewHolder<PicBean> getViewHolder(int viewType, View view) {
        return new SimpleViewHolder(view);
    }

     class SimpleViewHolder extends RvViewHolder<PicBean> {
        @BindView(R.id.item_img)
        ImageView ivPhoto;

        public SimpleViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData(int position, PicBean bean) {
            ImageLoader.getInstance().displayImage(bean.getThumbnail_pic(),ivPhoto);
        }
    }
}
