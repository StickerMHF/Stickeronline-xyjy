package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BSigninEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BSigninRepository extends JpaRepository<BSigninEntity, String> {

    Integer countByuserId(@Param("userId") String userId);

    List<BSigninEntity> queryAllByUserId(@Param("userId") String userId);


}