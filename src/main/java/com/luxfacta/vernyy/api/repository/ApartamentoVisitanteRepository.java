package com.luxfacta.vernyy.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.ApartamentoVisitante;



@Repository
public interface ApartamentoVisitanteRepository extends CrudRepository<ApartamentoVisitante,Long> {
   
    public Iterable<ApartamentoVisitante> findByAptIdOrderByNome(Long aptId);
    
    @Query(value = "from ApartamentoVisitante m join fetch m.apartamento a join fetch a.bloco b where b.conId = :conId  order by m.nome")
    public List<ApartamentoVisitante> findByConId(@Param("conId") Long conId);
    
}
