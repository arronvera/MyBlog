package code.vera.myblog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.widget.LoginoutButton;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import code.vera.myblog.config.Constants;
import code.vera.myblog.model.LoginModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.LoginView;

import static code.vera.myblog.AccessTokenKeeper.readAccessToken;

/**
 * 登陆界面
 */
public class LoginActivity extends PresenterActivity<LoginView, LoginModel> {

    @BindView(R.id.login_out_button_silver)
    LoginoutButton lbLogin;//微博登陆
    private AuthInfo authInfo;
    private AuthListener loginListener;
    private String token;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        isLogin();
    }

    private void isLogin() {
        String token=AccessTokenKeeper.readAccessToken(getApplicationContext()).getToken();
     if (!TextUtils.isEmpty(token)){
            start();
        }else{
         initView();
     }
    }

    private void initView() {
        authInfo=new AuthInfo(this, Constants.APP_KEY,Constants.REDIRECT_URL,Constants.SCOPE);
        loginListener=new AuthListener();//登陆认证的listener
        lbLogin.setWeiboAuthInfo(authInfo, loginListener);
    }



    private void start() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public class AuthListener implements WeiboAuthListener{
        @Override
        public void onComplete(Bundle bundle) {
            Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(bundle);
            //如果token不为空并且有效
            if (accessToken != null && accessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                        new java.util.Date(accessToken.getExpiresTime()));
                String format = "Token：%1$s \\n有效期：%2$s";
                token=String.format(format, accessToken.getToken(), date);
                Log.e("vera","token="+ readAccessToken(getApplicationContext()).getToken());
                //保存
                AccessTokenKeeper.writeAccessToken(getApplicationContext(), accessToken);
                start();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showToast(LoginActivity.this,e.getMessage());
        }

        @Override
        public void onCancel() {
            ToastUtil.showToast(LoginActivity.this,"取消");
        }
    }

}
