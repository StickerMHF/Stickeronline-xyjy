package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BBarrageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BBarrageRepository extends JpaRepository<BBarrageEntity, String> {


}