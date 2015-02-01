package me.ifen.api.ifen.services;

import me.ifen.api.ifen.exception.SupportTypeNotExistsException;
import me.ifen.core.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjingbo on 15/2/1.
 */
@Service
public class ServiceFactory {

    @Resource(name = "articleSupportService")
    SupportService articleSupportService;
    @Resource(name = "commentSupportService")
    SupportService commentSupportService;

    public SupportService getSupportService(Integer type) {
        if (type == Constants.SUPPORT_OBJECT_TYPE_ARTICLE) {
            return articleSupportService;
        } else if (type == Constants.SUPPORT_OBJECT_TYPE_COMMENT) {
            return commentSupportService;
        }
        throw new SupportTypeNotExistsException("点赞对象类型不存在！");
    }

}
