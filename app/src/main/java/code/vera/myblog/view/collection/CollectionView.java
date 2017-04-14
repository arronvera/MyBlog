package code.vera.myblog.view.collection;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import code.vera.myblog.R;
import code.vera.myblog.view.RefreshView;
import code.vera.myblog.view.widget.LikeView;
import code.vera.myblog.view.widget.MenuPopuWindow;

/**
 * Created by vera on 2017/4/12 0012.
 */

public class CollectionView extends RefreshView {
    private LikeView likeView;
    private Context context;
    private PopupWindow menuPopupWindow;//菜单
    private Button btnFavorites;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        context=view.getContext();
        likeView = new LikeView(context);
        initPopWindow();
    }

    private void initPopWindow() {
        menuPopupWindow=new MenuPopuWindow(context);
        View menu = LayoutInflater.from(context).inflate(R.layout.pop_bottom, null);
        menuPopupWindow = new PopupWindow(menu, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuPopupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        menuPopupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        menuPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        btnFavorites= (Button) menu.findViewById(R.id.btn_shoucang);
        btnFavorites.setText(R.string.cancel_collection);
    }

    public void showLikeView(ImageView imageView) {
        imageView.setImageResource(R.mipmap.ic_heart_sel);
        likeView.setImage(R.mipmap.ic_heart_sel);
        likeView.show(imageView);
    }

    public void showMenuPopwindow(View v) {
        menuPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }
}
