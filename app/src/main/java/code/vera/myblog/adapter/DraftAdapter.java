package code.vera.myblog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.bean.PostBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemDeleteClickListener;
import code.vera.myblog.listener.OnItemSendListener;

/**
 * Created by vera on 2017/2/22 0022.
 */

public class DraftAdapter extends RvAdapter<PostBean> {
    private OnItemDeleteClickListener onItemDeleteClickListener;
    private OnItemSendListener onItemSendListener;

    public DraftAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_draft;
    }

    @Override
    protected RvViewHolder<PostBean> getViewHolder(int viewType, View view) {
        return new DraftViewHolder(view);
    }

    class DraftViewHolder extends RvViewHolder<PostBean> {
        @BindView(R.id.tv_draft_type)
        TextView tvType;
        @BindView(R.id.tv_draft_content)
        TextView tvContent;//内容
        @BindView(R.id.iv_delete)
        ImageView ivDelete;//删除
        @BindView(R.id.iv_send)
        ImageView ivSend;//发送
        @BindView(R.id.ll_Origina_Layout)
        LinearLayout llOriginal;
        @BindView(R.id.iv_original_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_ori_name)
        TextView tvOriName;
        @BindView(R.id.tv_ori_content)
        TextView tvOriContent;


        public DraftViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData(final int position, PostBean bean) {
            switch (bean.getPostStatus()) {
                case Constants.POST_TYPE_COMMENT:
                    tvType.setText("评论");
                    break;
                case Constants.POST_TYPE_REPOST:
                    tvType.setText("转发");
                    break;
                default:
                    tvType.setText("新分享");
                    break;
            }
            tvContent.setText(bean.getStatus());

            if (bean.getOriId() != 0) {
                llOriginal.setVisibility(View.VISIBLE);
                tvOriName.setText(bean.getOriName());
                tvOriContent.setText(bean.getOriStatus());
                ImageLoader.getInstance().displayImage(bean.getOriHeadPhoto(), ivPhoto);
            } else {
                llOriginal.setVisibility(View.GONE);
            }
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemDeleteClickListener != null) {
                        onItemDeleteClickListener.onItemDeleteClickListener(view, position);
                    }
                }
            });
            ivSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemSendListener != null) {
                        onItemSendListener.onItemSendListener(view, position, getItem(position));
                    }
                }
            });
        }
    }

    public void setOnItemDeleteClickListener(OnItemDeleteClickListener onItemDeleteClickListener) {
        this.onItemDeleteClickListener = onItemDeleteClickListener;
    }

    public void setOnItemSendListener(OnItemSendListener onItemSendListener) {
        this.onItemSendListener = onItemSendListener;
    }
}
