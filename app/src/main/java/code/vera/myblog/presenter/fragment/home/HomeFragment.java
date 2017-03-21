package code.vera.myblog.presenter.fragment.home;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.adapter.HomeAdapter;
import code.vera.myblog.adapter.HomeTypeListAdpater;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemClickListener;
import code.vera.myblog.listener.OnItemCommentListener;
import code.vera.myblog.listener.OnItemHeadPhotoListener;
import code.vera.myblog.listener.OnItemLikeListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemMenuListener;
import code.vera.myblog.listener.OnItemOriginalListener;
import code.vera.myblog.listener.OnItemRepostListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.home.HomeModel;
import code.vera.myblog.presenter.activity.BrowserActivity;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.activity.TopicActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ScreenUtils;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.home.HomeView;
import code.vera.myblog.view.widget.LikeView;
import ww.com.core.Debug;
import ww.com.core.widget.CustomSwipeRefreshLayout;


/**
 * 首页
 * Created by vera on 2017/1/20 0020.
 */

public class HomeFragment  extends PresenterFragment<HomeView, HomeModel>implements
        OnItemCommentListener,OnItemRepostListener,OnItemLikeListener,OnItemOriginalListener,
        OnItemLinkListener,OnItemTopicListener,OnItemAtListener
        ,OnItemMenuListener,OnItemHeadPhotoListener,OnItemClickListener{
    private HomeRequestBean requestBean;
    private HomeAdapter adapter;//适配器
    private View contentView ;
    private   PopupWindow popupWindow;
    private LikeView likeView;
    private PopupWindow menuPopupWindow;//菜单
    private Button btnCancel;//取消
    private Button btnShoucang;//收藏
    private Button btnCopy;//复制
    private Button btnConcern;//关注

    private int index;//当前item
    private boolean isFollow;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        requestBean=new HomeRequestBean();
        initPopuwindow();
        setAdater();
        getData();
        //上下拉刷新

        view.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                Debug.d("下拉刷新");
                //下拉刷新
                requestBean.page="1";//
                getData();
            }

            @Override
            public void onFooterRefreshing() {
                Debug.d("上拉加载");
                //上拉加载
                int nextPage=Integer.parseInt(requestBean.getPage())+1;
                Debug.d("bean="+requestBean.toString());
                requestBean.setPage(nextPage+"");
                getData();
            }
        });
        addListener();
    }

    private void initPopuwindow() {
        likeView=new LikeView(getContext());
        contentView= LayoutInflater.from(mContext).inflate(R.layout.pop_window, null);
        ListView listView= (ListView) contentView.findViewById(R.id.lv_filter_type);
        List<String>list=new ArrayList<>();
        list.add("所有");
        list.add("互相关注");
        list.add("朋友圈");
        list.add("特别关注");
        listView.setAdapter(new HomeTypeListAdpater(list,getContext()));
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
//        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //------------------菜单
        View menu = LayoutInflater.from(getContext()).inflate(R.layout.pop_bottom, null);
        menuPopupWindow=new PopupWindow(menu, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuPopupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        menuPopupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        menuPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        btnCancel= (Button) menu.findViewById(R.id.btn_cancel);
        btnConcern= (Button) menu.findViewById(R.id.btn_concern);
        btnCopy= (Button) menu.findViewById(R.id.btn_copy);
        btnShoucang= (Button) menu.findViewById(R.id.btn_shoucang);
        btnCancel.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (menuPopupWindow.isShowing()){
                    menuPopupWindow.dismiss();
                }
            }
        });
        btnConcern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollow){
                    // 取消关注
                    model.destroyFriendShip(getContext(),adapter.getItem(index).getId()+"",bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<String>(mContext,true){
                        @Override
                        public void onNext(String s) {
                            super.onNext(s);
                            if (!TextUtils.isEmpty(s)){
                                ToastUtil.showToast(getContext(),getString(R.string.cancel_concern_success));
                            }
                        }
                    });
                }else{
                    model.createFriendShip(getContext(),adapter.getItem(index).getId()+"",bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<String>(mContext,true){
                        @Override
                        public void onNext(String s) {
                            super.onNext(s);
                            if (!TextUtils.isEmpty(s)){
                                ToastUtil.showToast(getContext(),getString(R.string.concern_success));
                            }
                        }
                    });
                }
            }
        });
        btnShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 收藏
            }
        });
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复制到粘贴板
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(adapter.getItem(index).getText());
                ToastUtil.showToast(getContext(),"复制成功");
                menuPopupWindow.dismiss();
            }
        });
    }

    private void addListener() {
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
                ScreenUtils.backgroundAlpaha(getActivity(),1.0f);
            }
        });

    }

    private void setAdater() {
        adapter=new HomeAdapter(getContext());
        view.setAdapter(adapter);
    }


    /**
     * 获取数据
     */
    private void getData() {
        model.getHomeTimeLine(requestBean, getContext(), bindUntilEvent(FragmentEvent.DESTROY), new CustomSubscriber<List<StatusesBean>>(mContext,true){
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                if (statusesBeen!=null){
                    if (requestBean.getPage().equals("1")){
                       adapter.addList(statusesBeen);//清空加载进去
                        view.refreshFinished();//加载完成
                    }else {//往后面追加
                        adapter.appendList(statusesBeen);
                    }
                }
            }
        });
    }

    @Override
    public void onItemCommentListener(View v, int pos) {
        //评论
        Debug.d("count="+adapter.getItem(pos).getComments_count());
        if (adapter.getItem(pos).getComments_count()==0){//如果没有评论数，直接跳到发布评论
            Intent intent=new Intent(getActivity(), PostActivity.class);
            intent.putExtra("type", Constants.POST_TYPE_COMMENT);
            intent.putExtra("StatusesBean",adapter.getItem(pos));
            startActivity(intent);
        }else {//跳转到评论详情
            Bundle bundle=new Bundle();
            bundle.putSerializable("status",adapter.getItem(pos));
            CommentDetailActivity.start(getContext(),bundle);
//            Intent intent=new Intent(getActivity(), CommentDetailActivity.class);
//            intent.putExtra("status",adapter.getItem(pos));
//            startActivity(intent);
        }

    }

    @Override
    public void onItemRepostListener(View v, int pos) {
        //转发
//        ToastUtil.showToast(getContext(),"转发");
        Intent intent=new Intent(getActivity(), PostActivity.class);
        intent.putExtra("type", Constants.POST_TYPE_REPOST);
        intent.putExtra("StatusesBean",adapter.getItem(pos));
        startActivity(intent);
    }

    @Override
    public void onItemLikeListener(View v,ImageView imageView, int pos) {
        //喜欢
//        ToastUtil.showToast(getContext(),"喜欢");
        //选中
        imageView.setImageResource(R.mipmap.ic_like_sel);
        likeView.setImage(getResources().getDrawable(R.mipmap.ic_like_sel));
        likeView.show(imageView);
        //todo count+1
    }
    @OnClick(R.id.iv_filter)
    public void filter(View v){
        showPopWindow(v);
    }

    private void showPopWindow(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //显示在上方
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]-popupWindow.getHeight());
    }

    @Override
    public void onItemOriginalListener(View v, int pos) {
        //原
        Intent intent=new Intent(getActivity(),CommentDetailActivity.class);
        intent.putExtra("retweeted_status",adapter.getItem(pos).getRetweetedStatusBean());
        startActivity(intent);
    }

    @Override
    public void onItemLinkListener(View v, int pos,String str) {
        //链接
//        ToastUtil.showToast(getContext(),"点击了链接"+str);
        Intent intent=new Intent(getActivity(),BrowserActivity.class);
        intent.putExtra("link",str);
        startActivity(intent);
    }

    @Override
    public void onItemTopicListener(View v, int pos,String str) {
//        ToastUtil.showToast(getContext(),"点击了话题"+str);
        Intent intent=new Intent(getContext(), TopicActivity.class);
        intent.putExtra("topic",str.substring(1,str.length()-1));
        startActivity(intent);
    }

    @Override
    public void onItemAtListener(View v, int pos,String str) {
        Intent intent=new Intent(getContext(), PersonalityActivity.class);
        intent.putExtra("user_name",str.substring(str.indexOf("@")+1,str.length()));
        startActivity(intent);
    }

    @Override
    public void onItemMenuListener(View v, int pos) {
        //弹出菜单更多,在底部显示
        index=pos;
       isFollow=adapter.getItem(pos).getUserBean().isFollowing();
        Debug.d("isFollow="+isFollow);
        if (isFollow){
            btnConcern.setText(getString(R.string.cancel_concern));
        }else{
            btnConcern.setText(getString(R.string.concern));
        }
        menuPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        ScreenUtils.backgroundAlpaha(getActivity(),0.5f);
    }

    @Override
    public void onItemHeadPhotoListener(View v, int pos) {
        //点击头头像，跳转到个人界面
        Intent intent=new Intent(getActivity(),PersonalityActivity.class);
        intent.putExtra("user",adapter.getItem(pos).getUserBean());
        startActivity(intent);
    }

    @Override
    public void onItemClickListener(View v, int pos) {
        //点击单个Item
//        Intent intent=new Intent(getActivity(), CommentDetailActivity.class);
//        intent.putExtra("status",adapter.getItem(pos));
//        startActivity(intent);
    }
}
