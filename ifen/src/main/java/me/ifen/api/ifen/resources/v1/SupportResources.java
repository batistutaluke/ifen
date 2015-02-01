package me.ifen.api.ifen.resources.v1;

import com.alibaba.fastjson.JSONObject;
import me.ifen.api.ifen.resources.v1.model.SupportAO;
import me.ifen.api.ifen.services.ServiceFactory;
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
@Path("/supports")
@Controller
@Scope("prototype")
public class SupportResources extends BaseResources {

    private static Logger log = LoggerFactory.getLogger(SupportResources.class);

    @Autowired
    private ServiceFactory serviceFactory;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(SupportAO supportAO) {
        long start = System.currentTimeMillis();
        log.info("[Resource:SupportResources][Method:save][step:start][param:{}]", JSONObject.toJSONString(supportAO));

        serviceFactory.getSupportService(supportAO.getType()).support(supportAO.getId(), supportAO.getCreatorId());

        log.info("[Resource:SupportResources][Method:save][step:stop][cost time:{}]", System.currentTimeMillis() - start);
        return Response.ok("点赞成功！").build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(SupportAO supportAO) {
        long start = System.currentTimeMillis();
        log.info("[Resource:SupportResources][Method:delete][step:start][param:{}]", JSONObject.toJSONString(supportAO));

        serviceFactory.getSupportService(supportAO.getType()).delete(supportAO.getId(), supportAO.getCreatorId());

        log.info("[Resource:SupportResources][Method:delete][step:stop][cost time:{}]", System.currentTimeMillis() - start);
        return Response.ok("取消点赞成功！").build();
    }

}
