package code.vera.myblog.presenter.fragment.person;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.TabPersonAllCircleAdapter;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.listener.OnItemDeleteClickListener;
import code.vera.myblog.model.user.UserModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.DialogUtils;
import code.vera.myblog.utils.ScreenUtils;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.personality.TabPersonAllCircleView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * 个人界面的圈子
 * Created by vera on 2017/2/24 0024.
 */

public class TabPersonAllCircleFragment extends PresenterFragment<TabPersonAllCircleView, UserModel>
implements OnItemDeleteClickListener{
    static TabPersonAllCircleFragment instance;
    private HomeRequestBean homeRequestBean;
    private TabPersonAllCircleAdapter adapter;
    private PopupWindow menuPopupWindow;
    private String uid;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_person_all;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        homeRequestBean=new HomeRequestBean();
        homeRequestBean.setUid(uid);
        initView();
        setAdapter();
        addListener();
        getCircles(true);
    }

    private void initView() {
        View menu = LayoutInflater.from(getContext()).inflate(R.layout.pop_bottom_me, null);
        menuPopupWindow=new PopupWindow(menu, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuPopupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        menuPopupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        menuPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
    }

    private void addListener() {
        adapter.setOnItemDeleteClickListener(this);
        menuPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                ScreenUtils.backgroundAlpaha(getActivity(),1.0f);
            }
        });
        view.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                homeRequestBean.page="1";
                getCircles(false);
            }

            @Override
            public void onFooterRefreshing() {
                int nextPage = Integer.parseInt(homeRequestBean.getPage()) + 1;
                homeRequestBean.setPage(nextPage + "");
                getCircles(false);
            }
        });
    }

    private void setAdapter() {
        adapter=new TabPersonAllCircleAdapter(getContext());
        view.setAdapter(adapter);
    }

    private void getCircles(boolean isDialog) {
        model.getUserTimeLine(getContext(),homeRequestBean,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<StatusesBean>>(mContext,isDialog){
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                adapter.addList(statusesBeen);
                view.refreshFinished();
            }
        });
    }



    public static TabPersonAllCircleFragment getInstance(){
        if (instance==null){
            instance=new TabPersonAllCircleFragment();
        }
        return instance;
    }

    public void setUid(long id) {
      uid=id+"";
    }

    @Override
    public void onItemDeleteClickListener(View view, final int pos) {
        final long id=adapter.getItem(pos).getId();
        DialogUtils.showDialog(mContext, "", "你确定要删除这条信息吗？", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCircle(id,pos);
            }
        }, "取消", null);

    }

    private void deleteCircle(long id, final int pos) {
        model.destroyStatus(id+"",mContext,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<Boolean>(mContext,false){
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean){
                    //删除成功
                    adapter.removeItem(adapter.getItem(pos));
                    adapter.notifyDataSetChanged();
                }else {
                    ToastUtil.showToast(mContext,"删除失败，请检查网络");
                }
            }
        });
    }
}
