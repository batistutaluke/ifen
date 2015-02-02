package me.ifen.api.ifen.services.impl;

import me.ifen.core.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjingbo on 15/2/1.
 */
@Service("articleSupportService")
@Transactional
public class ArticleSupportService extends BaseSupportServiceImpl {
    private static Logger logger = LoggerFactory.getLogger(ArticleSupportService.class);

    @Override
    protected Integer getType() {
        return Constants.SUPPORT_OBJECT_TYPE_ARTICLE;
    }
}