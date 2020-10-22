package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BOrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BOrganizationRepository extends JpaRepository<BOrganizationEntity, String> {
//    @Query(value = "",nativeQuery = true)
//void sss();
}