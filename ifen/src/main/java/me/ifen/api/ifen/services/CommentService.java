package me.ifen.api.ifen.services;

import me.ifen.api.ifen.resources.v1.model.CommentAO;
import me.ifen.core.hibernate.Page;

/**
 * Created by zhangjingbo on 15/2/1.
 */
public interface CommentService {


    public Page<CommentAO> list(Page page, Long articleId, boolean includeDelete);

    public void save(CommentAO commentAO);

    public void delete(Long id);

}
