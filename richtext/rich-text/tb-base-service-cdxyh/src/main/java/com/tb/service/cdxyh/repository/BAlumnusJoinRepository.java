package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BAlumnusJoinRepository extends JpaRepository<BAlumnusJoinEntity, String> {

}