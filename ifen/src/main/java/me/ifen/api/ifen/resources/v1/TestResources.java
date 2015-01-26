package me.ifen.api.ifen.resources.v1;

import me.ifen.api.ifen.resources.v1.model.Test;
import me.ifen.api.ifen.services.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by zhangjingbo on 14-10-22.
 */
@Path("/test")
@Controller
@Scope("prototype")
public class TestResources {

    private static Logger log = LoggerFactory.getLogger(TestResources.class);

    @Autowired
    private TestService testService;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Test get(@PathParam("id") Long id) {
        log.info("[Resource:test][Method:get][param:" + id + "]");
        System.out.println(id);
        me.ifen.api.ifen.dao.orm.Test test = testService.get(id);
        Test t = new Test();
        t.setId(test.getId());
        t.setTest(test.getTest());
        return t;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(Test test) {
        System.out.println(test.getId());
        System.out.println(test.getTest());
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(Test test) {
        System.out.println(test.getId());
        System.out.println(test.getTest());
    }


    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        System.out.println(id);
    }
}
