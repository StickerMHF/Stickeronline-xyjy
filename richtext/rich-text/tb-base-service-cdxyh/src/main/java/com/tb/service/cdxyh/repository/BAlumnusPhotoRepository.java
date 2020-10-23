package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BAlumnusPhotoRepository extends JpaRepository<BAlumnusPhotoEntity, String> {

}