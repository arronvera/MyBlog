package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
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
import code.vera.myblog.bean.GeoBean;
import code.vera.myblog.bean.PostBean;
import code.vera.myblog.bean.SortBean;
import code.vera.myblog.bean.home.RetweetedStatusBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.callback.AuthorityFragmentCallBack;
import code.vera.myblog.callback.FriendFragmentCallBack;
import code.vera.myblog.config.Constants;
import code.vera.myblog.db.PostDao;
import code.vera.myblog.model.PostModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.presenter.fragment.other.AtSomebodyFragment;
import code.vera.myblog.presenter.fragment.other.AuthorityFragment;
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
        EmojFragment.OnEmojiClickListener, FriendFragmentCallBack, AuthorityFragmentCallBack {
    public static final int TAKE_PICTURE = 0025;
    public static final String PARAM_STATUS_BEAN = "StatusesBean";
    public static final String PARAM_POST_TYPE = "type";
    public static final String PARAM_POST_BEAN = "postbean";
    public static final String ACTION_SAVE_DRAFT = "action.save.draft";
    public static final String ACTION_SEND_DRAFT = "action.seng.draft";
    public static final String PARAM_COMMENT_CID = "cid";
    public static final String PARAM_COMMENT_WEIB_ID = "comment.weiboId";
    public static final String PARAM_NEW_TEXT = "new.text";

    private int type;
    private String picPath;
    private boolean isShowEmoj = false;//是否表情已经显示
    private boolean isShowFriend = false;//是否好友已经显示
    private boolean isAddFriendFragment = false;
    private FragmentManager fragmentManager;
    private EmojFragment emojFragment;//表情
    private AtSomebodyFragment atSomebodyFragment;//好友
    private AuthorityFragment authorityFragment;//权限
    private StatusesBean statusesBean;
    private PostBean postBean;
    private CommentRequestBean commentRequestBean;
    private List<MediaBean> pictureList = new ArrayList<>();
    //数据库
    PostDao postDao;
    //地址相关
    private String address;
    public static final int ACTION_LOCATION = 25;
    private double lat;
    private double lon;
    private boolean isShowAuthority;
    private boolean isAddAuthorityFragment = false;
    private int visible;
    private long cid;//评论id
    private long weibId;

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
        }
        if (statusesBean != null) {
            view.showStatusesBean(statusesBean);
        }
        //草稿
        postBean = (PostBean) intent.getSerializableExtra(PARAM_POST_BEAN);
        if (postBean != null) {
            view.showPostBean(postBean);
        }
        //回复评论相关
        cid = intent.getLongExtra(PARAM_COMMENT_CID, 0);
        weibId = intent.getLongExtra(PARAM_COMMENT_WEIB_ID, 0);
        //反馈文字
        String feedback = intent.getStringExtra(PARAM_NEW_TEXT);
        if (!TextUtils.isEmpty(feedback)) {
            view.showFeedback(feedback);
        }

        initData();
        addListener();
    }

    private void initData() {
        if (atSomebodyFragment == null) {
            atSomebodyFragment = new AtSomebodyFragment();
        }
        postDao = PostDao.getInstance(this);
        fragmentManager = getSupportFragmentManager();
        if (authorityFragment == null) {
            authorityFragment = new AuthorityFragment();
        }
        if (emojFragment == null) {
            emojFragment = new EmojFragment();
        }
    }

    private void addListener() {
        atSomebodyFragment.setFragmentCallBack(this);
        authorityFragment.setFragmentCallBack(this);
    }

    @OnClick({R.id.tv_cancle, R.id.iv_repost, R.id.iv_choose_pic,
            R.id.iv_emotion, R.id.iv_at, R.id.iv_topic, R.id.tv_location,
            R.id.et_text, R.id.iv_close_location, R.id.ll_authority})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle://取消
                if (isShowFriend) {
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_anim_in, R.anim.pop_anim_out).hide(atSomebodyFragment).commit();
//                    hideFriendFragment();
                    isShowFriend = false;
                    view.setTitle("分享圈子");
                    return;
                }
                if (isShowAuthority) {
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_anim_in, R.anim.pop_anim_out).hide(authorityFragment).commit();
                    isShowAuthority = false;
                    view.setTitle("分享圈子");
                    return;
                }
                if ((!TextUtils.isEmpty(view.getEditStr())) || (pictureList.size() != 0)) {
                    //如果有内容  提示保存到草稿箱
                    if (postBean == null) {
                        postBean = new PostBean();
                        postBean.setStatus(view.getEditStr());
                        postBean.setPostStatus(type);
                        postBean.setLat(lat);
                        postBean.setLon(lon);
                    } else {
                        postBean.setStatus(view.getEditStr());
                        postBean.setLat(lat);
                        postBean.setLon(lon);
                    }
                    if (statusesBean != null && statusesBean.getRetweetedStatusBean() != null) {//原始信息
                        RetweetedStatusBean statusBean = statusesBean.getRetweetedStatusBean();
                        postBean.setOriHeadPhoto(statusBean.getUserbean().getProfile_image_url());
                        postBean.setOriId(statusBean.getId());
                        postBean.setOriName(statusBean.getUserbean().getName());
                        postBean.setOriStatus(statusBean.getText());
                    }
                    Debug.d(postBean.toString());
                    DialogUtils.showDialog(this, "", "是否保存到草稿箱?", "是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (saveMessage()) {
                                //发送广播
                                sendBroadcast(new Intent(ACTION_SAVE_DRAFT));
                                ToastUtil.showToast(mContext, "保存成功");
                                finish();
                            } else {
                                ToastUtil.showToast(mContext, "保存失败");
                            }
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
                    case Constants.POST_TYPE_REPLY_COMMENT://回复评论
                        reply(msg);

                        break;
                    default://发布新的
                        postBean = new PostBean();
                        postBean.setStatus(msg);
                        postBean.setPostStatus(Constants.POST_STATUS_NEW);
                        postBean.setLat(lat);
                        postBean.setLon(lon);
                        postBean.setVisible(visible);
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
                    fragmentManager.beginTransaction().add(R.id.ll_container, emojFragment).commit();
                    isShowEmoj = true;
                } else {
                    //隐藏
                    fragmentManager.beginTransaction().remove(emojFragment).commit();
                    isShowEmoj = false;
                }
                break;
            case R.id.iv_at://at好友
                if (!isShowFriend) {
                    if (!isAddFriendFragment) {
                        fragmentManager.beginTransaction().
                                setCustomAnimations(R.anim.pop_anim_in, R.anim.pop_anim_out)//动画
                                .add(R.id.rl_all_container, atSomebodyFragment).commit();
                        isAddFriendFragment = true;
                    } else {
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_anim_in, R.anim.pop_anim_out).show(atSomebodyFragment);
                    }
                    isShowFriend = true;
                    view.setTitle("好友");//设置标题
                }
                break;
            case R.id.iv_topic://插入话题
                view.getEt().append("##");
                //移动光标到中间
                view.getEt().setSelection(view.getEditStr().indexOf("#") + 1);
                break;
            case R.id.tv_location://定位
                Bundle bundle = new Bundle();
                bundle.putString(LocationActivity.PARAM_ADDRESS, address);
                bundle.putDouble(LocationActivity.PARAM_LATITUDE, lat);
                bundle.putDouble(LocationActivity.PARAM_LONGTITUDE, lon);
                LocationActivity.startActivityForResult(this, bundle, ACTION_LOCATION);
                break;
            case R.id.et_text:
                if (isShowEmoj) {
                    fragmentManager.beginTransaction().remove(emojFragment).commit();
                    isShowEmoj = false;
                }
                break;
            case R.id.iv_close_location://清除定位
                address = "";
                lat = 0;
                lon = 0;
                view.showAddress(address);
                break;
            case R.id.ll_authority://公共
                if (!isAddAuthorityFragment) {
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_anim_in, R.anim.pop_anim_out).add(R.id.rl_all_container, authorityFragment).commit();
                    isAddAuthorityFragment = true;
                } else {
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_anim_in, R.anim.pop_anim_out).show(authorityFragment).commit();
                }
                isShowAuthority = true;
                view.setTitle("选择分享范围");
                break;

        }
    }

    private void reply(String msg) {
        model.reply(mContext, cid + "", weibId + "", msg, view.getComment_ori(), bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, false) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(mContext, "回复成功");
                    finish();
                }
            }
        });
    }

    /**
     * 保存到草稿箱
     */
    private boolean saveMessage() {
        boolean isSuccesss = postDao.add(postBean);
        return isSuccesss;
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
        model.repostMessage(this, statusesBean.getId() + "", msg, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    showToast("转发成功");
                    finish();
                } else {
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
        commentRequestBean.setComment_ori(view.getComment_ori());
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
        model.uploadMessage(this, postBean, pictureList, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
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
//                        Debug.d("pic.size="+resultEvent.getResult().size());
//                        view.showPhotos(resultEvent.getResult());
                        if (pictureList == null || pictureList.size() == 0) {//如果集合为空
                            pictureList.addAll(resultEvent.getResult());
                            view.showPhotos(resultEvent.getResult());//显示
                        } else if (pictureList.size() < 9) {//如果集合不为空并且图片数量小于9
                            int re = 9 - pictureList.size();//剩余可以添加的图片数量
                            if (resultEvent.getResult().size() <= re) {//如果添加的图片小于可以添加的数量，则全部添加
                                for (int i=0;i<resultEvent.getResult().size();i++){
                                    pictureList.add(resultEvent.getResult().get(i));
                                }
//                                pictureList.addAll(resultEvent.getResult());
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

                    @Override
                    public void onCompleted() {
                        super.onCompleted();

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
        if (requestCode == ACTION_LOCATION) {
            if (data != null) {
                address = data.getStringExtra(LocationActivity.PARAM_ADDRESS);
                lat = data.getDoubleExtra(LocationActivity.PARAM_LATITUDE, 0);
                lon = data.getDoubleExtra(LocationActivity.PARAM_LONGTITUDE, 0);
                if (lat != 0 && lon != 0) {
                    //转换偏移坐标
                    model.gpsToOffSet(mContext, lon, lat, bindUntilEvent(ActivityEvent.DESTROY), new CustomSubscriber<List<GeoBean>>(mContext, false) {
                        @Override
                        public void onNext(List<GeoBean> geoBeen) {
                            super.onNext(geoBeen);
                            if (geoBeen != null && geoBeen.size() != 0) {
                                lat = geoBeen.get(0).getCoordinates().get(0);
                                lon = geoBeen.get(0).getCoordinates().get(1);
                            }
                        }
                    });
                }
                if (!TextUtils.isEmpty(address)) {
                    view.showAddress(address);
                }
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
        if (arg != null) {
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_anim_in, R.anim.pop_anim_out).hide(atSomebodyFragment).commit();
            SortBean sortBean = (SortBean) arg.getSerializable("sort_bean");
            view.setTitle("分享圈子");
            //添加字符
            if (sortBean != null) {
                view.addStr("@" + sortBean.getName() + " ");
            }
        }

    }

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(PARAM_POST_TYPE, bundle.getInt(PARAM_POST_TYPE));
        intent.putExtra(PARAM_STATUS_BEAN, bundle.getSerializable(PARAM_STATUS_BEAN));
        intent.putExtra(PARAM_POST_BEAN, bundle.getSerializable(PARAM_POST_BEAN));
        //回复评论相关
        intent.putExtra(PARAM_COMMENT_CID, bundle.getLong(PARAM_COMMENT_CID));
        intent.putExtra(PARAM_COMMENT_WEIB_ID, bundle.getLong(PARAM_COMMENT_WEIB_ID));
        //反馈
        intent.putExtra(PARAM_NEW_TEXT, bundle.getString(PARAM_NEW_TEXT));
        //草稿
        intent.putExtra(ACTION_SEND_DRAFT, bundle.getInt(ACTION_SEND_DRAFT));
        context.startActivity(intent);
    }

    @Override
    public void callbackAuthority(Bundle arg) {
        if (arg != null) {
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_anim_in, R.anim.pop_anim_out)
                    .hide(authorityFragment).commit();
            visible = arg.getInt("visible", 0);
            view.setTitle("分享圈子");
            view.setVisible(visible);
        }
    }
}
