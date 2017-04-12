package code.vera.myblog.view.collection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import code.vera.myblog.R;
import code.vera.myblog.view.RefreshView;
import code.vera.myblog.view.widget.LikeView;

/**
 * Created by vera on 2017/4/12 0012.
 */

public class CollectionView extends RefreshView {
    private LikeView likeView;
    private Context context;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        context=view.getContext();
        likeView = new LikeView(context);

    }

    public void showLikeView(ImageView imageView) {
        imageView.setImageResource(R.mipmap.ic_heart_sel);
        likeView.setImage(R.mipmap.ic_heart_sel);
        likeView.show(imageView);
    }
}
