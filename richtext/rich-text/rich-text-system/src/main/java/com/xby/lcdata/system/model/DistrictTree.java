package com.xby.lcdata.system.model;

import com.xby.lcdata.system.entity.SDistrictEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DistrictTree
 * @Description:
 * @Author: wangf
 * @Date: 2020/1/10 0010 15:52
 * @Version: 1.0
 **/
public class DistrictTree {

    private String id;
    private String adCode;
    private String name;
    private String level;
    private String cityCode;
    private String superiorCode;
    private List<DistrictTree> districts;

    public DistrictTree() {
    }

    public DistrictTree(SDistrictEntity district) {
        this.id = district.getId();
        this.adCode = district.getAdCode();
        this.name = district.getName();
        this.level = district.getLevel();
        this.cityCode = district.getCityCode();
        this.superiorCode = district.getSuperiorCode();
        this.districts = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSuperiorCode() {
        return superiorCode;
    }

    public void setSuperiorCode(String superiorCode) {
        this.superiorCode = superiorCode;
    }

    public List<DistrictTree> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictTree> districts) {
        this.districts = districts;
    }
}
