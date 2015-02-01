package me.ifen.api.ifen.resources.v1.model;

/**
 * Created by zhangjingbo on 15/1/28.
 */
public class SupportAO {

    /**
     * 点赞类型
     * 0 - 文章
     * 1 - 评论
     */
    private Integer type;

    /**
     * 点赞对象id
     */
    private Long id;

    /**
     * 评论人id
     */
    private Long creatorId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
