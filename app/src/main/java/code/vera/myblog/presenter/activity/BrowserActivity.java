package code.vera.myblog.presenter.activity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.model.other.BrowserModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.base.VoidView;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * 浏览器
 * Created by vera on 2017/2/13 0013.
 */

public class BrowserActivity extends PresenterActivity<VoidView,BrowserModel> {
    @BindView(R.id.progress)
    SmoothProgressBar smoothProgressBar;//水平进度条
    @BindView(R.id.webview)
    WebView webView;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_browser;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        initView();
    }

    private void initView() {
        smoothProgressBar.setIndeterminate(true);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    view.loadUrl("http://" + url);
                else
                    view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    smoothProgressBar.setVisibility(View.VISIBLE);
                } else if (newProgress == 100) {
                    smoothProgressBar.setVisibility(View.GONE);
                    invalidateOptionsMenu();
                }
                smoothProgressBar.setProgress(newProgress);

                super.onProgressChanged(view, newProgress);
            }

        });
        setting.setJavaScriptCanOpenWindowsAutomatically(true);

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
        super.onBackPressed();

    }
}
