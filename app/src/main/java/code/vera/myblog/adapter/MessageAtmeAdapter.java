package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.utils.TimeUtils;
import code.vera.myblog.view.CircleImageView;

/**
 * Created by vera on 2017/2/14 0014.
 */

public class MessageAtmeAdapter extends RvAdapter<CommentUserBean>{

    public MessageAtmeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_message_at_me;
    }

    @Override
    protected RvViewHolder<CommentUserBean> getViewHolder(int viewType, View view) {
        return new MessageAtmeHolder(view);
    }
    class  MessageAtmeHolder extends RvViewHolder<CommentUserBean>{
        @BindView(R.id.crv_item_photo)
        CircleImageView civPhoto;
        @BindView(R.id.tv_item_name)
        TextView tvName;
        @BindView(R.id.tv_item_time)
        TextView tvTime;
        @BindView(R.id.tv_item_text)
        TextView tvText;
        public MessageAtmeHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, CommentUserBean bean) {
            if (bean.getUserBean()!=null){
                ImageLoader.getInstance().displayImage(bean.getUserBean().getProfile_image_url(), civPhoto, BaseApplication
                        .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
                tvName.setText(bean.getUserBean().getName());

            }
            tvTime.setText(TimeUtils.dateTransfer(bean.getCreated_at()));
            tvText.setText(bean.getText());
        }
    }
}
