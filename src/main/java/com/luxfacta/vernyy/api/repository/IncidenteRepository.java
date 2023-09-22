package com.luxfacta.vernyy.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.Incidente;

@Repository
public interface IncidenteRepository extends CrudRepository<Incidente,Long> {

	Iterable<Incidente> findByConIdAndInsIdOrderByDataAberturaDesc(Long conId, Long insId);
	
	
}
