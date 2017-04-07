package code.vera.myblog.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.PicBean;
import code.vera.myblog.bean.home.RetweetedStatusBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemClickListener;
import code.vera.myblog.listener.OnItemCommentListener;
import code.vera.myblog.listener.OnItemConcernListener;
import code.vera.myblog.listener.OnItemHeadPhotoListener;
import code.vera.myblog.listener.OnItemLikeListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemOriginalListener;
import code.vera.myblog.listener.OnItemRepostListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.presenter.activity.PicturesActivity;
import code.vera.myblog.utils.HomeUtils;
import code.vera.myblog.utils.TimeUtils;
import code.vera.myblog.view.CircleImageView;

/**
 * 发现适配器
 * Created by vera on 2017/2/7 0007.
 */

public class FindAdapter extends RvAdapter<StatusesBean> {
    private Context context;
    private NineGridImageViewAdapter<PicBean> adapter;
    //监听-------------
    private OnItemRepostListener onItemRepostListener;//转发监听
    private OnItemCommentListener onItemCommentListener;//评论监听
    private OnItemLikeListener onItemLikeListener;//喜欢监听
    private OnItemOriginalListener onItemOriginalListener;//原weib监听
    private OnItemLinkListener onItemLinkListener;//链接
    private OnItemTopicListener onItemTopicListener;//话题
    private OnItemAtListener onItemAtListener;//at
    //    private OnItemMenuListener onItemMenuListener;
    private OnItemHeadPhotoListener onItemHeadPhotoListener;//头像
    private OnItemClickListener onItemClickListener;
    private OnItemConcernListener onItemConcernListener;

    public FindAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_find_weib;
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
        CircleImageView civPhoto;//头像
        @BindView(R.id.nineGridImageView)
        NineGridImageView nineGridImageView;
        @BindView(R.id.ll_item_author)
        LinearLayout llAuthorInfo;//微博原作者信息
        @BindView(R.id.tv_item_author_info)
        TextView tvAuthorText;
        @BindView(R.id.original_nineGridImageView)
        NineGridImageView oriNineGirdImageView;////微博原图片
        @BindView(R.id.tv_item_repost)
        TextView tvRepost;//转发
        @BindView(R.id.rl_item_repost)
        RelativeLayout rlRepost;
        @BindView(R.id.tv_item_comment)
        TextView tvComment;//评论
        @BindView(R.id.rl_item_comment)
        RelativeLayout rlComment;
        @BindView(R.id.tv_item_like)
        TextView tvLike;//喜欢
        @BindView(R.id.rl_item_like)
        RelativeLayout rlLike;
        @BindView(R.id.iv_like)
        ImageView ivLike;
        @BindView(R.id.btn_item_concern)
        Button btnConcern;
        @BindView(R.id.tv_source)
        TextView tvSource;//来源
        @BindView(R.id.line)
        LinearLayout llLine;
        @BindView(R.id.ll_item_view)
        LinearLayout llItem;//整个itemview

