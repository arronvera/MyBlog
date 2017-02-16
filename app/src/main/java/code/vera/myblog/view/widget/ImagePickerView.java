package code.vera.myblog.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import java.util.List;

import code.vera.myblog.adapter.ImagesAdapter;

/**
 * 图片选择
 * Created by vera on 2017/2/16 0016.
 */

public class ImagePickerView extends GridView {

    private Context context;
    private int maxSize=9;//最多选择9张
    private int columNumber=3;//列数
    private ImagesAdapter adapter;//适配器
    private List<String> imageList;//图片选择list

    public ImagePickerView(Context context) {
        super(context);
        this.context=context;

    }

    public ImagePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

    }

    public ImagePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;

    }
}
