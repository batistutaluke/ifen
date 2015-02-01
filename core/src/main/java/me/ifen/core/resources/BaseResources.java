package me.ifen.core.resources;

import me.ifen.core.exception.PageParamLackException;
import me.ifen.core.hibernate.Page;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

/**
 * Created by zhangjingbo on 15/1/27.
 */
public class BaseResources {

    @Context
    protected HttpServletRequest request;
    @Context
    protected HttpServletResponse response;

    /**
     * 从请求参数里获取分页对象
     */
    public Page getPage() {
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isBlank(pageNo) || StringUtils.isBlank(pageSize)) {
            throw new PageParamLackException("pageNo 和 pageSize 都不能为空！");
        }
        String orderBy = request.getParameter("orderBy") == null ? "id" : request.getParameter("orderBy");
        String order = request.getParameter("order") == null ? Page.DESC : request.getParameter("order");

        Page page = new Page();
        page.setPageNo(Integer.valueOf(pageNo));
        page.setPageSize(Integer.valueOf(pageSize));
        page.setOrderBy(orderBy);
        page.setOrder(order);
        return page;
    }
}
