package me.ifen.core.hibernate;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BaseEntity implements Serializable, Cloneable{

    /** 实体版本标识*/
    protected Integer version = 0;

    /** 实体别名*/
//    protected String alias;

    /** 实体更新时间*/
    protected Timestamp updateTime = new Timestamp(System.currentTimeMillis());

    /** 实体创建时间*/
    protected Timestamp createTime = new Timestamp(System.currentTimeMillis());

    /** 删除标志位 **/
    protected Integer isDelete = 0;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public BaseEntity clone() throws CloneNotSupportedException {
        return ((BaseEntity) super.clone());
    }

    @Override
    public int hashCode() {
        long hash = -1L;
        if (this.getId() != null) {
            hash = this.getId();
        }
        return new Long(hash + 1000).hashCode();
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) {
            return true;
        }

        if (another == null) {
            return false;
        }

        if (!(another instanceof BaseEntity)) {
            return false;
        }

        if (!this.getClass().equals(another.getClass())) {
            return false;
        }

        BaseEntity toBeCompare = (BaseEntity) another;

        if (toBeCompare.getId() == null
                && this.getId() == null) {
            return true;
        }

        if (toBeCompare.getId() != null
                && this.getId() != null
                && toBeCompare.getId().equals(this.getId())) {
            return true;
        }

        return false;
    }


    @Transient
    public abstract Long getId();

    public abstract void setId(Long id);

    @Version
    @Column(nullable = false)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(nullable = false)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Column(nullable = false, updatable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(nullable = false, name = "is_delete")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

//    @Transient
//    public String getAlias() {
//        return alias;
//    }
//
//    public void setAlias(String alias) {
//        this.alias = alias;
//    }
}
