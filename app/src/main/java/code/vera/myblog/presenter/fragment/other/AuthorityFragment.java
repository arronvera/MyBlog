package code.vera.myblog.presenter.fragment.other;

import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import code.vera.myblog.R;
import code.vera.myblog.callback.AuthorityFragmentCallBack;
import code.vera.myblog.config.Constants;
import code.vera.myblog.model.base.VoidModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.other.AuthorityView;

/**
 * 权限
 * Created by vera on 2017/4/6 0006.
 */

public class AuthorityFragment extends PresenterFragment<AuthorityView,VoidModel> {
    private AuthorityFragmentCallBack fragmentCallBack;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_authority;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }
    @OnClick({R.id.rb_public,R.id.rb_friends,R.id.rb_self})
    public void doClick(View v){
        switch (v.getId()){
            case R.id.rb_public:
                Bundle bundle=new Bundle();
                bundle.putInt("visible" , Constants.VISIBLE_ALL);
                fragmentCallBack.callbackAuthority(bundle);
                break;
            case R.id.rb_friends:
                Bundle bundle2=new Bundle();
                bundle2.putInt("visible" , Constants.VISIBLE_FRIENDS);
                fragmentCallBack.callbackAuthority(bundle2);
                break;
            case R.id.rb_self:
                Bundle bundle3=new Bundle();
                bundle3.putInt("visible" , Constants.VISIBLE_SELF);
                fragmentCallBack.callbackAuthority(bundle3);
                break;
        }
    }
    public void setFragmentCallBack(AuthorityFragmentCallBack callBack){
        this.fragmentCallBack=callBack;
    }
}
