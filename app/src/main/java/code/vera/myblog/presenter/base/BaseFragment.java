package code.vera.myblog.presenter.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * Created by 10142 on 2016/10/10.
 */
public abstract class BaseFragment extends RxFragment implements
        PresenterActivity.BackListener {

    public String tag;
    protected BaseActivity activity;
    protected Context mContext;
    protected View contentView;

    protected boolean pause = false;
    protected Handler mHandler;

    protected abstract int getLayoutResId();

    protected abstract void onAttach();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        tag = this.getClass().getSimpleName();


        if (contentView == null)
            contentView = inflater.inflate(getLayoutResId(), container, false);
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent != null) {
            parent.removeView(contentView);
        }
        ScreenUtil.scale(contentView);

        initBind(contentView);
        onAttach();
        return contentView;
    }



    private void initBind(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void onAttach(Activity _activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        activity = (BaseActivity) _activity;
        activity.addBackListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mHandler = new Handler();
    }

    @Override
    public void onResume() {
        super.onResume();
        pause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        pause = true;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            pause = true;
        }
    }


    // 隐藏软键盘
    public void hideSoftKeyBord(View v) {
        if (v == null)
            return;
        v.clearFocus();
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void showToast(String msg) {
        activity.showToast(msg);
    }


    public void onClickBack(){
        getParentFragment().getChildFragmentManager().popBackStack();
    }

    public boolean onBackPressed() {
        return false;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (activity != null) {
            activity.removeBackListener(this);
        }
        if (contentView != null) {
            try {
                ((ViewGroup) contentView.getParent()).removeView(contentView);
            } catch (Exception e) {
            }
        }
    }
}
