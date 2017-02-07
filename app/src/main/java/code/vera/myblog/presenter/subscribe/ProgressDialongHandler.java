package code.vera.myblog.presenter.subscribe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import code.vera.myblog.R;
import ww.com.core.ScreenUtil;

/**
 * Created by vera on 2017/2/7 0007.
 */

public class ProgressDialongHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog pd;
    private Context context;

    private SubscribeCancelListener subscribeCancelListener;
    private boolean cancelable;

    public ProgressDialongHandler(Context context, boolean cancelable, SubscribeCancelListener
            subscribeCancelListener) {
        this.context = context;
        this.cancelable = cancelable;
        this.subscribeCancelListener = subscribeCancelListener;
        initProgressDailog();
    }

    private void initProgressDailog() {
        if (pd == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_loading, null);
            ScreenUtil.scale(view);
            pd = new ProgressDialog(context, R.style.LoadingDialog);
            pd.setCanceledOnTouchOutside(false);
            pd.setCancelable(cancelable);


            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        subscribeCancelListener.onCancelProgress();
                    }
                });
            }

            if (cancelable&&!pd.isShowing()) {
                pd.show();
            }

            pd.setContentView(view);
        }
    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    private void showProgressDialog() {
        if (pd != null && pd.isShowing())
            return;
        if (cancelable) {
            initProgressDailog();
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                showProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}
