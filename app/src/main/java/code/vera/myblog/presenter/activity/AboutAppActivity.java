package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.Intent;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.view.base.VoidView;

public class AboutAppActivity extends PresenterActivity<VoidView, VoidModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about_app;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutAppActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }
}
