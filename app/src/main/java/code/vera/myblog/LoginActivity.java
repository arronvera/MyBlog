package code.vera.myblog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

import java.text.SimpleDateFormat;

import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.LoginView;
import ww.com.core.Debug;

import static code.vera.myblog.AccessTokenKeeper.readAccessToken;

/**
 * 登陆界面
 */
public class LoginActivity extends PresenterActivity<LoginView, VoidModel> {
    private AuthListener loginListener;
    private String token;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        loginListener=new AuthListener();//登陆认证的listener
        view.setAuthInfo(loginListener);
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
                MainActivity.start(mContext);
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
