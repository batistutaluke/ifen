package me.ifen.api.ifen.resources.v1.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhangjingbo on 15/1/26.
 */
@XmlRootElement
public class ArticlesAO {

    private Long id;
    private String title;
    private String image;
    private String summary;
    private String author;
    private Long time;
    /**
     * 点赞个数
     */
    private long supports;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public long getSupports() {
        return supports;
    }

    public void setSupports(long supports) {
        this.supports = supports;
    }

}
