package code.vera.myblog.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import code.vera.myblog.R;

/**
 * Created by vera on 2017/2/8 0008.
 */

public class SearchView extends RefreshView {
    @BindView(R.id.et_info)
    EditText etInfo;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }

    public String getSearchInfo(){
        return  etInfo.getText().toString();
    }
}
