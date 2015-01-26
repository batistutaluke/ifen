package me.ifen.core.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

public class AuditableEntityListener extends DefaultSaveOrUpdateEventListener {
    private static Logger logger = LoggerFactory.getLogger(AuditableEntityListener.class);
    /**
     * 处理实体的保持和更新事件
     *
     * @param event The update event to be handled.
     * @throws org.hibernate.HibernateException
     *
     */
    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
        Object object = event.getObject();

        //如果对象是BaseEntity子类,添加时间审计信息.
        if (object instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) object;
            if (entity.getId() == null) {
                entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            } else {
                entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            }
        }

        //如果对象是AuditableEntity子类,添加用户审计信息.
//        if(object instanceof AuditableEntity) {
//            AuditableEntity entity = (AuditableEntity) object;
//            SysUser user = SpringSecurityUtils.getCurrentUser();
//            if(user != null) {
//                if (entity.getId() == null) {
//                    entity.setCreateUser(user.getId());
//                    entity.setUpdateUser(user.getId());
//                } else {
//                    entity.setUpdateUser(user.getId());
//                }
//            }
//        }

        super.onSaveOrUpdate(event);
    }
}
