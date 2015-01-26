package me.ifen.api.ifen.dao;

import me.ifen.api.ifen.dao.orm.Test;
import me.ifen.core.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjingbo on 14-10-22.
 */
@Component("testDao")
public class TestDao extends HibernateDao<Test, Long> {

}
