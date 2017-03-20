package code.vera.myblog.bean;

/**
 * Created by vera on 2017/3/20 0020.
 */

public class UnReadBean {

    /**
     * status : 0
     * follower : 1
     * cmt : 0
     * dm : 1
     * mention_status : 0
     * mention_cmt : 0
     * group : 0
     * private_group : 0
     * notice : 0
     * invite : 0
     * badge : 0
     * photo : 0
     * msgbox : 0
     */

    private int status;
    private int follower;
    private int cmt;
    private int dm;
    private int mention_status;
    private int mention_cmt;
    private int group;
    private int private_group;
    private int notice;
    private int invite;
    private int badge;
    private int photo;
    private int msgbox;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getCmt() {
        return cmt;
    }

    public void setCmt(int cmt) {
        this.cmt = cmt;
    }

    public int getDm() {
        return dm;
    }

    public void setDm(int dm) {
        this.dm = dm;
    }

    public int getMention_status() {
        return mention_status;
    }

    public void setMention_status(int mention_status) {
        this.mention_status = mention_status;
    }

    public int getMention_cmt() {
        return mention_cmt;
    }

    public void setMention_cmt(int mention_cmt) {
        this.mention_cmt = mention_cmt;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getPrivate_group() {
        return private_group;
    }

    public void setPrivate_group(int private_group) {
        this.private_group = private_group;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    public int getInvite() {
        return invite;
    }

    public void setInvite(int invite) {
        this.invite = invite;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getMsgbox() {
        return msgbox;
    }

    public void setMsgbox(int msgbox) {
        this.msgbox = msgbox;
    }
}