        public HomeViewHolder(View itemView) {
            super(itemView);
            adapter = new NineGridImageViewAdapter<PicBean>() {
                //显示
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, PicBean s) {
                    ImageLoader.getInstance().displayImage(s.getThumbnail_pic(), imageView, BaseApplication
                            .getDisplayImageOptions(R.mipmap.ic_photo_default));
                }

                //点击
                @Override
                protected void onItemImageClick(Context context, int index, List<PicBean> list) {
                    super.onItemImageClick(context, index, list);
                    //跳转到图片
                    Intent intent = new Intent(context, PicturesActivity.class);
                    intent.putExtra("index", index);
                    intent.putExtra("bean", getItem(position));
                    context.startActivity(intent);
                }
            };
            nineGridImageView.setAdapter(adapter);
            oriNineGirdImageView.setAdapter(adapter);
        }

        @Override
        public void onBindData(final int position, StatusesBean bean) {
            //来源
            if (!TextUtils.isEmpty(bean.getSource()) && Html.fromHtml(bean.getSource()) != null) {
                tvSource.setText("来自" + Html.fromHtml(bean.getSource()));
            }
            //时间
            String timeStr = bean.getCreated_at();
            tvTime.setText(TimeUtils.dateTransfer(timeStr));
            //内容
            final String content = bean.getText();
            SpannableStringBuilder spannableString = HomeUtils.getWeiBoContent(onItemAtListener, onItemTopicListener, onItemLinkListener, content, context, position, tvContent);
            tvContent.setText(spannableString);
            if (bean.getUserBean() != null) {
                tvName.setText(bean.getUserBean().getName());//用户名
                ImageLoader.getInstance().displayImage(bean.getUserBean().getProfile_image_url(), civPhoto, BaseApplication
                        .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
            } else {
                civPhoto.setImageResource(R.mipmap.ic_user_default);
                tvName.setText("未知");
            }
            //九宫格图片
            if (bean.getPic_list() != null && bean.getPic_list().size() != 0) {
                nineGridImageView.setImagesData(bean.getPic_list());
                nineGridImageView.setVisibility(View.VISIBLE);
            } else {
                nineGridImageView.setVisibility(View.GONE);
            }
            //微博原作者-有的才返回
            if (bean.getRetweetedStatusBean() != null) {
                //
                llLine.setVisibility(View.VISIBLE);
                RetweetedStatusBean statusBean = bean.getRetweetedStatusBean();
                llAuthorInfo.setVisibility(View.VISIBLE);
                String content_author = "@" + statusBean.getUserbean().getName() + ":" + statusBean.getText();
                SpannableStringBuilder spannableString2 = HomeUtils.getWeiBoContent(onItemAtListener, onItemTopicListener, onItemLinkListener, content_author, context, position, tvAuthorText);
                tvAuthorText.setText(spannableString2);
                if (statusBean.getPic_list() != null && statusBean.getPic_list().size() != 0) {
                    oriNineGirdImageView.setImagesData(statusBean.getPic_list());
                    oriNineGirdImageView.setVisibility(View.VISIBLE);
                } else {
                    oriNineGirdImageView.setVisibility(View.GONE);
                }
            } else {
                llAuthorInfo.setVisibility(View.GONE);
            }
            //转发
            if (bean.getReposts_count() != 0) {
                tvRepost.setText(bean.getReposts_count() + "");
            } else {
                tvRepost.setText("转发");
            }
            //评论
            if (bean.getComments_count() != 0) {
                tvComment.setText(bean.getComments_count() + "");
            } else {
                tvComment.setText("评论");
            }
            //喜欢
            if (bean.getAttitudes_count() != 0) {
                tvLike.setText(bean.getAttitudes_count() + "");
            } else {
                tvLike.setText("喜欢");
            }
            //-----------------------------监听-----------------------
            rlRepost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemRepostListener != null)
                        onItemRepostListener.onItemRepostListener(v, position);
                }
            });
            rlComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCommentListener != null)
                        onItemCommentListener.onItemCommentListener(v, position);
                }
            });
            rlLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemLikeListener != null)
                        onItemLikeListener.onItemLikeListener(v, ivLike, position);
                }
            });
            llAuthorInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemOriginalListener != null)
                        onItemOriginalListener.onItemOriginalListener(v, position);
                }
            });
            btnConcern.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemConcernListener.onItemConcern(v, position);
                }
            });
            civPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemHeadPhotoListener != null)
                        onItemHeadPhotoListener.onItemHeadPhotoListener(v, position);
                }
            });
            llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClickListener(v, position);
                }
            });

        }
    }

    public void setOnItemRepostListener(OnItemRepostListener onItemActionListener) {
        this.onItemRepostListener = onItemActionListener;
    }

    public void setOnItemCommentListener(OnItemCommentListener onItemCommentListener) {
        this.onItemCommentListener = onItemCommentListener;
    }

    public void setOnItemLikeListener(OnItemLikeListener onItemLikeListener) {
        this.onItemLikeListener = onItemLikeListener;
    }

    public void setOnItemOriginalListener(OnItemOriginalListener onItemOriginalListener) {
        this.onItemOriginalListener = onItemOriginalListener;
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

    //    public void setOnItemMenuListener(OnItemMenuListener onItemMenuListener){
//        this.onItemMenuListener=onItemMenuListener;
//    }
    public void setOnItemHeadPhotoListener(OnItemHeadPhotoListener onItemHeadPhotoListener) {
        this.onItemHeadPhotoListener = onItemHeadPhotoListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemConcernListener(OnItemConcernListener onItemConcernListener) {
        this.onItemConcernListener = onItemConcernListener;
    }
}
