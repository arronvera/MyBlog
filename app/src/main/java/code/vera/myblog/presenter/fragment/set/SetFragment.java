package code.vera.myblog.presenter.fragment.set;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import butterknife.OnClick;
import code.vera.myblog.AccessTokenKeeper;
import code.vera.myblog.LoginActivity;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.manager.DataCleanManager;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.utils.DialogUtils;
import code.vera.myblog.utils.FileUtils;
import code.vera.myblog.utils.SaveUtils;
import code.vera.myblog.utils.ToastUtil;
import code.vera.myblog.view.other.SetView;

import static code.vera.myblog.utils.FileUtils.SIZETYPE_MB;

/**
 * 设置
 * Created by vera on 2017/2/13 0013.
 */

public class SetFragment extends PresenterFragment<SetView,VoidModel> {
    private UserInfoBean userInfoBean;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_set;
    }
    @Override

    protected void onAttach() {
        super.onAttach();
        initData();
        userInfoBean=SaveUtils.getUser(mContext);
        view.showUser(userInfoBean);
    }

    private void initData() {
        File cacheDir = StorageUtils.getCacheDirectory(mContext);
        Double size=FileUtils.getFileOrFilesSize(cacheDir.getAbsolutePath(),SIZETYPE_MB);
        view.setCache(size);

    }

    @OnClick({R.id.rl_exit,R.id.rl_clear_cache,R.id.rl_problems,R.id.rl_author,R.id.rl_check_vision})
    public void doClick(View v){
        switch (v.getId()){
            case R.id.rl_exit:
                DialogUtils.showDialog(mContext, "", "你是否确定注销登陆？", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logOut();
                    }
                }, "取消", null);
                break;
            case R.id.rl_clear_cache:
                DialogUtils.showDialog(mContext, "", "你是否确定清除缓存？", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearCache();
                    }
                }, "取消", null);
                break;
            case R.id.rl_problems:
                //todo
                break;
            case R.id.rl_author:
                //todo
                break;
            case R.id.rl_check_vision://检查更新
                ToastUtil.showToast(mContext,"当前版本已经是最新版本了哦~");
                break;
        }
    }

    private void clearCache() {
        DataCleanManager.cleanCustomCache( StorageUtils.getCacheDirectory(mContext).getAbsolutePath());
        initData();
    }

    /**
     * 退出登录
     */
    private void logOut() {
        //清空token
        AccessTokenKeeper.clear(mContext);
        //跳转并且清空栈
        Intent intent = new Intent(mContext,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
