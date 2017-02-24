package code.vera.myblog.view.home;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import code.vera.myblog.R;
import code.vera.myblog.adapter.RvAdapter;
import code.vera.myblog.view.RefreshView;

/**
 * Created by vera on 2017/1/25 0025.
 */

public class HomeView extends RefreshView {

    private RvAdapter adapter;
    private Context context;
    private   PopupWindow popupWindow;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        context=view.getContext();
        initPopWindow();
    }

    private void initPopWindow() {
        View popView= LayoutInflater.from(context).inflate(R.layout.pop_menu,null);
         popupWindow=new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
    }

    @Override
    public void setAdapter(RvAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter=adapter;
    }

    public void showPopuWindow(View v) {
      popupWindow.showAsDropDown(v);
    }
}
