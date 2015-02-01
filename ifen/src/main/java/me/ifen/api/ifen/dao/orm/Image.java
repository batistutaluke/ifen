package me.ifen.api.ifen.dao.orm;

import me.ifen.core.hibernate.BaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by zhangjingbo on 15/1/26.
 */
@Entity
@Table(name = "ifen_image")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Image extends BaseEntity {
    private Long id;

    /**
     * 描述，不超过10个字
     */
    private String description;
    /**
     * 图片url
     */
    private String url;
    /**
     * 创建者id
     */
    private String creator_id;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "creator_id")
    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }
}