package com.luxfacta.vernyy.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.Camera;



@Repository
public interface CameraRepository extends CrudRepository<Camera,Long> {
   
	@Query(value = "from Camera c join fetch c.area a where a.conId = :conId")
    public Iterable<Camera> findByConIdByOrderByDescricao(@Param("conId") Long conId);
}
