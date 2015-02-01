package me.ifen.api.ifen.services.impl;

import me.ifen.api.ifen.services.SupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjingbo on 15/2/1.
 */
@Service("commentSupportService")
@Transactional
public class CommentSupportService implements SupportService {
    private static Logger logger = LoggerFactory.getLogger(CommentSupportService.class);

    @Override
    public void support(Long id, Long creatorId) {
    }

    @Override
    public void delete(Long id, Long creatorId) {
    }

    @Override
    public Integer count(Long id) {
        return 0;
    }
}