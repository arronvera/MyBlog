package code.vera.myblog.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.adapter.TabCommentAdapter;
import code.vera.myblog.bean.PicBean;
import code.vera.myblog.bean.RetweetedStatusBean;
import code.vera.myblog.bean.StatusesBean;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.PicturesActivity;
import code.vera.myblog.utils.HomeUtils;
import code.vera.myblog.utils.TimeUtils;
import code.vera.myblog.view.base.BaseView;
import code.vera.myblog.view.widget.LikeView;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class CommentDetailView extends BaseView {
    @BindView(R.id.tv_content)
    TextView tvContent;//内容
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.crv_photo)
    CircleImageView civPhoto;
    @BindView(R.id.nineGridImageView)
    NineGridImageView nineGridImageView;
    @BindView(R.id.tab_comment_detail)
    TabLayout tabLayout;
    @BindView(R.id.vp_comment_view)
    ViewPager vpComment;
    @BindView(R.id.tv_source)
    TextView tvSource;
    @BindView(R.id.iv_like)
    ImageView ivLike;//喜欢
    @BindView(R.id.iv_shoucang)
    ImageView ivCollection;//收藏
    @BindView(R.id.ll_Original_Layout)
    LinearLayout llOriginal;
    @BindView(R.id.tv_ori_text)
    TextView tvOriginal;


    private LikeView likeView;
    private TabCommentAdapter tabCommentAdapter;
    private NineGridImageViewAdapter<PicBean> adapter;
    private Context context;
    private CommentDetailActivity activity;
    private StatusesBean statusesBean;
    private OnItemAtListener onItemAtListener;
    private OnItemTopicListener onItemTopicListener;
    private OnItemLinkListener onItemLinkListener;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        context=view.getContext();
        likeView=new LikeView(context);

        adapter=new NineGridImageViewAdapter<PicBean>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, PicBean s) {
                ImageLoader.getInstance().displayImage(s.getThumbnail_pic(), imageView, BaseApplication
                        .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<PicBean> list) {
                super.onItemImageClick(context, index, list);
                //点击
                //跳转到图片
                Intent intent=new Intent(context, PicturesActivity.class);
                intent.putExtra("index",index);
                intent.putExtra("bean",statusesBean);
                context.startActivity(intent);
            }
        };
        nineGridImageView.setAdapter(adapter);

    }
    public void showInfo(StatusesBean statusesBean){
        this.statusesBean=statusesBean;
        String text=statusesBean.getText();
        tvContent.setText(HomeUtils.getWeiBoContent(onItemAtListener,onItemTopicListener, onItemLinkListener,text,context,0,tvContent));
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
        //来源
        if (!TextUtils.isEmpty(statusesBean.getSource())&& Html.fromHtml(statusesBean.getSource())!=null){
            tvSource.setText("来自"+ Html.fromHtml(statusesBean.getSource()));
        }
        //收藏
        if (statusesBean.isFavorited()){
            ivCollection.setImageResource(R.mipmap.ic_star_sel);
        }else {
            ivCollection.setImageResource(R.mipmap.ic_star);
        }
        if (statusesBean.getRetweetedStatusBean()!=null){
            RetweetedStatusBean bean=statusesBean.getRetweetedStatusBean();
            llOriginal.setVisibility(View.VISIBLE);
            text="@"+bean.getUserbean().getName()+":"+bean.getText();
            tvOriginal.setText(HomeUtils.getWeiBoContent(onItemAtListener,onItemTopicListener, onItemLinkListener,text,context,0,tvOriginal));
            if (bean.getPic_list()!=null&&bean.getPic_list().size()!=0){
                nineGridImageView.setImagesData(bean.getPic_list());
                nineGridImageView.setVisibility(View.VISIBLE);
            }else {
                nineGridImageView.setVisibility(View.GONE);
            }


        }

    }
    public void showInfo2(RetweetedStatusBean statusesBean){
        tvContent.setText(statusesBean.getText());
        tvName.setText(statusesBean.getUserbean().getName());
        tvTime.setText(TimeUtils.dateTransfer(statusesBean.getCreated_at()));
        ImageLoader.getInstance().displayImage(statusesBean.getUserbean().getProfile_image_url(), civPhoto, BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
        if (statusesBean.getPic_list()!=null&&statusesBean.getPic_list().size()!=0){
            nineGridImageView.setImagesData(statusesBean.getPic_list());
            nineGridImageView.setVisibility(View.VISIBLE);
        }else {
            nineGridImageView.setVisibility(View.GONE);
        }
    }

    public void setAdapter() {
        tabCommentAdapter = new TabCommentAdapter(activity.getSupportFragmentManager(),statusesBean);
        //设置标题
//        tabLayout.getTabAt(Constants.TAB_COMMENT).setText("评论"+statusesBean.getComments_count());
//        tabLayout.getTabAt(Constants.TAB_REPOST).setText("转发"+statusesBean.getReposts_count());
//        tabLayout.getTabAt(Constants.TAB_LIKE).setText("喜欢"+statusesBean.getAttitudes_count());
        //给ViewPager设置适配器
        vpComment.setAdapter(tabCommentAdapter);
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(vpComment);
        //给Tabs设置适配器
        tabLayout.setTabsFromPagerAdapter(tabCommentAdapter);
    }

    public void setActivity(CommentDetailActivity commentDetailActivity) {
        activity=commentDetailActivity;
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


  public void showLikeView() {
        ivLike.setImageResource(R.mipmap.ic_heart_sel);
        likeView.setImage(context.getResources().getDrawable(R.mipmap.ic_heart_sel));
        likeView.show(ivLike);
    }

    public void showCollectionView() {
        ivCollection.setImageResource(R.mipmap.ic_star_sel);
        likeView.setImage(context.getResources().getDrawable(R.mipmap.ic_star_sel));
        likeView.show(ivCollection);
    }

    public void updateView() {

    }
}
