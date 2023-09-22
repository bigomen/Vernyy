package com.luxfacta.vernyy.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.SyncDeletado;

@Repository
public interface SyncDeletadoRepository extends CrudRepository<SyncDeletado, Long> {
	
	@Query(value = "SELECT * FROM VW_SYNC_DELETADO V WHERE V.DATA > :data ", nativeQuery = true)
	public List<SyncDeletado> findByData(@Param("data") Date data);
}
