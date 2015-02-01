package me.ifen.api.ifen.dao.orm;

import me.ifen.core.hibernate.BaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhangjingbo on 15/1/26.
 */
@Entity
@Table(name = "ifen_comment")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment extends BaseEntity {

    public Comment(Long id, String content, Long articleId, Long creatorId, String creatorNickname, Object createTime) {
        this.id = id;
        this.content = content;
        this.articleId = articleId;
        this.creatorId = creatorId;
        this.creatorNickname = creatorNickname;
        this.createTime = (Timestamp) createTime;
    }

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
    private String creatorNickname;

    public Comment() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "article_id")
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Column(name = "creator_id")
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Column(name = "creator_nickname")
    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname;
    }
}