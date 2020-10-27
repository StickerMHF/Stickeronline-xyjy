package com.tb.service.cdxyh.repository;

import com.tb.service.cdxyh.entity.BMomentsLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BMomentsLikeRepository extends JpaRepository<BMomentsLikeEntity, String> {
List<BMomentsLikeEntity>  findAllByUserIdAndMomentId(@Param("userId") String userId,@Param("momentId") String momentId);

}