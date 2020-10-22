package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlummunsMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BAlumnusMemberRepository extends JpaRepository<BAlummunsMemberEntity, String> {

}