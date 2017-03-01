package code.vera.myblog.bean;

/**
 * 排序的实体类
 * Created by vera on 2017/3/1 0001.
 */

public class SortBean {
    /**
     * 显示的数据
     */
    private String name;
    /**
     * 显示的数据首字母
     */
    private String sortLetters;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
