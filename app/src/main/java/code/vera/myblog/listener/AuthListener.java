package code.vera.myblog.listener;

import android.os.Bundle;

import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created by vera on 2017/1/12 0012.
 */

public class AuthListener implements WeiboAuthListener{
    @Override
    public void onComplete(Bundle bundle) {

    }

    @Override
    public void onWeiboException(WeiboException e) {

    }

    @Override
    public void onCancel() {

    }
}
