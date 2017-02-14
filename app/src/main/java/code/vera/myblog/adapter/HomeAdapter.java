package code.vera.myblog.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
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
import code.vera.myblog.utils.TimeUtils;
import code.vera.myblog.view.CircleImageView;
import code.vera.myblog.view.other.CustomClickableSpan;
import code.vera.myblog.view.other.CustomLinkMovement;

//import static com.sina.weibo.sdk.openapi.legacy.AccountAPI.CAPITAL.R;

/**
 * 首页适配器
 * Created by vera on 2017/2/7 0007.
 */

public class HomeAdapter extends RvAdapter<StatusesBean>{
    private Context context;
    private NineGridImageViewAdapter<PicBean>adapter;
    private CustomClickableSpan ccs;

    private OnItemRepostListener onItemRepostListener;//转发监听
    private OnItemCommentListener onItemCommentListener;//评论监听
    private OnItemLikeListener onItemLikeListener;//喜欢监听
    private OnItemOriginalListener onItemOriginalListener;//原weib监听


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

        public HomeViewHolder(View itemView) {
            super(itemView);
            adapter=new NineGridImageViewAdapter<PicBean>() {
                //显示
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, PicBean s) {
                    ImageLoader.getInstance().displayImage(s.getThumbnail_pic(), imageView, BaseApplication
                            .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
                }
                //点击
                @Override
                protected void onItemImageClick(Context context, int index, List<PicBean> list) {
                    super.onItemImageClick(context, index, list);

                }
            };
            nineGridImageView.setAdapter(adapter);
            oriNineGirdImageView.setAdapter(adapter);
        }

        @Override
        public void onBindData(final int position, StatusesBean bean) {
            String timeStr=bean.getCreated_at();
            tvTime.setText(TimeUtils.dateTransfer(timeStr));
            //内容
            String content=bean.getText();
            SpannableString spannable = new SpannableString(content);
            ccs = new CustomClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //todo
                }
            };
            int start=content.indexOf("http://");
            int end=content.indexOf(" ",start);
            if (start!=-1){//如果有链接
                if (end>start){
                    spannable.setSpan(ccs, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tvContent.setText(spannable);
                    tvContent.setHighlightColor(Color.GRAY);
                    tvContent.setMovementMethod(new CustomLinkMovement(ccs));
                }else {
                    tvContent.setText(content);
                }
            }else {
                tvContent.setText(content);
            }
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
                nineGridImageView.setVisibility(View.VISIBLE);
            }else {
                nineGridImageView.setVisibility(View.GONE);
            }
            //微博原作者-有的才返回
            if (bean.getRetweetedStatusBean()!=null){
                RetweetedStatusBean statusBean=bean.getRetweetedStatusBean();
                llAuthorInfo.setVisibility(View.VISIBLE);
                tvAuthorText.setText("@"+statusBean.getUserbean().getName()+":"+bean.getText());
                if (statusBean.getPic_list()!=null&&statusBean.getPic_list().size()!=0){
                    oriNineGirdImageView.setImagesData(statusBean.getPic_list());
                    oriNineGirdImageView.setVisibility(View.VISIBLE);
                }else {
                    oriNineGirdImageView.setVisibility(View.GONE);
                }
            }else {
                llAuthorInfo.setVisibility(View.GONE);
            }
            //转发
            if (bean.getReposts_count()!=0){
              tvRepost.setText(bean.getReposts_count()+"");
            }
            //评论
            if (bean.getComments_count()!=0){
                tvComment.setText(bean.getComments_count()+"");
            }
            //喜欢
            if (bean.getAttitudes_count()!=0){
                tvLike.setText(bean.getAttitudes_count()+"");
            }
            //监听
            rlRepost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemRepostListener.onItemRepostListener(v, position);
                }
            });
            rlComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemCommentListener.onItemCommentListener(v, position);
                }
            });
            rlLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemLikeListener.onItemLikeListener(v, position);
                }
            });
            llAuthorInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemOriginalListener.onItemOriginalListener(v,position);
                }
            });

        }
    }

    /**
     * 转发监听
     */
    public interface OnItemRepostListener {
        void onItemRepostListener(View v, int pos);
    }

    public void setOnItemRepostListener(OnItemRepostListener onItemActionListener) {
        this.onItemRepostListener = onItemActionListener;
    }
    /**
     *评论监听
     */
    public interface OnItemCommentListener {
        void onItemCommentListener(View v, int pos);
    }

    public void setOnItemCommentListener(OnItemCommentListener onItemCommentListener) {
        this.onItemCommentListener = onItemCommentListener;
    }
    /**
     * 喜欢监听
     */
    public interface OnItemLikeListener {
        void onItemLikeListener(View v, int pos);
    }

    public void setOnItemLikeListener(OnItemLikeListener onItemLikeListener) {
        this.onItemLikeListener = onItemLikeListener;
    }
    /**
     * 原weib监听
     */
    public interface OnItemOriginalListener {
        void onItemOriginalListener(View v, int pos);
    }

    public void setOnItemOriginalListener(OnItemOriginalListener onItemOriginalListener) {
        this.onItemOriginalListener = onItemOriginalListener;
    }
}
