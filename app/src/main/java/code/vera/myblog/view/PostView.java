package code.vera.myblog.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
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
//    @BindView(R.id.iv_repost)
//    Button btnUpload;//发送
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


    private Context context;
    private TextWatcher textWatcher;
    private CharSequence temp;
    private int editStart;
    private int editEnd;
    private View galleryView;
    private ImageView ivItemGallery;
    private ImageView ivItemDelete;

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
        initGallery();
    }

    private void initGallery() {
         galleryView = LayoutInflater.from(context).inflate(R.layout.item_gallery, llGallery, false);
         ivItemGallery = (ImageView) galleryView
                .findViewById(R.id.iv_item_gallery);//图片
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
                break;
            case Constants.POST_TYPE_REPOST:
                tvTitle.setText("转发");
                etMessage.setHint("转发");
                break;
            default:
                tvTitle.setText("分享圈子");
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
        for (int i=0;i<mediaBeanList.size();i++){
            Bitmap bitmap= BitmapFactory.decodeFile(mediaBeanList.get(i).getOriginalPath());
            ivItemGallery.setImageBitmap(bitmap);
            llGallery.addView(galleryView);
//            llGallery.setTag(i);
        }
    }

    public void showPhoto(String picPath) {
        Bitmap bitmap= BitmapFactory.decodeFile(picPath);
        ivItemGallery.setImageBitmap(bitmap);
        llGallery.addView(galleryView);
    }

//    /**
//     * 设置发送按钮是否可见
//     * @param visible
//     */
//    public void setSendBtnVisible(boolean visible){
//        if (visible){
//            ivRepost.setVisibility(View.VISIBLE);
//        }else{
//            ivRepost.setVisibility(View.INVISIBLE);
//        }
//    }
}
