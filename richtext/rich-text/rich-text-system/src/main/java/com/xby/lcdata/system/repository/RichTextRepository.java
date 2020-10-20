package com.xby.lcdata.system.repository;

import com.xby.lcdata.system.entity.RichTextEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: RichTextRepository
 * @Description:
 * @Author: wangf
 * @Date: 2020/10/19 001916:15
 * @Version: 1.0
 **/
@Repository
public interface RichTextRepository extends JpaRepository<RichTextEntity,String> {
}
