package me.ifen.api.ifen.services.impl;

import me.ifen.api.ifen.dao.TestDao;
import me.ifen.api.ifen.dao.orm.Test;
import me.ifen.api.ifen.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjingbo on 14-10-22.
 */
@Service("testService")
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public Test get(Long id) {
        return testDao.get(id);
    }
}
