package code.vera.myblog.bean;

/**
 * 评论请求类
 * Created by vera on 2017/2/9 0009.
 */

public class CommentRequestBean {
    private String comment;
    private long id;
    private int comment_ori;//当评论转发微博时，是否评论给原微博，0：否、1：是，默认为0。
    private String rip;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getComment_ori() {
        return comment_ori;
    }

    public void setComment_ori(int comment_ori) {
        this.comment_ori = comment_ori;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }
}
