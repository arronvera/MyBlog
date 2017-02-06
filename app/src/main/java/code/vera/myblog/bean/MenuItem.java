package code.vera.myblog.bean;

/**
 * 菜单类
 * Created by vera on 2017/1/23 0023.
 */

public class MenuItem {
    private String text;//文字
    private int pic;//图片

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
