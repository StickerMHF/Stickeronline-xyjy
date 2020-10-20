package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SChangeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SChangeRecordRepository extends JpaRepository<SChangeRecord,String> {
}
