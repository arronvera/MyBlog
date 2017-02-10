package code.vera.myblog.view.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by vera on 2016/12/28 0028.
 */

public class BaseView  implements IView {
    protected View rootView;

    @Override
    public void onAttachView(@NonNull View view) {
        this.rootView = view;
    }

    public View getRootView() {
        return rootView;
    }

    public String getStrText(TextView _textVi) {
        return _textVi.getText().toString().trim();
    }

    public void hideSoftKeyBord(AppCompatActivity activity, View v) {
        if (v == null)
            return;
        v.clearFocus();
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
