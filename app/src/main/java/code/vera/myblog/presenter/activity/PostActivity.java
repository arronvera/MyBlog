package code.vera.myblog.presenter.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.trello.rxlifecycle.ActivityEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import code.vera.myblog.R;
import code.vera.myblog.bean.CommentRequestBean;
import code.vera.myblog.bean.Emoji;
import code.vera.myblog.bean.PostBean;
import code.vera.myblog.bean.SortBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.callback.FragmentCallBack;
import code.vera.myblog.config.Constants;
import code.vera.myblog.model.PostModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.presenter.fragment.other.AtSomebodyFragment;
import code.vera.myblog.presenter.fragment.other.EmojFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.DialogUtils;
import code.vera.myblog.utils.EmojiUtil;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.PostView;


/**
 * 发布
 */
public class PostActivity extends PresenterActivity<PostView, PostModel> implements EmojFragment.OnEmojiClickListener,FragmentCallBack {
    public static final int TAKE_PICTURE=0025;
    public static final int   CHOOSE_PICTURE=0026;
    private PostBean postBean;
    private CommentRequestBean commentRequestBean;
    private int type;
    private String picPath;
    private List<Fragment> fragments=new ArrayList<>();
    private  boolean isShowEmoj=false;//是否表情已经显示
    private  boolean isShowFriend=false;//是否好友已经显示
    private static final int REQUEST_CODE = 732;

    private ArrayList<String> results = new ArrayList<>();
    private EmojFragment emojFragment;//表情
    private AtSomebodyFragment atSomebodyFragment;//好友
    private StatusesBean statusesBean;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_post;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Intent intent = getIntent();
        type = intent.getIntExtra("type", -1);
        statusesBean= (StatusesBean) intent.getSerializableExtra("StatusesBean");
        if (type != -1) {
            view.showTitleAndHint(type);
        }
        if (statusesBean!=null){
            view.showStatusesBean(statusesBean);
        }

