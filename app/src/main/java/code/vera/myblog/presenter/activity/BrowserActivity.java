package code.vera.myblog.presenter.activity;

import android.content.Intent;
import android.webkit.WebView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.model.other.BrowserModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.base.VoidView;
import code.vera.myblog.view.widget.WebViewWithProgress;

/**
 * 浏览器
 * Created by vera on 2017/2/13 0013.
 */

public class BrowserActivity extends PresenterActivity<VoidView,BrowserModel> {
    @BindView(R.id.progress)
    WebViewWithProgress webViewWithProgress;//水平进度条
    private WebView webView;
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
        initView();
        loadView();
    }

    private void initView() {
        webView = webViewWithProgress.getWebView();
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    }
    private void loadView() {
        webView.loadUrl(link);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
