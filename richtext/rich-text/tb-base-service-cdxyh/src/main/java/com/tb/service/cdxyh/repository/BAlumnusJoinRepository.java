package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusJoinEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BAlumnusJoinRepository extends JpaRepository<BAlumnusJoinEntity, String>, JpaSpecificationExecutor<BAlumnusJoinEntity> {
    List<BAlumnusJoinEntity> findByAlumnusIdAndUserId(@Param("alumnusId") String alumnusId, @Param("userId") String userId);

    Integer countAllByAlumnusIdAndStatus(@Param("alumnusId") String alumnusId,@Param("status") String status);

    Integer countAllByAlumnusIdAndUserId(@Param("alumnusId") String alumnusId, @Param("userId") String userId);

    Integer countAllByUserId(@Param("userId") String userId);

    List<BAlumnusJoinEntity> findByUserIdAndCheckStateNot(@Param("userId") String userId,@Param("checkState") Integer checkState);
    Integer countByUserIdAndCheckStateNot(@Param("userId") String userId,@Param("checkState") Integer checkState);

}
