package me.ifen.api.ifen.resources.v1.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhangjingbo on 15/1/26.
 */
@XmlRootElement
public class ArticleAO extends ArticlesAO {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
