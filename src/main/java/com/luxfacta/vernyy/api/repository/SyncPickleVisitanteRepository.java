package com.luxfacta.vernyy.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.SyncPickleVisitante;

@Repository
public interface SyncPickleVisitanteRepository extends CrudRepository<SyncPickleVisitante, Long>{
	
	@Query(value = "SELECT * FROM VW_SYNC_PICKLE_VISITANTE V WHERE V.CON_ID = :conId and V.SYN_DATA > :data ", nativeQuery = true)
	public List<SyncPickleVisitante> findByConId(@Param("conId") Long conId, @Param("data") Date data);

}
