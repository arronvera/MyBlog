package code.vera.myblog.presenter.fragment.person;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import code.vera.myblog.R;
import code.vera.myblog.adapter.HomeAdapter;
import code.vera.myblog.bean.home.HomeRequestBean;
import code.vera.myblog.bean.home.StatusesBean;
import code.vera.myblog.listener.OnItemMenuListener;
import code.vera.myblog.model.user.UserModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.presenter.subscribe.CustomSubscriber;
import code.vera.myblog.utils.ScreenUtils;
import code.vera.myblog.view.personality.TabPersonAllCircleView;

/**
 * 个人界面的圈子
 * Created by vera on 2017/2/24 0024.
 */

public class TabPersonAllCircleFragment extends PresenterFragment<TabPersonAllCircleView, UserModel>
implements OnItemMenuListener{
    static TabPersonAllCircleFragment instance;
    private HomeRequestBean homeRequestBean;
    private HomeAdapter adapter;
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
        getCircles();
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
        adapter.setOnItemMenuListener(this);
        menuPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                ScreenUtils.backgroundAlpaha(getActivity(),1.0f);
            }
        });
    }

    private void setAdapter() {
        adapter=new HomeAdapter(getContext());
        view.setAdapter(adapter);
    }

    private void getCircles() {
        model.getUserTimeLine(getContext(),homeRequestBean,bindUntilEvent(FragmentEvent.DESTROY),new CustomSubscriber<List<StatusesBean>>(mContext,true){
            @Override
            public void onNext(List<StatusesBean> statusesBeen) {
                super.onNext(statusesBeen);
                adapter.addList(statusesBeen);
            }
        });
    }


    @Override
    public void onItemMenuListener(View v, int pos) {
        menuPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        ScreenUtils.backgroundAlpaha(getActivity(),0.5f);
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
}
