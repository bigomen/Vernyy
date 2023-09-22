package com.luxfacta.vernyy.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.Veiculo;



@Repository
public interface VeiculoRepository extends CrudRepository<Veiculo,Long> {
   
    public List<Veiculo> findByAptIdOrderByModelo(Long aptId);
    
    @Query(value = "select * from VW_SYNC_VEICULO v where v.CON_ID = :conId and v.SYN_DATA > :data", nativeQuery = true)
    public Iterable<Veiculo> findSyncByConId(@Param("conId") Long conId, @Param("data") Date data);
}
