package code.vera.myblog.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.view.base.IView;

/**
 * Created by vera on 2017/4/18 0018.
 */

public class RegisterView implements IView {
    @BindView(R.id.et_user_name)
    EditText etName;

    @Override
    public void onAttachView(@NonNull View view) {

    }

    public String getUserName() {
        return etName.getText().toString();
    }
}
