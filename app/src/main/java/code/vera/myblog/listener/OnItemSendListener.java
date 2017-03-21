package code.vera.myblog.listener;

import android.view.View;

import code.vera.myblog.bean.PostBean;

/**
 * 发送
 * Created by vera on 2017/3/20 0020.
 */

public interface OnItemSendListener {
    void onItemSendListener(View view,int pos,PostBean postBean);
}
