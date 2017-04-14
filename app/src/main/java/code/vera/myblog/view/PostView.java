package code.vera.myblog.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.R;
import code.vera.myblog.bean.PostBean;
import code.vera.myblog.bean.home.RetweetedStatusBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.utils.HomeUtils;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class PostView extends BaseView {
    @BindView(R.id.et_text)
    EditText etMessage;//内容
    @BindView(R.id.tv_text_num)
    TextView tvNum;
    @BindView(R.id.tv_post_title)
    TextView tvTitle;//标题
    @BindView(R.id.iv_choose_pic)
    ImageView ivChoosePic;//选择图片
    @BindView(R.id.iv_repost)
    ImageView ivRepost;
    @BindView(R.id.tv_cancle)
    TextView tvCancel;//取消

    @BindView(R.id.ll_Origina_Layout)
    LinearLayout llOri;
    @BindView(R.id.tv_ori_content)
    TextView tvOriContent;
    @BindView(R.id.iv_original_photo)
    ImageView ivOriPhoto;
    @BindView(R.id.tv_ori_name)
    TextView tvOriName;
    @BindView(R.id.ll_gallery)
    LinearLayout llGallery;//选择的图片
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.iv_close_location)
    ImageView ivCloseLocation;
    @BindView(R.id.tv_public)
    TextView tvAuthority;
    @BindView(R.id.ll_authority)
    LinearLayout llAuthority;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.ll_checkbox)
    LinearLayout llIfOriginal;
    @BindView(R.id.cb_if_original)
    CheckBox cbIfOriginal;
//    @BindView(R.id.iv_choose_pic)
//    ImageView ivChoosPic;






    private Context context;
    private TextWatcher textWatcher;
    private CharSequence temp;
    private int editStart;
    private int editEnd;
    private View galleryView;
    private ImageView ivItemGallery;
    private ImageView ivItemDelete;
    private int comment_ori=0;//是否评论原作者

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        context = view.getContext();
        //文字改变监听
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = etMessage.getSelectionStart();
                editEnd = etMessage.getSelectionEnd();
                tvNum.setText(temp.length()+"");
                if (temp.length() > 140) {
                    Toast.makeText(context, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                    //改变颜色
                    tvNum.setTextColor(Color.parseColor("#ff8162"));
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    etMessage.setText(s);
                    etMessage.setSelection(tempSelection);
                }
            }
        };
        etMessage.addTextChangedListener(textWatcher);
        cbIfOriginal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    comment_ori=1;

                }
            }
        });
    }

    public int getComment_ori() {
        return comment_ori;
    }

    private void initGallery() {
         galleryView = LayoutInflater.from(context).inflate(R.layout.item_gallery, llGallery, false);
         ivItemGallery = (ImageView) galleryView.findViewById(R.id.iv_item_gallery);//图片
         ivItemDelete = (ImageView) galleryView.findViewById(R.id.iv_delete);//删除
    }

    public String getEditStr(){
        return etMessage.getText().toString();
    }

    /*
     * 显示标题和提示
     */
    public void showTitleAndHint(int type) {
        switch (type){
            case Constants.POST_TYPE_COMMENT:
                tvTitle.setText("发评论");
                etMessage.setHint("写评论");
                llAuthority.setVisibility(View.GONE);
                llLocation.setVisibility(View.GONE);
                llIfOriginal.setVisibility(View.VISIBLE);
                cbIfOriginal.setText(R.string.str_if_original_weibo_comment);
                break;
            case Constants.POST_TYPE_REPOST:
                tvTitle.setText("转发");
                etMessage.setHint("转发");
                llAuthority.setVisibility(View.GONE);
                llLocation.setVisibility(View.GONE);
                llIfOriginal.setVisibility(View.GONE);
                break;
            case Constants.POST_TYPE_REPLY_COMMENT:
                tvTitle.setText("回复评论");
                etMessage.setHint("回复评论");
                llAuthority.setVisibility(View.GONE);
                llLocation.setVisibility(View.GONE);
                llIfOriginal.setVisibility(View.GONE);
//                ivChoosPic.setEnabled(false);

                break;
            default:
                tvTitle.setText("分享圈子");
                etMessage.setHint("分享你周围的圈子");
                llIfOriginal.setVisibility(View.GONE);
                break;
        }
    }
    public EditText getEt(){
        return etMessage;
    }

    /**
     * 追加
     * @param str
     */
    public void addStr(String str){
        etMessage.append(str);
    }

    /**
     * 显示标题
     * @param s
     */
    public void setTitle(String s) {
        tvTitle.setText(s);
    }

    public void showStatusesBean(StatusesBean statusesBean) {
        llOri.setVisibility(View.VISIBLE);
        if (statusesBean!=null&&statusesBean.getRetweetedStatusBean()!=null){//有原weib
            RetweetedStatusBean retweetedStatusBean=statusesBean.getRetweetedStatusBean();
            tvOriContent.setText(retweetedStatusBean.getText());
            tvOriName.setText(retweetedStatusBean.getUserbean().getName());
            ImageLoader.getInstance().displayImage(retweetedStatusBean.getUserbean().getProfile_image_url(), ivOriPhoto, BaseApplication
                    .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
            String text="//@"+statusesBean.getUserBean().getName()+":"+statusesBean.getText();
            //设置样式，但是不可以点击
            etMessage.setText( HomeUtils.getWeiBoContent(null,null,null,text,context,0,etMessage));
            etMessage.setSelection(0);//移动光标
        }else if (statusesBean.getRetweetedStatusBean()==null){
            tvOriContent.setText(statusesBean.getText());
            tvOriName.setText(statusesBean.getUserBean().getName());
            ImageLoader.getInstance().displayImage(statusesBean.getUserBean().getProfile_image_url(), ivOriPhoto, BaseApplication
                    .getDisplayImageOptions(R.mipmap.ic_user_default));//头像
        }

    }

    /**
     * 显示选择的图片
     */
    public void showPhotos(List<MediaBean> mediaBeanList) {
        llGallery.setOrientation(LinearLayout.HORIZONTAL);
        for (int i=0;i<mediaBeanList.size();i++){
            initGallery();
            Bitmap bitmap= BitmapFactory.decodeFile(mediaBeanList.get(i).getOriginalPath());
            ivItemGallery.setImageBitmap(bitmap);
            llGallery.addView(galleryView);
            llGallery.setTag(i);
        }
    }

    public void showPhoto(String picPath) {
        Bitmap bitmap= BitmapFactory.decodeFile(picPath);
        ivItemGallery.setImageBitmap(bitmap);
        llGallery.addView(galleryView);
    }

    public void showPostBean(PostBean postBean) {
        //todo
    }

    public void showAddress(String address) {
        if (!TextUtils.isEmpty(address)){
            tvLocation.setText(address);
            tvLocation.setTextColor(Color.parseColor("#ff8162"));
            ivCloseLocation.setVisibility(View.VISIBLE);
        }else {
            tvLocation.setText("查看位置");
            tvLocation.setTextColor(Color.parseColor("#909090"));
            ivCloseLocation.setVisibility(View.INVISIBLE);

        }
    }

    public void setVisible(int visible) {
        switch (visible){
            case Constants.VISIBLE_ALL:
                tvAuthority.setText("公开");
                break;
            case Constants.VISIBLE_FRIENDS:
                tvAuthority.setText("好友可见");
                break;
            case Constants.VISIBLE_SELF:
                tvAuthority.setText("自己可见");
                break;
        }
    }
}
