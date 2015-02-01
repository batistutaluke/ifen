package me.ifen.api.ifen.services.impl;

import com.google.common.collect.Lists;
import me.ifen.api.ifen.dao.CommentDao;
import me.ifen.api.ifen.dao.orm.Comment;
import me.ifen.api.ifen.resources.v1.model.CommentAO;
import me.ifen.api.ifen.services.CommentService;
import me.ifen.core.hibernate.Page;
import me.ifen.core.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zhangjingbo on 15/2/1.
 */
@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
    private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Override
    public Page<CommentAO> list(Page page, Long articleId, boolean includeDelete) {
        Page<Comment> commentPage = commentDao.list(page, articleId, includeDelete);
        Page<CommentAO> comments = new Page();
        List<CommentAO> commentsList = Lists.newArrayList();
        for (Comment comment : commentPage.getResult()) {
            CommentAO c = new CommentAO();
            c.setId(comment.getId());
            c.setArticleId(comment.getArticleId());
            c.setContent(comment.getContent());
            c.setCreatorId(comment.getCreatorId());
            c.setNickname(comment.getCreatorNickname());
            c.setTime(comment.getCreateTime().getTime());
            c.setSupports(0);
            commentsList.add(c);
        }
        comments.setResult(commentsList);
        ResultUtils.copyPage(commentPage, comments);
        return comments;
    }

    @Override
    public void save(CommentAO commentAO) {
        commentDao.saveOnly(toORM(commentAO));
    }

    @Override
    public void delete(Long id) {
        commentDao.logicDelete(id);
    }

    private Comment toORM(CommentAO commentAO) {
        Comment comment = new Comment();
        comment.setContent(commentAO.getContent());
        /**
         * todo 获得用户的openid，并通过openid获得用户id，放入creatorId字段
         */
        comment.setCreatorId(1L);
        comment.setArticleId(commentAO.getArticleId());
        comment.setCreatorNickname(commentAO.getNickname());
        comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return comment;
    }
}