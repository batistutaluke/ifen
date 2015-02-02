package me.ifen.api.ifen.services.impl;

import com.google.common.collect.Maps;
import me.ifen.api.ifen.dao.SupportDao;
import me.ifen.api.ifen.dao.orm.Support;
import me.ifen.api.ifen.exception.SupportObjectNotExistsException;
import me.ifen.api.ifen.services.SupportService;
import me.ifen.core.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by zhangjingbo on 15/2/2.
 */
@Service("baseSupportService")
@Transactional
public abstract class BaseSupportServiceImpl implements SupportService {
    private static Logger logger = LoggerFactory.getLogger(BaseSupportServiceImpl.class);


    @Autowired
    private SupportDao supportDao;

    @Override
    public void support(Long id, Long creatorId) {
        Support support = supportDao.findUniqueByObjectIdAndCreatorId(id, creatorId);
        if (null == support) {
            Support newSupport = new Support();
            newSupport.setType(getType());
            newSupport.setCreator_id(creatorId);
            newSupport.setObject_id(id);
            supportDao.saveOnly(newSupport);
        } else {
            support.setIsDelete(Constants.IS_DELETE_NOT_DELETE);
            supportDao.save(support);
        }
    }

    @Override
    public void delete(Long id, Long creatorId) {
        Support support = supportDao.findUniqueByObjectIdAndCreatorId(id, creatorId);
        if (null == support) {
            throw new SupportObjectNotExistsException("您要删除的点赞对象不存在");
        } else {
            support.setIsDelete(Constants.IS_DELETE_DELETE);
            supportDao.save(support);
        }

    }

    @Override
    public long count(Long id) {
        return supportDao.count(id);
    }

    abstract protected Integer getType();
}