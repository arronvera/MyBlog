package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
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
import code.vera.myblog.db.PostDao;
import code.vera.myblog.model.PostModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.presenter.fragment.other.AtSomebodyFragment;
import code.vera.myblog.presenter.fragment.other.EmojFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.DialogUtils;
import code.vera.myblog.utils.EmojiUtil;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.PostView;
import ww.com.core.Debug;


/**
 * 发布
 */
public class PostActivity extends PresenterActivity<PostView, PostModel> implements
        EmojFragment.OnEmojiClickListener, FragmentCallBack {
    public static final int TAKE_PICTURE = 0025;
    public static final int CHOOSE_PICTURE = 0026;
    private static final int REQUEST_CODE = 732;
    public static final String PARAM_STATUS_BEAN = "StatusesBean";
    public static final String PARAM_POST_TYPE = "type";
    public static final String PARAM_POST_BEAN = "postbean";

    private int type;
    private String picPath;
    private boolean isShowEmoj = false;//是否表情已经显示
    private boolean isShowFriend = false;//是否好友已经显示
    private EmojFragment emojFragment;//表情
    private AtSomebodyFragment atSomebodyFragment;//好友
    private StatusesBean statusesBean;
    private PostBean postBean;
    private CommentRequestBean commentRequestBean;
    private List<MediaBean> pictureList = new ArrayList<>();
    //数据库
    PostDao postDao;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_post;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Intent intent = getIntent();
        type = intent.getIntExtra(PARAM_POST_TYPE, -1);
        statusesBean = (StatusesBean) intent.getSerializableExtra(PARAM_STATUS_BEAN);
        if (type != -1) {
            view.showTitleAndHint(type);
            Debug.d("type==============" + type);
        }
        if (statusesBean != null) {
            view.showStatusesBean(statusesBean);
        }
        postBean= (PostBean) intent.getSerializableExtra(PARAM_POST_BEAN);
        if (postBean!=null){
            view.showPostBean(postBean);
        }
        atSomebodyFragment = AtSomebodyFragment.getInstance();
        postDao = PostDao.getInstance(this);
        addListener();
    }

    private void addListener() {
        atSomebodyFragment.setFragmentCallBack(this);
    }

    @OnClick({R.id.tv_cancle, R.id.iv_repost, R.id.iv_choose_pic, R.id.iv_emotion, R.id.iv_at, R.id.iv_topic, R.id.tv_location,R.id.et_text})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle://取消
                if (isShowFriend) {
                    hideFriendFragment();
                    isShowFriend = false;
                    view.setTitle("分享圈子");
//                    view.setSendBtnVisible(true);
                    return;
                }
                if ((!TextUtils.isEmpty(view.getEditStr())) || (pictureList.size() != 0)) {
                    //如果有内容  提示保存到草稿箱
                    postBean = new PostBean();
                    postBean.setStatus(view.getEditStr());
                    postBean.setPostStatus(type);
                    DialogUtils.showDialog(this, "", "是否保存到草稿箱?", "是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveMessage();
                            ToastUtil.showToast(getApplicationContext(), "保存成功");
                            finish();
                        }
                    }, "否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                } else {
                    finish();
                }
                break;
            case R.id.iv_repost://发送
                String msg = view.getEditStr();
                Debug.d("msg=" + msg);
                if (TextUtils.isEmpty(msg)) {
                    return;
                }
                switch (type) {
                    case Constants.POST_TYPE_COMMENT://评论
                        comment();
                        break;
                    case Constants.POST_TYPE_REPOST://转发
                        repost(msg);
                        break;
                    default://发布新的
                        postBean = new PostBean();
                        postBean.setStatus(msg);
                        postBean.setPostStatus(Constants.POST_STATUS_NEW);
                        if (pictureList != null && pictureList.size() != 0) {//带图片
                            upLoad();
                        } else {
                            //仅发文字
                            upDate();
                        }
                        break;
                }
                break;
            case R.id.iv_choose_pic://选择图片
                showChosePicDialog();
                break;
            case R.id.iv_emotion://表情
                if (!isShowEmoj) {
                    emojFragment = EmojFragment.Instance();
                    getSupportFragmentManager().beginTransaction().add(R.id.ll_container, emojFragment).commit();
                    isShowEmoj = true;
                } else {
                    //隐藏
                    getSupportFragmentManager().beginTransaction().remove(emojFragment).commit();
                    isShowEmoj = false;
                }
                break;
            case R.id.iv_at://at好友
                if (!isShowFriend) {
                    getSupportFragmentManager().
                            beginTransaction().
                            setCustomAnimations(R.anim.pop_anim_in, R.anim.pop_anim_out)//动画
                            .add(R.id.rl_all_container, atSomebodyFragment).commit();
                    isShowFriend = true;
                    view.setTitle("好友");//设置标题
//                    view.setSendBtnVisible(false);//发送按钮不可见
                }
                break;
            case R.id.iv_topic://插入话题
                view.getEt().append("##");
                //移动光标到中间
                view.getEt().setSelection(view.getEditStr().indexOf("#") + 1);
                break;
            case R.id.tv_location://定位
                LocationActivity.start(this);
                break;
            case R.id.et_text:
                if (isShowEmoj){
                    getSupportFragmentManager().beginTransaction().remove(emojFragment).commit();
                    isShowEmoj=false;
                }
                break;

        }
    }

    /**
     * 保存到草稿箱
     */
    private void saveMessage() {
        postDao.add(postBean);
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
                                takePic();
                                break;
                            case 1://图库
                                choosePic();
                                break;
                        }
                    }
                }).show();
    }

    /**
     * 转发
     */
    private void repost(String msg) {
        model.repostMessage(this,statusesBean.getId()+"",msg,bindUntilEvent(ActivityEvent.DESTROY),new CustomSubscriber<String>(mContext,true){
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)){
                    showToast("转发成功");
                    finish();
                }else {
                    showToast("转发失败");
                }
            }
        });
    }

    /**
     * 评论
     */
    private void comment() {
        commentRequestBean = new CommentRequestBean();
        commentRequestBean.setId(statusesBean.getId());
        commentRequestBean.setComment(view.getEditStr());
        model.commentMessage(this, commentRequestBean, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(PostActivity.this, "评论成功");
                    finish();
                } else {
                    ToastUtil.showToast(PostActivity.this, "评论失败");
                }
            }
        });
    }

    private void upLoad() {
        model.uploadMessage(this, postBean,pictureList, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
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
        RxGalleryFinal.with(this)
                .image()
                .multiple()
                .crop()
                .maxSize(9)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent resultEvent) throws Exception {
                        Debug.d("pic.size="+resultEvent.getResult().size());
                        if (pictureList == null || pictureList.size() == 0) {//如果集合为空
                            pictureList.addAll(resultEvent.getResult());
                            view.showPhotos(resultEvent.getResult());//显示
                        } else if (pictureList.size() < 9) {//如果集合不为空并且图片数量小于9
                            int re = 9 - pictureList.size();//剩余可以添加的图片数量
                            if (resultEvent.getResult().size() <= re) {//如果添加的图片小于可以添加的数量，则全部添加
                                pictureList.addAll(resultEvent.getResult());
                                view.showPhotos(resultEvent.getResult());
                            } else {//否则
                                List<MediaBean> reList = new ArrayList<>();
                                for (int i = 0; i < re; i++) {
                                    pictureList.add(resultEvent.getResult().get(i));
                                    reList.add(resultEvent.getResult().get(i));
                                }
                                view.showPhotos(reList);//显示
                                ToastUtil.showToast(getApplicationContext(), "最多选择9张照片");
                            }
                        } else {//集合图片数量等于9
                            //不做任何处理
                            ToastUtil.showToast(getApplicationContext(), "最多选择9张照片");
                        }
                    }
                }).openGallery();
        ToastUtil.showToast(getApplicationContext(), "你一共选择了" + pictureList.size() + "张图片");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            if (pictureList != null && pictureList.size() < 9) {
                MediaBean mediaBean = new MediaBean();
                mediaBean.setOriginalPath(picPath);
                pictureList.add(mediaBean);
                view.showPhoto(picPath);
            }
        }
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
        SortBean sortBean = (SortBean) arg.getSerializable("sort_bean");
        view.setTitle("分享圈子");
        //添加字符
        if (sortBean != null) {
            view.addStr("@" + sortBean.getName() + " ");
        }
    }

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(PARAM_POST_TYPE, bundle.getInt(PARAM_POST_TYPE));
        intent.putExtra(PARAM_STATUS_BEAN, bundle.getSerializable(PARAM_STATUS_BEAN));
        intent.putExtra(PARAM_POST_BEAN,bundle.getSerializable(PARAM_POST_BEAN));
        context.startActivity(intent);
    }
}
