package code.vera.myblog.bean.home;

import java.io.Serializable;

/**
 * Created by vera on 2017/2/7 0007.
 */

public class PicBean implements Serializable{
    private String thumbnail_pic;

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }
}
