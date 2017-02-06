package code.vera.myblog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 授权
 */
public class OAuthActivity extends AppCompatActivity {
    @BindView(R.id.webview)
    WebView webView;

    //保存数据信息
    private SharedPreferences preferences;
    private static final String TAG = "OAuthActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        //获取只能被本程序读写的SharedPreferences对象
        preferences = getSharedPreferences("OAuth2.0", Context.MODE_PRIVATE);
        //管理WebView
        WebSettings webSettings = webView.getSettings();
        //启用JavaScript调用功能
        webSettings.setJavaScriptEnabled(true);
        //启用缩放网页功能
        webSettings.setSupportZoom(true);
        //获取焦点
        webView.requestFocus();
        //判断网络连接状态，无网络则去设置网络，有网则继续
//        String url = Constants.URL;
//        webView.loadUrl(url);

    }
}
