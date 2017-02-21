package code.vera.myblog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import ww.com.core.Debug;

/**
 * 欢迎界面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        isLogin();
        Intent intent=new Intent(this,LoginActivity.class);

    }

    private void isLogin() {
        String token=AccessTokenKeeper.readAccessToken(getApplicationContext()).getToken();
        if (!TextUtils.isEmpty(token)){
         Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Debug.d("未登陆---");
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }
}
