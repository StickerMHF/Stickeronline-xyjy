package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BAlumnusActivityRepository extends JpaRepository<BAlumnusActivityEntity, String> {
        Integer countAllByFid(@Param("fid") String fid);
}