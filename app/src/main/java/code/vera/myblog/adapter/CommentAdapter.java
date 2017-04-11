package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.CommentUserBean;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemLikeListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.utils.HomeUtils;
import code.vera.myblog.utils.TimeUtils;
import code.vera.myblog.view.CircleImageView;

/**
 * Created by vera on 2017/2/10 0010.
 */

public class CommentAdapter extends RvAdapter<CommentUserBean> {
    private OnItemAtListener onItemAtListener;
    private OnItemTopicListener onItemTopicListener;
    private OnItemLinkListener onItemLinkListener;
    private OnItemLikeListener onItemLikeListener;

    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_comment_user;
    }

    @Override
    protected RvViewHolder<CommentUserBean> getViewHolder(int viewType, View view) {
        return new CommentViewHolder(view);
    }
    class CommentViewHolder extends RvViewHolder<CommentUserBean> {

        @BindView(R.id.item_comment_user_name)
        TextView tvName;
        @BindView(R.id.item_comment_user_text)
        TextView tvContent;
        @BindView(R.id.item_comment_user_num)
        TextView tvLikeNum;
        @BindView(R.id.item_comment_user_time)
        TextView tvTime;
        @BindView(R.id.item_comment_user_photo)
        CircleImageView civPhoto;
        @BindView(R.id.iv_heart)
        ImageView ivHeart;


        public CommentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(final int position, CommentUserBean bean) {
            if (bean.getUserBean()!=null){
                tvName.setText(bean.getUserBean().getName());
                tvLikeNum.setText(bean.getUserBean().getFavourites_count()+"");
                ImageLoader.getInstance().displayImage(bean.getUserBean().getProfile_image_url(), civPhoto, BaseApplication
                        .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
            }
            String text=bean.getText();//内容
            tvContent.setText(HomeUtils.getWeiBoContent(onItemAtListener,onItemTopicListener,onItemLinkListener,text,getContext(),0,tvContent));
            tvTime.setText(TimeUtils.dateTransfer(bean.getCreated_at()));
            ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemLikeListener!=null){
                        onItemLikeListener.onItemLikeListener(view,ivHeart,position);
                    }
                }
            });
        }
    }
    public void setOnItemAtListener(OnItemAtListener onItemAtListener){
        this.onItemAtListener=onItemAtListener;
    }
    public void setOnItemTopicListener(OnItemTopicListener onItemTopicListener){
        this.onItemTopicListener=onItemTopicListener;
    }
    public void setOnItemLinkListener(OnItemLinkListener onItemLinkListener){
        this.onItemLinkListener=onItemLinkListener;
    }
    public void setOnItemLikeListener(OnItemLikeListener onItemLikeListener){
        this.onItemLikeListener=onItemLikeListener;
    }
}
