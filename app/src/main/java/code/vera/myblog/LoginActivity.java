package code.vera.myblog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import ww.com.core.Debug;

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
        initView();
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
            Debug.d("complete");
            Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(bundle);
            //如果token不为空并且有效
            if (accessToken != null && accessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                        new java.util.Date(accessToken.getExpiresTime()));
                String format = "Token：%1$s \\n有效期：%2$s";
                token=String.format(format, accessToken.getToken(), date);
                Debug.d("token="+ readAccessToken(getApplicationContext()).getToken());
                Debug.d("time="+ readAccessToken(getApplicationContext()).getExpiresTime());
                //保存
                ToastUtil.showToast(mContext,"登陆成功~");
                AccessTokenKeeper.writeAccessToken(getApplicationContext(), accessToken);
                start();
            }else{
                Debug.d("token为空");

            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Debug.d("e="+e.getMessage());
            ToastUtil.showToast(mContext,e.getMessage());
        }

        @Override
        public void onCancel() {
            Debug.d("cancel");

            ToastUtil.showToast(mContext,"取消");
        }
    }
    public static void start(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);

    }

}