        atSomebodyFragment = AtSomebodyFragment.getInstance();
        addListener();
    }

    private void addListener() {
        //
        atSomebodyFragment.setFragmentCallBack(this);
    }

    @OnClick({R.id.tv_cancle, R.id.iv_repost,R.id.iv_choose_pic,R.id.iv_emotion,R.id.iv_at,R.id.iv_topic})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle://取消
                if (isShowFriend){
                    hideFriendFragment();
                    isShowFriend=false;
                    view.setTitle("分享圈子");
//                    view.setSendBtnVisible(true);
                    return;
                }
                if (!TextUtils.isEmpty(view.getEditStr())){
                    //如果有内容  提示保存到草稿箱
                    DialogUtils.showDialog(this, "", "是否保存到草稿箱?", "是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //todo 保存到草稿箱
                        }
                    }, "否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                }else {
                    finish();
                }
                break;
            case R.id.iv_repost://发送
                String msg=view.getEditStr();
                if (TextUtils.isEmpty(msg)){
                    return;
                }
                switch (type){
                    case Constants.COMMENT_TYPE:
                        comment();
                        break;
                    case Constants.REPOST_TYPE:
                        repost();
                        break;
                    default://发布新的
                        postBean = new PostBean();
                        postBean.setStatus(msg);
                        postBean.setPostStatus(Constants.POST_STATUS_NEW);
                        if (TextUtils.isEmpty(postBean.getPic())) {
                            //仅发文字
                            upDate();
                        } else {
                            //带图片
                            upLoad();
                        }
                        break;
                }
                break;
            case R.id.iv_choose_pic://选择图片
                showChosePicDialog();
                break;
            case R.id.iv_emotion://表情
                if (!isShowEmoj){
                    emojFragment = EmojFragment.Instance();
                    getSupportFragmentManager().beginTransaction().add(R.id.ll_container,emojFragment).commit();
                    isShowEmoj=true;
                }else{
                    //隐藏
                    getSupportFragmentManager().beginTransaction().remove(emojFragment).commit();
                    isShowEmoj=false;
                }
                break;
            case R.id.iv_at://at好友
                if (!isShowFriend){
                    getSupportFragmentManager().
                            beginTransaction().
                            setCustomAnimations(R.anim.pop_anim_in,R.anim.pop_anim_out)//动画
                            .add(R.id.rl_all_container,atSomebodyFragment).commit();
                    isShowFriend=true;
                    view.setTitle("好友");//设置标题
//                    view.setSendBtnVisible(false);//发送按钮不可见
                }
                break;
            case R.id.iv_topic://插入话题
                view.getEt().append("##");
                //移动光标到中间
                view.getEt().setSelection(view.getEditStr().indexOf("#")+1);
                //todo
                break;
        }
    }

    public void hideFriendFragment() {
        getSupportFragmentManager().beginTransaction().hide(atSomebodyFragment).commit();
    }

    private void showChosePicDialog() {
        new AlertDialog.Builder(this)
                .setItems(new String[]{"拍照", "图库"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0://拍照
                                //打开系统拍照程序，选择拍照图片
                                takePic();
                                break;
                            case 1://图库
                                //打开图库程序，选择图片
                                choosePic();
                                break;
                        }
                    }
                }) .show();
    }

    /**
     * 转发
     */
    private void repost() {


    }

    /**
     * 评论
     */
    private void comment() {
        commentRequestBean=new CommentRequestBean();
        commentRequestBean.setId(statusesBean.getId());
        commentRequestBean.setComment(view.getEditStr());
//        commentRequestBean.setComment_ori();
        model.commentMessage(this, commentRequestBean, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(PostActivity.this, "评论成功");
                    finish();
                } else {
                    ToastUtil.showToast(PostActivity.this, "评论失败");
                }
            }
        });
    }

    private void upLoad() {
        model.uploadMessage(this, postBean, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(PostActivity.this, "发布成功");
                    finish();
                } else {
                    ToastUtil.showToast(PostActivity.this, "发布失败");
                }
            }
        });
    }

    private void upDate() {
        model.updateMessage(this, postBean, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(PostActivity.this, "发布成功");
                    finish();
                } else {
                    ToastUtil.showToast(PostActivity.this, "发布失败");
                }
            }
        });
    }


    /**
     * 拍照
     */
    private void takePic() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            picPath = outFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, PostActivity.TAKE_PICTURE);
        } else {
            Toast.makeText(this, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 选择图片
     */
    private void choosePic() {
//        Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
//        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(openAlbumIntent, PostActivity.CHOOSE_PICTURE);
        RxGalleryFinal.with(this)
                .image()
                .multiple()
                .maxSize(9)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent resultEvent) throws Exception {
                        view.showPhotos(resultEvent.getResult());
//                        Debug.d("已选择" + resultEvent.getResult().size() +"张图片");
                        Toast.makeText(getBaseContext(), "已选择" + resultEvent.getResult().size() +"张图片", Toast.LENGTH_SHORT).show();
                    }
                }).openGallery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onEmojiDelete() {
        //表情删除
        String text = view.getEditStr();
        if (text.isEmpty()) {
            return;
        }
        if ("]".equals(text.substring(text.length() - 1, text.length()))) {
            int index = text.lastIndexOf("[");
            if (index == -1) {
                int action = KeyEvent.ACTION_DOWN;
                int code = KeyEvent.KEYCODE_DEL;
                KeyEvent event = new KeyEvent(action, code);
                view.getEt().onKeyDown(KeyEvent.KEYCODE_DEL, event);
                displayTextView();
                return;
            }
            view.getEt().getText().delete(index, text.length());
            displayTextView();
            return;
        }
        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        view.getEt().onKeyDown(KeyEvent.KEYCODE_DEL, event);
        displayTextView();
    }

    @Override
    public void onEmojiClick(Emoji emoji) {
        //表情点击
        if (emoji != null) {
            //获取光标位置
            int index = view.getEt().getSelectionEnd();
            Editable editable = view.getEt().getEditableText();
            if (index < 0) {
                editable.append(emoji.getValue());
            } else {
                editable.insert(index, emoji.getValue());
            }
        }
        displayTextView();
    }

    /**
     * 显示
     */
    private void displayTextView() {
        try {
            EmojiUtil.handlerEmojiText(view.getEt(), view.getEditStr(), this);
            //光标移动到最后
            view.getEt().setSelection(view.getEditStr().length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callbackFriend(Bundle arg) {
        //好友回调
        getSupportFragmentManager().beginTransaction().hide(atSomebodyFragment).commit();
        SortBean sortBean= (SortBean) arg.getSerializable("sort_bean");
        view.setTitle("分享圈子");
//        view.setSendBtnVisible(true);
        //添加字符
        if (sortBean!=null){
            view.addStr("@"+sortBean.getName()+" ");
        }
    }
}
