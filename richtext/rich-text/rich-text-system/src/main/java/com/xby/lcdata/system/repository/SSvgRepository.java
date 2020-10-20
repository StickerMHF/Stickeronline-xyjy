package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.SSvg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SSvgRepository extends JpaRepository<SSvg, String> {

    void deleteByIdEquals(String id);

}
