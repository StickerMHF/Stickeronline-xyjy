package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BAlumnusActivityRepository extends JpaRepository<BAlumnusActivityEntity, String> {

}