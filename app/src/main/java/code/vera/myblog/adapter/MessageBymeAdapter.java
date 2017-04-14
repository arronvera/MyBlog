package code.vera.myblog.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemDeleteClickListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemOriginalListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.utils.HomeUtils;
import code.vera.myblog.utils.TimeUtils;
import code.vera.myblog.view.CircleImageView;

import static code.vera.myblog.R.id.item_delete;

/**
 * Created by vera on 2017/2/14 0014.
 */

public class MessageBymeAdapter extends RvAdapter<CommentUserBean>{

    private OnItemAtListener onItemAtListener;
    private OnItemTopicListener onItemTopicListener;
    private OnItemLinkListener onItemLinkListener;
    private OnItemOriginalListener onItemOriginalListener;
    private OnItemDeleteClickListener onItemDeleteClickListener;

    public MessageBymeAdapter(Context context) {
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
        CircleImageView civPhoto;//头像
        @BindView(R.id.tv_item_name)
        TextView tvName;//名字
        @BindView(R.id.tv_item_time)
        TextView tvTime;//时间
        @BindView(R.id.tv_item_text)
        TextView tvText;//内容
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;//原作者头像
        @BindView(R.id.tv_ori_text)
        TextView tvOriText;//原作者内容
        private Context context;
        @BindView(R.id.rl_original_info)
        RelativeLayout rlOriginalInfo;//原信息
        @BindView(item_delete)
        TextView tvDelete;
        @BindView(R.id.tv_reply)
        TextView tvReply;


        public MessageAtmeHolder(View itemView) {
            super(itemView);
            context=itemView.getContext();
        }

        @Override
        public void onBindData(final int position, CommentUserBean bean) {
            tvDelete.setVisibility(View.VISIBLE);
            tvReply.setVisibility(View.GONE);
            if (bean.getUserBean()!=null){
                ImageLoader.getInstance().displayImage(bean.getUserBean().getProfile_image_url(), civPhoto, BaseApplication
                        .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
                tvName.setText(bean.getUserBean().getName());
            }
            if (bean.getStatusesBean()!=null){
                if (bean.getStatusesBean().getUserBean()!=null){
                    ImageLoader.getInstance().displayImage(bean.getStatusesBean().getUserBean().getProfile_image_url(), ivPhoto, BaseApplication
                            .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
                    ivPhoto.setVisibility(View.VISIBLE);
                }else {
                    ivPhoto.setVisibility(View.GONE);
                }
                tvOriText.setText(bean.getStatusesBean().getText());
            }
            tvTime.setText(TimeUtils.dateTransfer(bean.getCreated_at()));
            //---------------内容
            String content=bean.getText();
            SpannableStringBuilder spannableString= HomeUtils.getWeiBoContent(onItemAtListener,onItemTopicListener,onItemLinkListener,content,context,position,tvText);
            tvText.setText(spannableString);
            //--------------监听
            rlOriginalInfo.setOnClickListener(new View.OnClickListener() {
                @Override
             public void onClick(View v) {
                    if (onItemOriginalListener!=null)
                    onItemOriginalListener.onItemOriginalListener(v,position);
                }
            });
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemDeleteClickListener!=null){
                        onItemDeleteClickListener.onItemDeleteClickListener(view,position);
                    }
                }
            });

        }
    }

    public void setOnItemLinkListener(OnItemLinkListener onItemLinkListener) {
        this.onItemLinkListener = onItemLinkListener;
    }

    public void setOnItemTopicListener(OnItemTopicListener onItemTopicListener) {
        this.onItemTopicListener = onItemTopicListener;
    }

    public void setOnItemAtListener(OnItemAtListener onItemAtListener) {
        this.onItemAtListener = onItemAtListener;
    }
    public void setOnItemOriginalListener(OnItemOriginalListener onItemOriginalListener) {
        this.onItemOriginalListener = onItemOriginalListener;
    }
    public void setOnItemDeleteClickListener(OnItemDeleteClickListener onItemDeleteClickListener) {
        this.onItemDeleteClickListener = onItemDeleteClickListener;
    }
}
