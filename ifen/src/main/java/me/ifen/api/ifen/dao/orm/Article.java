package me.ifen.api.ifen.dao.orm;

import me.ifen.core.hibernate.BaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhangjingbo on 15/1/26.
 */
@Entity
@Table(name = "ifen_article")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Article extends BaseEntity {

    public Article() {
    }

    public Article(Long id, String title, String image, String summary, String author, Object createTime) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.summary = summary;
        this.author = author;
        this.createTime = (Timestamp) createTime;
    }

    private Long id;

    /**
     * 文章标题
     */
    private String title;
    /**
     * 图片url
     */
    private String image;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 正文
     */
    private String content;
    /**
     * 作者
     */
    private String author;
    /**
     * 创建人id
     */
    private Long creatorId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "creator_id")
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}