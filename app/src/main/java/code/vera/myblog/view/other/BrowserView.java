package code.vera.myblog.view.other;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.view.base.BaseView;
import code.vera.myblog.view.widget.X5WebView;

/**
 * Created by vera on 2017/4/10 0010.
 */

public class BrowserView extends BaseView {
    //    @BindView(R.id.progress)
//    WebViewWithProgress webViewWithProgress;//水平进度条
//
//    private WebView webView;
    @BindView(R.id.webview)
    X5WebView webView;
    @BindView(R.id.progrese_bar)
    ProgressBar progressBar;
    private Context context;

    @Override
    public void onAttachView(@NonNull View view) {
//        super.onAttachView(view);
//        webView = webViewWithProgress.getWebView();
//        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        context = view.getContext();
        progressBar.setMax(100);
        progressBar.setProgressDrawable(context.getResources()
                .getDrawable(R.drawable.progress_bar_horizontal));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // mTestHandler.sendEmptyMessage(MSG_OPEN_TEST_URL);
//                mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?
//                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
//                    changGoForwardButton(view);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
//                FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
//                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
//                viewGroup.removeView(normalView);
//                viewGroup.addView(view);
//                myVideoView = view;
//                myNormalView = normalView;
//                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });

    }

    public void load(String link) {
        webView.loadUrl(link);
    }
}
