package me.ifen.api.ifen.resources.v1.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by zhangjingbo on 14-10-22.
 */
@XmlRootElement
public class Test implements Serializable {

    private Long id;
    private String test;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
