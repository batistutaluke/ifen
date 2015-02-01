package me.ifen.api.ifen.services.impl;

import com.google.common.collect.Lists;
import me.ifen.api.ifen.dao.ArticleDao;
import me.ifen.api.ifen.dao.orm.Article;
import me.ifen.api.ifen.exception.ArticleNotFoundException;
import me.ifen.api.ifen.resources.v1.model.ArticleAO;
import me.ifen.api.ifen.resources.v1.model.ArticlesAO;
import me.ifen.api.ifen.services.ArticleService;
import me.ifen.core.hibernate.Page;
import me.ifen.core.util.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zhangjingbo on 15/1/26.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleDao articleDao;


    @Override
    public Page<ArticlesAO> list(Page page, boolean includeDelete) {
        Page<Article> articlePage = articleDao.list(page, includeDelete);
        Page<ArticlesAO> articles = new Page<ArticlesAO>();
        List<ArticlesAO> articlesList = Lists.newArrayList();
        for (me.ifen.api.ifen.dao.orm.Article article : articlePage.getResult()) {
            ArticlesAO a = new ArticlesAO();
            a.setId(article.getId());
            a.setTitle(article.getTitle());
            a.setImage(article.getImage());
            a.setAuthor(article.getAuthor());
            a.setSummary(article.getSummary());
            a.setTime(article.getCreateTime().getTime());
            a.setSupports(0);
            articlesList.add(a);
        }
        articles.setResult(articlesList);
        ResultUtils.copyPage(articlePage, articles);
        return articles;
    }

    @Override
    public ArticleAO getArticleById(Long id) {
        me.ifen.api.ifen.dao.orm.Article article = articleDao.get(id);
        if (null == article) {
            throw new ArticleNotFoundException("文章不存在！");
        }
        return toAO(article);
    }

    @Override
    public void save(ArticleAO article) {
        articleDao.saveOnly(toORM(article));
    }

    @Override
    public void modify(ArticleAO article) {
        if (null == article.getId()) {
            throw new ArticleNotFoundException("要修改的文章不存在！");
        }
        me.ifen.api.ifen.dao.orm.Article origin = articleDao.get(article.getId());
        if (null == origin) {
            throw new ArticleNotFoundException("要修改的文章不存在！");
        }
        articleDao.save(toORM(article));
    }

    @Override
    public void delete(Long id) {
        articleDao.logicDelete(id);
    }

    @Override
    public void recover(Long id) {
        articleDao.recoverLogicDelete(id);
    }

    private ArticleAO toAO(me.ifen.api.ifen.dao.orm.Article article) {
        ArticleAO returnVal = new ArticleAO();
        returnVal.setId(article.getId());
        returnVal.setTitle(article.getTitle());
        returnVal.setImage(article.getImage());
        returnVal.setSummary(article.getSummary());
        returnVal.setContent(article.getContent());
        returnVal.setAuthor(article.getAuthor());
        returnVal.setTime(article.getCreateTime().getTime());
        return returnVal;
    }

    private me.ifen.api.ifen.dao.orm.Article toORM(ArticleAO article) {
        me.ifen.api.ifen.dao.orm.Article articleORM = new me.ifen.api.ifen.dao.orm.Article();
        if (StringUtils.isNotBlank(String.valueOf(article.getId()))) {
            articleORM.setId(article.getId());
        }
        articleORM.setTitle(article.getTitle());
        articleORM.setImage(article.getImage());
        articleORM.setSummary(article.getSummary());
        articleORM.setContent(article.getContent());
        articleORM.setAuthor(article.getAuthor());
        /**
         * todo 获得用户的openid，并通过openid获得用户id，放入creatorId字段
         */
        articleORM.setCreatorId(1L);
        articleORM.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return articleORM;
    }
}