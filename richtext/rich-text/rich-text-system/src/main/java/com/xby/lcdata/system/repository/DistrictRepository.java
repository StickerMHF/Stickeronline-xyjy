package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SDistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: DistrictRepository
 * @Description:
 * @Author: wangf
 * @Date: 2020/1/10 0010 15:08
 * @Version: 1.0
 **/
@Repository
public interface DistrictRepository extends JpaRepository<SDistrictEntity, String> {

}
