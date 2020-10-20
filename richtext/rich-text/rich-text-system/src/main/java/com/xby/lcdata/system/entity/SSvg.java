package com.xby.lcdata.system.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ClassName: SSvg
 * @Description:
 * @Author: wangf
 * @Date: 2020/1/19 0019 9:17
 * @Version: 1.0
 **/
@Entity
@Table(name = "s_svg", catalog = "")
public class SSvg {
    private String id;
    private String url;
    private Integer type;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SSvg sSvg = (SSvg) o;
        return Objects.equals(id, sSvg.id) &&
                Objects.equals(url, sSvg.url) &&
                Objects.equals(type, sSvg.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, type);
    }
}
