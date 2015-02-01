package me.ifen.api.ifen.services;

import me.ifen.api.ifen.resources.v1.model.ArticleAO;
import me.ifen.api.ifen.resources.v1.model.ArticlesAO;
import me.ifen.core.hibernate.Page;

/**
 * Created by zhangjingbo on 15/1/26.
 */
public interface ArticleService {

    public Page<ArticlesAO> list(Page page, boolean includeDelete);

    public ArticleAO getArticleById(Long id);

    public void save(ArticleAO article);

    public void modify(ArticleAO article);

    public void delete(Long id);

    public void recover(Long id);
}
