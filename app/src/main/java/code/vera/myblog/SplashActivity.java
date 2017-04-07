package code.vera.myblog;

import android.os.Handler;
import android.text.TextUtils;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import butterknife.BindView;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.base.VoidView;
import ww.com.core.Debug;

/**
 * 欢迎界面
 */
public class SplashActivity extends PresenterActivity<VoidView, VoidModel> {
    @BindView(R.id.logo_text)
    HTextView textView;
    private Handler handler;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setAnimateType(HTextViewType.TYPER);
                textView.animateText("用圈艺");
            }
        }, 100);
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setAnimateType(HTextViewType.ANVIL);
                textView.animateText("用圈艺,分享属于你的艺术");
            }
        }, 1000);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isLogin();
            }
        }, 2600);


    }

    /**
     * 是否登陆
     */
    private void isLogin() {
        String token = AccessTokenKeeper.readAccessToken(getApplicationContext()).getToken();
        if (!TextUtils.isEmpty(token)) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            LoginActivity.start(mContext);
            finish();
        } else {
            Debug.d("未登陆---");
//            Intent intent=new Intent(this,LoginActivity.class);
//            startActivity(intent);
            LoginActivity.start(mContext);
            finish();
        }
    }
}
