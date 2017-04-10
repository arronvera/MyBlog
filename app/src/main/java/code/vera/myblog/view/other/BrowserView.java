package code.vera.myblog.view.other;

import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.view.base.BaseView;
import code.vera.myblog.view.widget.WebViewWithProgress;

/**
 * Created by vera on 2017/4/10 0010.
 */

public class BrowserView extends BaseView {
    @BindView(R.id.progress)
    WebViewWithProgress webViewWithProgress;//水平进度条

    private WebView webView;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        webView = webViewWithProgress.getWebView();
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    }

    public void load(String link) {
        webView.loadUrl(link);
    }
}
