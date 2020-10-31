package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BAlumnusJoinRepository extends JpaRepository<BAlumnusJoinEntity, String> {


    List<BAlumnusJoinEntity> findByAlumnusIdAndUserId(@Param("alumnusId") String alumnusId, @Param("userId") String userId);

    Integer countAllByAlumnusId(@Param("alumnusId") String alumnusId);

    Integer countAllByAlumnusIdAndUserId(@Param("alumnusId") String alumnusId, @Param("userId") String userId);

    @Query(value = "delete from b_alumnus_join where alumnus_id=?1 and user_id=?2", nativeQuery = true)
    void deleteByAlumnusIdAndUserId(String alumnusId, String userId);
}