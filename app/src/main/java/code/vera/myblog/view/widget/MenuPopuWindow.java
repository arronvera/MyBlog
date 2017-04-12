package code.vera.myblog.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import code.vera.myblog.R;
import code.vera.myblog.utils.ScreenUtils;

/**
 * Created by vera on 2017/4/12 0012.
 */

public class MenuPopuWindow extends PopupWindow {

    private PopupWindow menuPopupWindow;
    private Context context;

    public MenuPopuWindow(Context context) {
        this.context=context;
        View menu = LayoutInflater.from(context).inflate(R.layout.pop_bottom, null);
        menuPopupWindow = new PopupWindow(menu, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuPopupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        menuPopupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        menuPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
    }

    @Override
    public void showAsDropDown(View anchor) {
        menuPopupWindow.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
        ScreenUtils.backgroundAlpaha((Activity) context, 0.5f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        menuPopupWindow.dismiss();
        ScreenUtils.backgroundAlpaha((Activity) context, 1f);
    }
}
