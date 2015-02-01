package me.ifen.core.util;

import me.ifen.core.hibernate.Page;

/**
 * Created by zhangjingbo on 15/1/27.
 */
public class ResultUtils {

    public static void copyPage(Page from, Page to) {
        to.setPageNo(from.getPageNo());
        to.setPageSize(from.getPageSize());
        to.setTotalCount(from.getTotalCount());
    }

}
