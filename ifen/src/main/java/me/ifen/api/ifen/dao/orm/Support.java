package me.ifen.api.ifen.dao.orm;

import me.ifen.core.hibernate.BaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by zhangjingbo on 15/1/26.
 */
@Entity
@Table(name = "ifen_support")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Support extends BaseEntity {

    public Support() {
    }

//    public Support(Long id, Long object_id, Long creator_id, Integer type, Integer isDelete) {
//        this.id = id;
//        this.object_id = object_id;
//        this.creator_id = creator_id;
//        this.type = type;
//        this.isDelete = isDelete;
//    }

    private Long id;

    /**
     * 点赞对象id
     */
    private Long object_id;
    /**
     * 点赞人id
     */
    private Long creator_id;
    /**
     * 点赞对象类型
     */
    private Integer type;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "object_id")
    public Long getObject_id() {
        return object_id;
    }

    public void setObject_id(Long object_id) {
        this.object_id = object_id;
    }

    @Column(name = "creator_id")
    public Long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Long creator_id) {
        this.creator_id = creator_id;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}