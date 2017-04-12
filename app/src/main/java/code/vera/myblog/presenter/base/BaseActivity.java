package code.vera.myblog.presenter.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import code.vera.myblog.BaseApplication;
import code.vera.myblog.utils.ToastUtil;

/**
 * Created by vera on 2016/12/28 0028.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    protected Context mContext;
    protected BaseApplication baseApplication;
    protected String tag;
    private int REQUEST_CODE_PERMISSION = 0x00099;
    private String[] requestPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        baseApplication = BaseApplication.getInstance();
        baseApplication.addRunActivity(this);
        tag = this.getClass().getSimpleName();
//
//        if (!ImageLoader.getInstance().isInited()) {
//            BaseApplication.initImageLoader(getApplicationContext());
//        }

        View viRoot = getLayoutInflater().inflate(getLayoutResId(), null);
        setContentView(viRoot);

//        ScreenUtil.init(this, AppConfig.BASE_SCREEN_WIDTH);
        ButterKnife.bind(this);
//        scaleView();
        onAttach();
    }

    /**
     * 获取 layout 资源id
     *
     * @return
     */
    protected abstract int getLayoutResId();


    /**
     * 固定 View, Listener, onAttachData
     */
    protected abstract void onAttach();




    @Override
    public void onBackPressed() {
        boolean flag = false;
        for (BackListener backListener : backListeners) {
            boolean process = backListener.onBackPressed();
            if (!flag) {
                flag = process;
            }
        }
        if (flag) {
            return;
        }
        super.onBackPressed();
    }

    private List<BackListener> backListeners = new ArrayList<>();

    public void addBackListener(BackListener listener) {
        backListeners.add(listener);
    }

    public void removeBackListener(BackListener listener) {
        backListeners.remove(listener);
    }

    public static interface BackListener {
        boolean onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        baseApplication.removeRunActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.bind(Unbinder.EMPTY);
//        if (view != null) {
//            ButterKnife.unbind(view);
//        }
    }

    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    public void startActivity(Class<?> cls, long time) {
        final Intent intent = new Intent(mContext, cls);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, time);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    // 隐藏软键盘
    public void hideSoftKeyBord(View v) {
        if (v == null)
            return;
        v.clearFocus();
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    //请求权限
    public void requestPermission(int requestCode, String... permissions) {
        requestPermissions = permissions;

        requestPermission(permissions, requestCode);
    }

    //请求权限
    public void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new
                    String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    //检测所有的权限是否都已授权
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //获取权限集中需要申请权限的列表
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    // 系统请求权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION);
            } else {
                permissionFail(REQUEST_CODE_PERMISSION);
                showTipsDialog();
            }
        }
    }

    //确认所有的权限是否都已授权
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //显示提示对话框
    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }

    //启动当前应用设置页面
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    //获取权限成功
    public void permissionSuccess(int requestCode) {
        Log.d("vera","获取权限成功=" + requestCode);
    }

    //权限获取失败
    public void permissionFail(int requestCode) {
        Log.d("vera","获取权限失败=" + requestCode);
    }
}
