package me.ifen.api.ifen.dao;

import com.google.common.collect.Maps;
import me.ifen.api.ifen.dao.orm.Comment;
import me.ifen.core.Constants;
import me.ifen.core.hibernate.HibernateDao;
import me.ifen.core.hibernate.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhangjingbo on 15/2/1.
 */
@Component
public class CommentDao extends HibernateDao<Comment, Long> {

    private static String listHql = "select new Comment(id, content, articleId, creatorId, creatorNickname, createTime) from Comment where articleId = :articleId";

    public Page<Comment> list(Page page, Long articleId, boolean includeDelete) {
        Page<Comment> commentPage = null;
        Map<String, Object> param = Maps.newHashMap();
        param.put("articleId", articleId);
        if (!includeDelete) {
            String hql = listHql + " and isDelete = :isDelete";
            param.put("isDelete", Constants.IS_DELETE_NOT_DELETE);
            commentPage = this.findPage(page, hql, param);
        } else {
            commentPage = this.findPage(page, listHql, param);
        }
        return commentPage;
    }
}