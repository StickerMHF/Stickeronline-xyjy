package com.tb.service.cdxyh.repository;



import com.tb.service.cdxyh.entity.BAlumnusNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BAlumnusNewsRepository extends JpaRepository<BAlumnusNewsEntity, String> {

}