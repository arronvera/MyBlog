package code.vera.myblog.presenter.activity;

import android.content.Intent;

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
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_browser;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Intent intent=getIntent();
         link=intent.getStringExtra("link");
        loadView();
    }

    private void loadView() {
      view.load(link);
    }
}
