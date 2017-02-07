package code.vera.myblog.presenter.subscribe;

import android.content.Context;

import code.vera.myblog.utils.DialogUtils;
import rx.Subscriber;

/**
 *  自定义Subscriber，对加载进行操作
 * Created by vera on 2017/2/7 0007.
 */

public class CustomSubscriber<T> extends Subscriber<T> implements SubscribeCancelListener  {

    private SubscribeListener mSubscribeListener;
    private Context context;
    private ProgressDialongHandler progressDialongHandler;


    public CustomSubscriber(SubscribeListener subscribeListener, Context context,
                            Boolean dialogShow) {
        mSubscribeListener = subscribeListener;
        this.context = context;
        progressDialongHandler = new ProgressDialongHandler(context, dialogShow, this);
    }

    public CustomSubscriber(Context context, Boolean dialogShow) {
        this.context = context;
        progressDialongHandler = new ProgressDialongHandler(context, dialogShow, this);
    }


    /**
     * 显示加载dialog
     */
    private void showProgressDialog() {
        if (progressDialongHandler != null) {
            progressDialongHandler.obtainMessage(ProgressDialongHandler.SHOW_PROGRESS_DIALOG)
                    .sendToTarget();
        }
    }


    /**
     * 隐藏dialog
     */
    private void dismissProgressDialog() {
        if (progressDialongHandler != null) {
            progressDialongHandler.obtainMessage(ProgressDialongHandler.DISMISS_PROGRESS_DIALOG)
                    .sendToTarget();
            progressDialongHandler = null;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        onEnd();
    }

    @Override
    public final void onError(Throwable e) {
        onEnd();
        onFailure(e);
    }

    @Override
    public void onNext(T t) {
        if (mSubscribeListener != null) {
            mSubscribeListener.onNext(t);
        }
    }


    public void onEnd() {
        dismissProgressDialog();
    }

    public void onFailure(Throwable e) {
//        if (e instanceof ApiException) {
//            ApiException apiException = (ApiException) e;
//            ResponseBean responseBean = apiException.getResponseBean();
//            if (responseBean != null && responseBean.isNeedRelogin()) {
//                DialogUtils.showDialog(context, "温馨提示", "需要重新登录", "确定", new DialogInterface
//                        .OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        BaseApplication.getInstance().saveUser(null);
//                        BaseApplication.getInstance().clearActivities();
//                        LoginActivity.start(context);
//                    }
//                });
//                return;
//            }
//            resultError(apiException.getCode(), apiException.getMessage(), context);

//            e.printStackTrace();
//        } else if (e instanceof SocketTimeoutException) {
//            DialogUtils.showNotice(context, "提示", "网络请求超时！");
//        } else if (e instanceof ConnectException) {
//            DialogUtils.showNotice(context, "提示", "无法连接到服务器，请检查网络是否正常或请稍后再试！");
//        } else {
//            e.printStackTrace();
//            DialogUtils.showNotice(context, "提示", "请求失败，请检查网络是否正常！");
//        }
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    /**
     * 对返回错误结果进行处理
     */
    public void resultError(int code, String msg, final Context context) {
        try {
            if (code == 401) {
            } else {
                DialogUtils.showNotice(context, "提示", msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
