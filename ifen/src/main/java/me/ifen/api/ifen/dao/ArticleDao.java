package me.ifen.api.ifen.dao;

import com.google.common.collect.Maps;
import me.ifen.api.ifen.dao.orm.Article;
import me.ifen.core.Constants;
import me.ifen.core.hibernate.HibernateDao;
import me.ifen.core.hibernate.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhangjingbo on 15/1/26.
 */
@Component
public class ArticleDao extends HibernateDao<Article, Long> {

    private static String listHql = "select new Article(id, title, image, summary, author, createTime) from Article";

    public Page<Article> list(Page page, boolean includeDelete) {
        Page<Article> articlePage = null;
        Map<String, Object> param = Maps.newHashMap();
        if (!includeDelete) {
            String hql = listHql + " where isDelete = :isDelete";
            param.put("isDelete", Constants.IS_DELETE_NOT_DELETE);
            articlePage = this.findPage(page, hql, param);
        } else {
            articlePage = this.findPage(page, listHql, param);
        }
        return articlePage;
    }

}