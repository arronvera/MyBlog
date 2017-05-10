package code.vera.myblog.presenter.fragment.set;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import butterknife.OnClick;
import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.LoginActivity;
import code.vera.myblog.R;
import code.vera.myblog.bean.UserInfoBean;
import code.vera.myblog.config.Constants;
import code.vera.myblog.manager.DataCleanManager;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.activity.AboutAppActivity;
import code.vera.myblog.presenter.activity.AboutAuthorActivity;
import code.vera.myblog.presenter.activity.PostActivity;
import code.vera.myblog.presenter.activity.ProblemsActivity;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.utils.DialogUtils;
import code.vera.myblog.utils.FileUtils;
import code.vera.myblog.utils.SaveUtils;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.other.SetView;

import static code.vera.myblog.presenter.activity.PostActivity.PARAM_NEW_TEXT;
import static code.vera.myblog.presenter.activity.PostActivity.PARAM_POST_TYPE;
import static code.vera.myblog.utils.FileUtils.SIZETYPE_MB;

/**
 * 设置
 * Created by vera on 2017/2/13 0013.
 */

public class SetFragment extends PresenterFragment<SetView, VoidModel> {
    private UserInfoBean userInfoBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_set;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        initData();
        userInfoBean = SaveUtils.getUser(mContext);
        view.showUser(userInfoBean);
    }

    private void initData() {
        File cacheDir = StorageUtils.getCacheDirectory(mContext);
        Double size = FileUtils.getFileOrFilesSize(cacheDir.getAbsolutePath(), SIZETYPE_MB);
        view.setCache(size);

    }

    @OnClick({R.id.rl_exit, R.id.rl_clear_cache, R.id.rl_problems,
            R.id.rl_author, R.id.rl_check_vision, R.id.rl_about_app,
            R.id.rl_feedback})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.rl_exit://退出登录
                DialogUtils.showDialog(mContext, "", "你是否确定注销登陆？", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logOut();
                    }
                }, "取消", null);
                break;
            case R.id.rl_clear_cache://清空缓存
                DialogUtils.showDialog(mContext, "", "你是否确定清除缓存？", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearCache();
                    }
                }, "取消", null);
                break;
            case R.id.rl_problems://问题
                ProblemsActivity.start(mContext);
                break;
            case R.id.rl_author://关于作者
                AboutAuthorActivity.start(mContext);
                break;
            case R.id.rl_check_vision://检查更新
                ToastUtil.showToast(mContext, "当前版本已经是最新版本了哦~");
                break;
            case R.id.rl_about_app://关于App
                AboutAppActivity.start(mContext);
                break;
            case R.id.rl_feedback://反馈
                Bundle bundle = new Bundle();
                String feedback = "@炎小香 ";
                bundle.putString(PARAM_NEW_TEXT, feedback);
                bundle.putInt(PARAM_POST_TYPE, Constants.POST_TYPE_NEW);
                PostActivity.start(mContext, bundle);
                break;
        }
    }

    private void clearCache() {
        DataCleanManager.cleanCustomCache(StorageUtils.getCacheDirectory(mContext).getAbsolutePath());
        initData();
    }

    /**
     * 退出登录
     */
    private void logOut() {
        //清空token
        AccessTokenKeeper.clear(mContext);
        //跳转并且清空栈
        Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
