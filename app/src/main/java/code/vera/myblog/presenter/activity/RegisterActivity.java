package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.Intent;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.RegisterAPI;

import butterknife.OnClick;
import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.R;
import code.vera.myblog.config.Constants;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.view.RegisterView;

public class RegisterActivity extends PresenterActivity<RegisterView,VoidModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }

    public static void start(Context context){
        Intent intent=new Intent(context,RegisterActivity.class);
        context.startActivity(intent);
    }
    @OnClick(R.id.btn_register)
    public void register(){
        RegisterAPI registerAPI=new RegisterAPI(mContext, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(mContext));
        registerAPI.suggestions(view.getUserName(), new RequestListener() {
            @Override
            public void onComplete(String s) {

            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });
    }
}
