package code.vera.myblog.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by vera on 2017/3/21 0021.
 */

public class ScreenUtils {
    /**
     * 设置添加屏幕的背景透明度
     * **/
    public static void backgroundAlpaha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
