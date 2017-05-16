package code.vera.myblog.view.other;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.adapter.RvAdapter;
import code.vera.myblog.view.RefreshView;

/**
 * Created by vera on 2017/2/28 0028.
 */

public class TopicView extends RefreshView {
    @BindView(R.id.tv_topic_title)
    TextView tvTopicTtile;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }

    @Override
    public void setAdapter(RvAdapter adapter) {
        super.setAdapter(adapter);
    }

    public void setTopicTitle(String topic) {
        tvTopicTtile.setText(topic);
    }
}
