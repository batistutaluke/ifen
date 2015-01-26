package me.ifen.core.hibernate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AuditableEntity extends BaseEntity{

    /** 实体更新人*/
    protected Long updateUser = -1L;

    /** 实体创建人*/
    protected Long createUser = -1L;

    @Column(nullable = false)
    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    @Column(nullable = false, updatable = false)
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}
