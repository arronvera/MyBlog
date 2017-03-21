package code.vera.myblog.listener;

import android.view.View;
import android.widget.ImageView;

/**
 * 喜欢监听
 * Created by vera on 2017/2/24 0024.
 */

public interface OnItemLikeListener {
    void onItemLikeListener(View v, ImageView imageView, int pos);
}
