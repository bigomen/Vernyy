package com.luxfacta.vernyy.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.ApartamentoMorador;



@Repository
public interface ApartamentoMoradorRepository extends CrudRepository<ApartamentoMorador,Long> {
   
	@Query(value = "from ApartamentoMorador am join am.morador m where am.aptId = :aptId order by m.nome")
    public List<ApartamentoMorador> findByAptId(@Param("aptId") Long aptId);


	@Query(value = "from ApartamentoMorador am join am.morador m join am.apartamento a join a.bloco b where b.conId = :conId and m.id=:morId")
    public List<ApartamentoMorador> findByConIdAndMorId(@Param("conId") Long conId, @Param("morId") Long morId);
	
    public long countByAptId(Long aptId);

}
