package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.bean.PicBean;
import code.vera.myblog.listener.OnItemClickListener;

/**
 * Created by vera on 2017/3/30 0030.
 */

public class SimpleRecyclerCardAdapter  extends RvAdapter<PicBean> {
    private OnItemClickListener onItemClickListener;
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
        public void onBindData(final int position, PicBean bean) {
            ImageLoader.getInstance().displayImage(bean.getThumbnail_pic(),ivPhoto);
            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClickListener(view,position);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
