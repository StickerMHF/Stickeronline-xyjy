package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BPhotoRepository extends JpaRepository<BPhotoEntity, String> {


}