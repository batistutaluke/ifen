package me.ifen.api.ifen.dao;

import com.google.common.collect.Maps;
import me.ifen.api.ifen.dao.orm.Support;
import me.ifen.core.Constants;
import me.ifen.core.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhangjingbo on 15/2/2.
 */
@Component
public class SupportDao extends HibernateDao<Support, Long> {

    protected String queryHql = "from Support s where s.object_id = :object_id and s.creator_id = :creator_id and s.type = :type";
    protected String countHql = "from Support s where s.object_id = :object_id and s.type = :type and s.isDelete = " + Constants.IS_DELETE_NOT_DELETE;

    public Support findUniqueByObjectIdAndCreatorId(Long objectId, Long creatorId, Integer type) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("object_id", objectId);
        param.put("creator_id", creatorId);
        param.put("type", type);
        return this.findUnique(queryHql, param);
    }

    public long count(Long objectId, Integer type) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("object_id", objectId);
        param.put("type", type);
        return this.countHqlResult(countHql, param);
    }

}
