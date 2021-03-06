package code.vera.myblog.presenter.fragment.home;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.adapter.HomeAdapter;
import code.vera.myblog.bean.GeoBean;
import code.vera.myblog.bean.HomeRequestBean;
import code.vera.myblog.bean.StatusesBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemClickListener;
import code.vera.myblog.listener.OnItemCommentListener;
import code.vera.myblog.listener.OnItemHeadPhotoListener;
import code.vera.myblog.listener.OnItemLikeListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemLocationListener;
import code.vera.myblog.listener.OnItemMenuListener;
import code.vera.myblog.listener.OnItemOriginalListener;
import code.vera.myblog.listener.OnItemRepostListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.home.HomeModel;
import code.vera.myblog.presenter.activity.BrowserActivity;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.NearByLocationActivity;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.activity.TopicActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ScreenUtils;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.home.HomeView;
import ww.com.core.Debug;
import ww.com.core.widget.CustomSwipeRefreshLayout;

import static code.vera.myblog.presenter.activity.BrowserActivity.BUNDLER_PARAM_LINK;
import static code.vera.myblog.presenter.activity.CommentDetailActivity.BUNDLE_PARAM_STATUS;
import static code.vera.myblog.presenter.activity.NearByLocationActivity.PARAM_LOCATION_LATITUDE;
import static code.vera.myblog.presenter.activity.NearByLocationActivity.PARAM_LOCATION_LONGTITUDE;
import static code.vera.myblog.presenter.activity.PersonalityActivity.BUNDLER_PARAM_USER;
import static code.vera.myblog.presenter.activity.PersonalityActivity.BUNDLER_PARAM_USER_NAME;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_POST_TYPE;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_STATUS_BEAN;
import static code.vera.myblog.presenter.activity.TopicActivity.BUNDLER_PARAM_TOPIC;


/**
 * 首页
 * Created by vera on 2017/1/20 0020.
 */

