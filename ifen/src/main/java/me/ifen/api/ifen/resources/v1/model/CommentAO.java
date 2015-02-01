package me.ifen.api.ifen.resources.v1.model;

/**
 * Created by zhangjingbo on 15/1/28.
 */
public class CommentAO {

    private Long id;

    /**
     * 评论内容
     */
    private String content;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 评论人id
     */
    private Long creatorId;
    /**
     * 评论人昵称
     */
    private String nickname;
    /**
     * 点赞个数
     */
    private Integer supports;
    /**
     * 评论时间
     */
    private Long time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSupports() {
        return supports;
    }

    public void setSupports(Integer supports) {
        this.supports = supports;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
