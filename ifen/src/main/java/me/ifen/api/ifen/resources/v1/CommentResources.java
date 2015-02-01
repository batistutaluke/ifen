package me.ifen.api.ifen.resources.v1;

import com.alibaba.fastjson.JSONObject;
import me.ifen.api.ifen.resources.v1.model.CommentAO;
import me.ifen.api.ifen.services.CommentService;
import me.ifen.core.hibernate.Page;
import me.ifen.core.resources.BaseResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by zhangjingbo on 14-10-22.
 */
@Path("/comments")
@Controller
@Scope("prototype")
public class CommentResources extends BaseResources {

    private static Logger log = LoggerFactory.getLogger(CommentResources.class);

    @Autowired
    private CommentService commentService;

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@PathParam("id") Long articleId) {
        long start = System.currentTimeMillis();
        log.info("[Resource:CommentResources][Method:list][step:start][param:{}]");

        Page<CommentAO> commentsPage = commentService.list(super.getPage(), articleId, false);

        log.info("[Resource:CommentResources][Method:list][step:stop][cost time:{}][result:{}]", System.currentTimeMillis() - start, JSONObject.toJSONString(commentsPage));
        return Response.ok(commentsPage).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(CommentAO commentAO) {
        long start = System.currentTimeMillis();
        log.info("[Resource:CommentResources][Method:save][step:start][param:{}]", JSONObject.toJSONString(commentAO));

        commentService.save(commentAO);

        log.info("[Resource:CommentResources][Method:save][step:stop][cost time:{}]", System.currentTimeMillis() - start);
        return Response.ok("提交评论成功！").build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        long start = System.currentTimeMillis();
        log.info("[Resource:CommentResources][Method:delete][step:start][id:{}]", id);

        commentService.delete(id);

        log.info("[Resource:CommentResources][Method:delete][step:stop][cost time:{}]", System.currentTimeMillis() - start);
        return Response.ok("删除评论成功！").build();
    }

}
