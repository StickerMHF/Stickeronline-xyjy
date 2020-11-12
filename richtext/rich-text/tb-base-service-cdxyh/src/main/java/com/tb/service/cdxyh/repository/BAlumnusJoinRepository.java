package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BAlumnusJoinRepository extends JpaRepository<BAlumnusJoinEntity, String> {


    List<BAlumnusJoinEntity> findByAlumnusIdAndUserId(@Param("alumnusId") String alumnusId, @Param("userId") String userId);

    Integer countAllByAlumnusIdAndStatus(@Param("alumnusId") String alumnusId,@Param("status") String status);

    Integer countAllByAlumnusIdAndUserId(@Param("alumnusId") String alumnusId, @Param("userId") String userId);

}
