package code.vera.myblog.view.user;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.adapter.RvAdapter;
import code.vera.myblog.config.Constants;
import code.vera.myblog.view.RefreshView;

/**
 * Created by vera on 2017/3/20 0020.
 */

public class UsersFollowsView extends RefreshView {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }

    @Override
    public void setAdapter(RvAdapter adapter) {
        super.setAdapter(adapter);
    }

    public void showTitle(int type) {
        switch (type) {
            case Constants.TYPE_CONCERN:
                tvTitle.setText("关注的人");
                break;
            case Constants.TYPE_FOLLOWERES:
                tvTitle.setText("粉丝");
                break;

        }
    }
}
