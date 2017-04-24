package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.view.other.BrowserView;

/**
 * 浏览器
 * Created by vera on 2017/2/13 0013.
 */

public class BrowserActivity extends PresenterActivity<BrowserView,VoidModel> {
    private String link;
    public static final String BUNDLER_PARAM_LINK="link";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_browser;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Intent intent=getIntent();
         link=intent.getStringExtra(BUNDLER_PARAM_LINK);
        loadView();
    }

    private void loadView() {
      view.load(link);
    }
    public static void start(Context context, Bundle bundle){
        Intent intent=new Intent(context,BrowserActivity.class);
        intent.putExtra(BUNDLER_PARAM_LINK, bundle.getSerializable(BUNDLER_PARAM_LINK));
        context.startActivity(intent);
    }
}
