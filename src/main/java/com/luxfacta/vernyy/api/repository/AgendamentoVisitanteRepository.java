package com.luxfacta.vernyy.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.AgendamentoVisitante;



@Repository
public interface AgendamentoVisitanteRepository extends CrudRepository<AgendamentoVisitante,Long> {
   
	@Query(value = "from AgendamentoVisitante where dataInicio > current_date and vapId=:vapId")
    public List<AgendamentoVisitante> findByVapId(@Param("vapId") Long vapId);
	
	@Query(value = "from AgendamentoVisitante where dataInicio between current_date-90 and current_date and vapId=:vapId")
    public List<AgendamentoVisitante> findByVapIdLast(@Param("vapId") Long vapId);
	
}
