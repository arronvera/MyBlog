package code.vera.myblog.view.tab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import code.vera.myblog.R;
import code.vera.myblog.adapter.RvAdapter;
import code.vera.myblog.view.RefreshView;
import code.vera.myblog.view.widget.LikeView;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class TabCommentView extends RefreshView {
    private LikeView likeView;
    private Context context;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        context=view.getContext();
        likeView=new LikeView(context);
    }

    @Override
    public void setAdapter(RvAdapter adapter) {
        super.setAdapter(adapter);
    }

    public void setLikeView(ImageView imageView) {
        imageView.setImageResource(R.mipmap.ic_heart_sel);
        likeView.setImage(context.getResources().getDrawable(R.mipmap.ic_heart_sel));
        likeView.show(imageView);
    }
}
