package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BAlumnusRepository extends JpaRepository<BAlumnusEntity, String> {

}