package com.luxfacta.vernyy.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.AgendamentoArea;



@Repository
public interface AreaAgendamentoRepository extends CrudRepository<AgendamentoArea,Long> {
   
	@Query(value = "from AgendamentoArea where dataInicio > current_date and areId = :areId")
    public List<AgendamentoArea> findByAreId(@Param("areId") Long areId);

	@Query(value = "from AgendamentoArea where dataInicio between current_date-90 and current_date and areId = :areId")
    public List<AgendamentoArea> findByAreIdLast(@Param("areId") Long areId);

	
    public List<AgendamentoArea> findByAptId(Long aptId);
    
    @Query(value = "from AgendamentoArea where areId=:areId and (:inicio between dataInicio and dataFim or :fim between dataInicio and dataFim)")
    public List<AgendamentoArea> findDisponibilidade(@Param("areId") Long areId, @Param("inicio") Date inicio, @Param("fim") Date fim);
    
}
