package code.vera.myblog.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 提示
 * Created by vera on 2017/1/5 0005.
 */

public class ToastUtil {

        static Toast mToast;
        public static void showToast(Context context, String msg) {
            if (TextUtils.isEmpty(msg)) {
                return;
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }
}
