package code.vera.myblog.presenter.activity;

import android.content.Context;
import android.content.Intent;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterActivity;
import code.vera.myblog.view.base.VoidView;

/**
 * 常见问题
 */
public class ProblemsActivity extends PresenterActivity<VoidView,VoidModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_problems;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }
    @OnClick(R.id.iv_back)
    public void back(){
        finish();
    }
    public static void start(Context context){
        Intent intent=new Intent(context,ProblemsActivity.class);
        context.startActivity(intent);
    }
}