public class HomeFragment extends PresenterFragment<HomeView, HomeModel> implements
        OnItemCommentListener, OnItemRepostListener, OnItemLikeListener, OnItemOriginalListener,
        OnItemLinkListener, OnItemTopicListener, OnItemAtListener, OnItemLocationListener
        , OnItemMenuListener, OnItemHeadPhotoListener, OnItemClickListener {
    private HomeRequestBean requestBean;
    private HomeAdapter adapter;//适配器
    private PopupWindow menuPopupWindow;//菜单
    private Button btnCancel;//取消
    private Button btnShoucang;//收藏
    private Button btnCopy;//复制
    private Button btnConcern;//关注
    private Button btnShare;//分享

    private int index;//当前item
    private boolean isFollow;
    private boolean isCollection;//是否收藏
    private AlertDialog.Builder cateDialogBuilder;//弹出框
    private AlertDialog cateDialog;

    public static final String ACTION_CLEAR_UNREAD = "action.clearunread";
    public static final String ACTION_UPDATE_FAVORITE = "action.updatefavorite";

    private String[] cate = new String[]{"所有人", "好友"};
    private int currentCateIndex = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        requestBean = new HomeRequestBean();
        initData();
        setAdater();
        getData(true);
        addListener();
    }

    private void initData() {
        View menu = LayoutInflater.from(getContext()).inflate(R.layout.pop_bottom, null);
        menuPopupWindow = new PopupWindow(menu, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuPopupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        menuPopupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        menuPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        btnCancel = (Button) menu.findViewById(R.id.btn_cancel);
        btnConcern = (Button) menu.findViewById(R.id.btn_concern);
        btnCopy = (Button) menu.findViewById(R.id.btn_copy);
        btnShoucang = (Button) menu.findViewById(R.id.btn_shoucang);
        btnShare = (Button) menu.findViewById(R.id.btn_share);
        btnCancel.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (menuPopupWindow.isShowing()) {
                    menuPopupWindow.dismiss();
                }
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
        btnConcern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollow) {
                    // 取消关注
                    ToastUtil.showToast(getContext(), getString(R.string.api_not_found));
//                    destroyFriendShip();
                } else {
                    ToastUtil.showToast(getContext(), getString(R.string.api_not_found));
//                    createFriendShip();
                }
            }
        });
        btnShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weibId = adapter.getItem(index).getId() + "";
                if (isCollection) {
                    destroyFavorites(weibId);
                } else {
                    createFavorites(weibId);
                }
            }
        });
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复制到粘贴板
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(adapter.getItem(index).getText());
                ToastUtil.showToast(getContext(), "复制成功");
                menuPopupWindow.dismiss();
            }
        });
        cateDialogBuilder = new AlertDialog.Builder(mContext);
    }

    private void share() {
        //todo 分享
//        StatusesBean statusesBean = adapter.getItem(index);
//        model.share(statusesBean.getText(),);
//        WeiboMultiMessage weiboMultiMessage = new WeiboMultiMessage();
//        weiboMultiMessage.textObject=getTextObj();
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, "文本");
//        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/sdcard/aaa.jpg")));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Context context = null;
        try{
            context=mContext.createPackageContext("com.sina.weibo", Context.CONTEXT_IGNORE_SECURITY);
            intent.setClassName(context, "com.sina.weibo.EditActivity");
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            ToastUtil.showToast(mContext,"您的手机没有安装新浪微博客户端");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createFriendShip() {
        model.createFriendShip(getContext(), adapter.getItem(index).getId() + "", bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(getContext(), getString(R.string.concern_success));
                    adapter.getItem(index).setFavorited(true);
                    adapter.notifyItemChanged(index);
                }
            }
        });
    }

    private void destroyFriendShip() {
        model.destroyFriendShip(getContext(), adapter.getItem(index).getId() + "", bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(getContext(), getString(R.string.cancel_concern_success));
                    adapter.getItem(index).setFavorited(false);
                    adapter.notifyItemChanged(index);
                } else {
                    ToastUtil.showToast(getContext(), getString(R.string.wrong_api));
                }
            }
        });
    }

    private void createFavorites(String weibId) {
        model.createFavorites(weibId, mContext, bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<String>(mContext, true) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    ToastUtil.showToast(mContext, "收藏成功");
                    menuPopupWindow.dismiss();
                    //收藏 更新
                    adapter.getItem(index).setFavorited(true);
                    adapter.notifyItemChanged(index);
                    mContext.sendBroadcast(new Intent(ACTION_UPDATE_FAVORITE));
                }
            }
        });
    }

    private void destroyFavorites(String weibId) {
        model.destroyFavorites(weibId, mContext, bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<Boolean>(mContext, true) {
            @Override
            public void onNext(Boolean b) {
                super.onNext(b);
                if (b) {
                    ToastUtil.showToast(mContext, "取消收藏成功");
                    menuPopupWindow.dismiss();
                    //取消收藏 更新
                    adapter.getItem(index).setFavorited(false);
                    adapter.notifyItemChanged(index);
                    mContext.sendBroadcast(new Intent(ACTION_UPDATE_FAVORITE));
                } else {
                    ToastUtil.showToast(mContext, "取消收藏失败");
                }
            }
        });
    }

    private void addListener() {
        view.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                //下拉刷新
                requestBean.page = "1";//
                if (currentCateIndex == 0) {
                    getData(false);
                } else if (currentCateIndex == 1) {
                    getDoubleData(false);
                }
            }

            @Override
            public void onFooterRefreshing() {
                //上拉加载
                int nextPage = Integer.parseInt(requestBean.getPage()) + 1;
                Debug.d("bean=" + requestBean.toString());
                requestBean.setPage(nextPage + "");
                if (currentCateIndex == 0) {
                    getData(false);
                } else if (currentCateIndex == 1) {
                    getDoubleData(false);
                }
            }
        });
        adapter.setOnItemCommentListener(this);
        adapter.setOnItemRepostListener(this);
        adapter.setOnItemLikeListener(this);
        adapter.setOnItemOriginalListener(this);
        adapter.setOnItemLinkListener(this);
        adapter.setOnItemTopicListener(this);
        adapter.setOnItemAtListener(this);
        adapter.setOnItemMenuListener(this);
        adapter.setOnItemHeadPhotoListener(this);
        adapter.setOnItemClickListener(this);
        menuPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                ScreenUtils.backgroundAlpaha(getActivity(), 1.0f);
            }
        });
        adapter.setOnItemLocationListener(this);

    }

    private void setAdater() {
        adapter = new HomeAdapter(getContext());
        view.setAdapter(adapter);
    }


    /**
     * 获取数据
     */
    private void getData(boolean isDialog) {
        model.getHomeTimeLine(requestBean, mContext, bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<List<StatusesBean>>(mContext, isDialog) {
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                if (statusesBeen != null) {
                    if (requestBean.getPage().equals("1")) {
                        adapter.addList(statusesBeen);//清空加载进去
                    } else {//往后面追加
                        adapter.appendList(statusesBeen);
                    }
                    view.refreshFinished();//加载完成
                }
            }
        });
