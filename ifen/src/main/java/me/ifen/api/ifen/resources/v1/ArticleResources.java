package me.ifen.api.ifen.resources.v1;

import com.alibaba.fastjson.JSONObject;
import me.ifen.api.ifen.resources.v1.model.ArticleAO;
import me.ifen.api.ifen.resources.v1.model.ArticlesAO;
import me.ifen.api.ifen.services.ArticleService;
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
@Path("/articles")
@Controller
@Scope("prototype")
public class ArticleResources extends BaseResources {

    private static Logger log = LoggerFactory.getLogger(ArticleResources.class);

    @Autowired
    private ArticleService articleService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        long start = System.currentTimeMillis();
        log.info("[Resource:ArticleResources][Method:list][step:start][param:{}]");

        Page<ArticlesAO> articlesPage = articleService.list(super.getPage(), false);

        log.info("[Resource:ArticleResources][Method:list][step:stop][cost time:{}][result:{}]", System.currentTimeMillis() - start, JSONObject.toJSONString(articlesPage));
        return Response.ok(articlesPage).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticleById(@PathParam("id") Long id) {
        long start = System.currentTimeMillis();
        log.info("[Resource:ArticleResources][Method:getArticleById][step:start][id:{}]", id);

        ArticleAO article = articleService.getArticleById(id);

        log.info("[Resource:ArticleResources][Method:getArticleById][step:stop][cost time:{}][result:{}]", System.currentTimeMillis() - start, JSONObject.toJSONString(article));
        return Response.ok(article).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(ArticleAO article) {
        long start = System.currentTimeMillis();
        log.info("[Resource:ArticleResources][Method:save][step:start][param:{}]", JSONObject.toJSONString(article));

        articleService.save(article);

        log.info("[Resource:ArticleResources][Method:save][step:stop][cost time:{}]", System.currentTimeMillis() - start);
        return Response.ok("文章保存成功！").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modify(ArticleAO article) {
        long start = System.currentTimeMillis();
        log.info("[Resource:ArticleResources][Method:modify][step:start][param:{}]", JSONObject.toJSONString(article));

        articleService.modify(article);

        log.info("[Resource:ArticleResources][Method:modify][step:stop][cost time:{}]", System.currentTimeMillis() - start);
        return Response.ok("文章修改成功！").build();
    }


    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        long start = System.currentTimeMillis();
        log.info("[Resource:ArticleResources][Method:delete][step:start][id:{}]", id);

        articleService.delete(Long.valueOf(id));

        log.info("[Resource:ArticleResources][Method:delete][step:stop][cost time:{}]", System.currentTimeMillis() - start);
        return Response.ok("文章删除成功！").build();
    }

    @PUT
    @Path("recover/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recover(@PathParam("id") String id) {
        long start = System.currentTimeMillis();
        log.info("[Resource:ArticleResources][Method:recover][step:start][id:{}]", id);

        articleService.recover(Long.valueOf(id));

        log.info("[Resource:ArticleResources][Method:recover][step:stop][cost time:{}]", System.currentTimeMillis() - start);
        return Response.ok("文章恢复成功！").build();
    }
}
