package com.luxfacta.vernyy.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.TipoIncidente;

@Repository
public interface TipoIncidenteRepository extends CrudRepository<TipoIncidente,Long> {

	
	
}