//        model.clearUnread(mContext, bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<String>(mContext, false) {
//            @Override
//            public void onNext(String s) {
//                super.onNext(s);
//                mContext.sendBroadcast(new Intent(ACTION_CLEAR_UNREAD));
//            }
//        });
    }

    @Override
    public void onItemCommentListener(View v, int pos) {
        //评论
        StatusesBean statusesBean = adapter.getItem(pos);
        Debug.d("count=" + statusesBean.getComments_count());
        if (statusesBean.getComments_count() == 0) {//如果没有评论数，直接跳到发布评论
            Bundle bundle = new Bundle();
            bundle.putInt(PARAM_POST_TYPE, Constants.POST_TYPE_COMMENT);
            bundle.putSerializable(PARAM_STATUS_BEAN, statusesBean);
            PostActivity.start(mContext, bundle);
        } else {//跳转到评论详情
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_PARAM_STATUS, statusesBean);
            CommentDetailActivity.start(getContext(), bundle);
        }

    }

    @Override
    public void onItemRepostListener(View v, int pos) {
        //转发
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM_POST_TYPE, Constants.POST_TYPE_REPOST);
        bundle.putSerializable(PARAM_STATUS_BEAN, adapter.getItem(pos));
        PostActivity.start(mContext, bundle);
    }

    @Override
    public void onItemLikeListener(View v, ImageView imageView, int pos) {
        //喜欢
        view.setLikeView(imageView);
        StatusesBean statusesBean = adapter.getItem(pos);
        statusesBean.setAttitudes_count(statusesBean.getAttitudes_count() + 1);
        adapter.notifyItemChanged(pos, imageView);
    }

    @OnClick(R.id.iv_filter)
    public void filter() {
        ButtonOnClick buttonOnClick = new ButtonOnClick();//默认为0表示选中第一个项目，-1表示没有项目被选中
        cateDialogBuilder.setSingleChoiceItems(cate, 1, buttonOnClick);
        cateDialogBuilder.setSingleChoiceItems(cate, currentCateIndex, buttonOnClick);
        cateDialog = cateDialogBuilder.show();
    }

    /*
    获取双向关注
     */
    private void getDoubleData(boolean isDialog) {
        model.getBilateralTimeLine(requestBean, getContext(), bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<List<StatusesBean>>(mContext, isDialog) {
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                if (statusesBeen != null) {
                    if (requestBean.getPage().equals("1")) {
                        adapter.addList(statusesBeen);//清空加载进去
                        view.refreshFinished();//加载完成
                    } else {//往后面追加
                        adapter.appendList(statusesBeen);
                    }
                }
            }
        });
    }

    @Override
    public void onItemOriginalListener(View v, int pos) {
        //原
        Intent intent = new Intent(getActivity(), CommentDetailActivity.class);
        intent.putExtra("retweeted_status", adapter.getItem(pos).getRetweetedStatusBean());
        startActivity(intent);
    }

    @Override
    public void onItemLinkListener(View v, int pos, String str, int type) {
        //链接
        switch (type) {
            case Constants.LINK_TYPE_WEBSITE:
                Bundle bundle = new Bundle();
                bundle.putString(BUNDLER_PARAM_LINK, str);
                BrowserActivity.start(mContext, bundle);
                break;
            case Constants.LINK_TYPE_MUSIC:
                //音乐
                break;
            case Constants.LINK_TYPE_VIDEO:
                //电影
                break;
            default:
                bundle = new Bundle();
                bundle.putString(BUNDLER_PARAM_LINK, str);
                BrowserActivity.start(mContext, bundle);
                break;
        }

    }

    @Override
    public void onItemTopicListener(View v, int pos, String str) {
        Debug.d("点击topic话题" + str);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLER_PARAM_TOPIC, str.substring(1, str.length() - 1));
        TopicActivity.start(mContext, bundle);
    }

    @Override
    public void onItemAtListener(View v, int pos, String str) {
        Debug.d("点击@某人" + str);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLER_PARAM_USER_NAME, str.substring(str.indexOf("@") + 1, str.length()));
        PersonalityActivity.start(mContext, bundle);
    }

    @Override
    public void onItemMenuListener(View v, int pos) {
        //弹出菜单更多,在底部显示
        index = pos;
        isFollow = adapter.getItem(pos).getUserBean().isFollowing();
        isCollection = adapter.getItem(pos).isFavorited();
        if (isFollow) {
            btnConcern.setText(getString(R.string.cancel_concern));
        } else {
            btnConcern.setText(getString(R.string.concern));
        }
        if (isCollection) {
            btnShoucang.setText(getString(R.string.cancel_collection));
        } else {
            btnShoucang.setText(getString(R.string.collection));
        }
        menuPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        ScreenUtils.backgroundAlpaha(getActivity(), 0.5f);
    }

    @Override
    public void onItemHeadPhotoListener(View v, int pos) {
        //点击头头像，跳转到个人界面
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLER_PARAM_USER, adapter.getItem(pos).getUserBean());
        PersonalityActivity.start(mContext, bundle);
    }

    @Override
    public void onItemClickListener(View v, int pos) {
        //点击单个Item
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_PARAM_STATUS, adapter.getItem(pos));
        CommentDetailActivity.start(mContext, bundle);
    }

    @Override
    public void onItemLocation(View v, int pos) {
        //查看位置
        Bundle bundle = new Bundle();
        GeoBean geoBean = adapter.getItem(pos).getGeoBean();
        bundle.putDouble(PARAM_LOCATION_LATITUDE, geoBean.getCoordinates().get(0));
        bundle.putDouble(PARAM_LOCATION_LONGTITUDE, geoBean.getCoordinates().get(1));
        NearByLocationActivity.start(mContext, bundle);
    }

    private class ButtonOnClick implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Debug.d("i=" + i);
            currentCateIndex = i;
            cateDialog.dismiss();
            switch (i) {
                case 0:
                    getData(true);
                    break;
                case 1:
                    getDoubleData(true);
                    break;
            }
        }
    }
}
