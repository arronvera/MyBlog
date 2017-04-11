package code.vera.myblog.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.widget.LoginoutButton;

import butterknife.BindView;
import code.vera.myblog.LoginActivity;
import code.vera.myblog.R;
import code.vera.myblog.config.Constants;
import code.vera.myblog.view.base.IView;

/**
 * Created by vera on 2017/1/5 0005.
 */

public class LoginView implements IView {
    @BindView(R.id.login_out_button_silver)
    LoginoutButton lbLogin;//微博登陆
    private Context context;
    private AuthInfo authInfo;
    @Override
    public void onAttachView(@NonNull View view) {
        context=view.getContext();
    }

    public void setAuthInfo(LoginActivity.AuthListener loginListener) {
        authInfo=new AuthInfo(context, Constants.APP_KEY,Constants.REDIRECT_URL,Constants.SCOPE);
        lbLogin.setWeiboAuthInfo(authInfo, loginListener);
    }
}
