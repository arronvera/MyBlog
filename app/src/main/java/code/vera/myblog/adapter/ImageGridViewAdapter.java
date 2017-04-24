package code.vera.myblog.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import code.vera.myblog.R;
import ww.com.core.Debug;

/**
 * Created by vera on 2017/4/24 0024.
 */

public class ImageGridViewAdapter extends ABaseAdapter<MediaBean> {

    private AddImgGridListener listener;
    private GridView gridView;
    private int maxSize;
    public void setListener(AddImgGridListener listener) {
        this.listener = listener;
    }
    public ImageGridViewAdapter(Context context) {
        super(context);
        maxSize = 9;
    }

    public void setGridView(GridView gridView) {
        this.gridView = gridView;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public int getCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        }
            return 0;
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_gallery;
    }

    @Override
    protected IViewHolder<MediaBean> getViewHolder(int position) {

        return new IViewHolder<MediaBean>() {
            ImageView itemImg;
            ImageView delImg;

            @Override
            public void initView() {
                itemImg = findView(R.id.iv_item_gallery);
                delImg = findView(R.id.iv_delete);
            }

            @Override
            public void buildData(final int position, MediaBean obj) {
                if (obj!=null) {
                    Debug.d("path="+obj.getOriginalPath());
                    Bitmap bitmap = BitmapFactory.decodeFile(obj.getOriginalPath());
                    itemImg.setImageBitmap(bitmap);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onItemClicked(position);
                            }
                        }
                    });
                    delImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onDeleteItem(position);
                            }
                        }
                    });
                } else {
                    delImg.setOnClickListener(null);
                }

            }
        };
    }
//
//    private ImagePreviewBean getImagePreviewDatas() {
//        ImagePreviewBean imagePreviewBean = new ImagePreviewBean();
//        ArrayList<ImagePreview> imagePreviews = new ArrayList<>();
//        for (String s : datas) {
//            ImagePreview imagePreview = new ImagePreview(s, 0, 0);
//            imagePreviews.add(imagePreview);
//        }
//        View firstItemView = gridView.getChildAt(0);
//        if (firstItemView != null) {
//            int w = firstItemView.getWidth();
//            int h = firstItemView.getWidth();
//            int[] startingLocation = new int[2];
//            firstItemView.getLocationOnScreen(startingLocation);
//            startingLocation[0] += w / 2;
//            startingLocation[1] += h / 2;
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                startingLocation[1] -= ScreenUtil.getStatusHeight(getContext());
//            }
//            for (int i = 0; i < datas.size(); i++) {
//                int[] location = new int[2];
//                location[0] = startingLocation[0] + w * (0 + i % 3);
//                location[1] = startingLocation[1] + h * (0 + i / 3);
//                imagePreviews.get(i).setStartLoaction(location);
//            }
//        }
//        imagePreviewBean.imagePreviews = imagePreviews;
//        return imagePreviewBean;
//    }

    public interface AddImgGridListener {
        void onItemClicked(int position);

        void onDeleteItem(int position);
    }
}
