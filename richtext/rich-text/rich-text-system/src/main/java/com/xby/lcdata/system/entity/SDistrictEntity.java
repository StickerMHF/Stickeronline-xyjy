package com.xby.lcdata.system.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ClassName: SDistrict
 * @Description:
 * @Author: wangf
 * @Date: 2020/1/10 0010 15:16
 * @Version: 1.0
 **/
@Entity
@Table(name = "s_district", catalog = "")
public class SDistrictEntity {
    private String id;
    private String adCode;
    private String name;
    private String level;
    private String cityCode;
    private String superiorCode;

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
    @Column(name = "ad_code")
    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "level")
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Basic
    @Column(name = "city_code")
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "superior_code")
    public String getSuperiorCode() {
        return superiorCode;
    }

    public void setSuperiorCode(String superiorCode) {
        this.superiorCode = superiorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SDistrictEntity sDistrictEntity = (SDistrictEntity) o;
        return Objects.equals(adCode, sDistrictEntity.adCode) &&
                Objects.equals(name, sDistrictEntity.name) &&
                Objects.equals(level, sDistrictEntity.level) &&
                Objects.equals(cityCode, sDistrictEntity.cityCode) &&
                Objects.equals(superiorCode, sDistrictEntity.superiorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adCode, name, level, cityCode, superiorCode);
    }

}
