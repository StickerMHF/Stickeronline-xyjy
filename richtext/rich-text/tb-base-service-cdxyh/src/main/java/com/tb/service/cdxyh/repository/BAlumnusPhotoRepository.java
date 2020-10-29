package com.tb.service.cdxyh.repository;


import com.tb.service.cdxyh.entity.BAlumnusPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BAlumnusPhotoRepository extends JpaRepository<BAlumnusPhotoEntity, String> {

    @Query(value = "SELECT b.* FROM b_alumnus_join a, b_alumnus_photo b where a.alumnus_id= ?1 and a.user_id = b.user_id  order by a.create_time", nativeQuery = true)
    List<Map<String, Object>> queryAllBySql(String id);

}