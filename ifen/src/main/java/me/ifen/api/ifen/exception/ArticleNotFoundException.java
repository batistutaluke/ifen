package me.ifen.api.ifen.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by zhangjingbo on 15/1/27.
 */
public class ArticleNotFoundException extends WebApplicationException {
    public ArticleNotFoundException(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(message).type(MediaType.APPLICATION_JSON).build());
    }
}
