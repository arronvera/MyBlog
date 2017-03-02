package code.vera.myblog.presenter.fragment.home;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.adapter.HomeAdapter;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.listener.OnItemAtListener;
import code.vera.myblog.listener.OnItemCommentListener;
import code.vera.myblog.listener.OnItemHeadPhotoListener;
import code.vera.myblog.listener.OnItemLikeListener;
import code.vera.myblog.listener.OnItemLinkListener;
import code.vera.myblog.listener.OnItemMenuListener;
import code.vera.myblog.listener.OnItemOriginalListener;
import code.vera.myblog.listener.OnItemRepostListener;
import code.vera.myblog.listener.OnItemTopicListener;
import code.vera.myblog.model.home.HomeModel;
import code.vera.myblog.presenter.activity.CommentDetailActivity;
import code.vera.myblog.presenter.activity.PersonalityActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
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
        ,OnItemMenuListener,OnItemHeadPhotoListener{
    private HomeRequestBean requestBean;
    private HomeAdapter adapter;//适配器
    private View contentView ;
    private   PopupWindow popupWindow;
    private LikeView likeView;
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
//        contentView= LayoutInflater.from(mContext).inflate(R.layout.pop_window, null);
//        ListView listView= (ListView) contentView.findViewById(R.id.lv_filter_type);
//        List<String>list=new ArrayList<>();
//        list.add("所有");
//        list.add("互相关注");
//        list.add("朋友圈");
//        list.add("特别关注");
//        listView.setAdapter(new HomeTypeListAdpater(list,getContext()));
//        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setTouchable(true);
//        popupWindow.setOutsideTouchable(true);
//        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());

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
            intent.putExtra("type", Constants.COMMENT_TYPE);
            intent.putExtra("id",adapter.getItem(pos).getId()+"");
            startActivity(intent);
        }else {//跳转到评论详情
            Intent intent=new Intent(getActivity(), CommentDetailActivity.class);
            intent.putExtra("status",adapter.getItem(pos));
            startActivity(intent);
        }

    }

    @Override
    public void onItemRepostListener(View v, int pos) {
        //转发
        ToastUtil.showToast(getContext(),"转发");
        Intent intent=new Intent(getActivity(), PostActivity.class);
        intent.putExtra("type", Constants.REPOST_TYPE);
        intent.putExtra("StatusesBean",adapter.getItem(pos));
        startActivity(intent);
    }

    @Override
    public void onItemLikeListener(View v, int pos) {
        //喜欢
//        ToastUtil.showToast(getContext(),"喜欢");
        //选中
        ((ImageView) v).setImageResource(R.mipmap.ic_like_sel);
        likeView.setImage(getResources().getDrawable(R.mipmap.ic_like_sel));
        likeView.show(v);
        //todo count+1
    }
    @OnClick(R.id.iv_filter)
    public void filter(View v){
//        showPopWindow(v);
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
        ToastUtil.showToast(getContext(),"点击了链接"+str);
//        Intent intent=new Intent(getActivity(),Browser.class);
//        intent.putExtra("link",adapter.getItem(pos).getRetweetedStatusBean());
//        startActivity(intent);
    }

    @Override
    public void onItemTopicListener(View v, int pos,String str) {
        //Todo
        ToastUtil.showToast(getContext(),"点击了话题"+str);
    }

    @Override
    public void onItemAtListener(View v, int pos,String str) {
        ToastUtil.showToast(getContext(),"点击了at"+str);
        Intent intent=new Intent(getContext(), PersonalityActivity.class);
        intent.putExtra("user_name",str.substring(str.indexOf("@")+1,str.length()));
        startActivity(intent);
    }

    @Override
    public void onItemMenuListener(View v, int pos) {
        //弹出菜单更多
        view.showPopuWindow(v);
    }

    @Override
    public void onItemHeadPhotoListener(View v, int pos) {
        //点击头头像，跳转到个人界面
        Intent intent=new Intent(getActivity(),PersonalityActivity.class);
        intent.putExtra("user",adapter.getItem(pos).getUserBean());
        startActivity(intent);
    }
}
