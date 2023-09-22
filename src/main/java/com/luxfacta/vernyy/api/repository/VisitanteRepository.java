package com.luxfacta.vernyy.api.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.Visitante;



@Repository
public interface VisitanteRepository extends CrudRepository<Visitante,Long> {
   
    public Page<Visitante> findAll(Pageable pageable);
    
    @Query(value="from Visitante v join fetch v.apartamentoVisitanteList av where av.aptId = :aptId")
    public Iterable<Visitante> findByAptId(@Param("aptId") Long aptId);


    @Query(value="from Visitante v  "
    		+ "where "
    		+ "	exists (select 1 from Condominio c"
    		+ "		join c.blocoList b"
    		+ "		join b.apartamentoList a"
    		+ "		join c.blocoList b1"
    		+ "		join b1.apartamentoList a1"
    		+ "		join a1.apartamentoVisitanteList va where b1.conId = c.id AND a.id=:aptId and va.visId = v.id) and v.cpf=:cpf"
    		+ "")
    public Optional<Visitante> findByCpfAndAptId(@Param("cpf") String cpf, @Param("aptId") Long aptid);

    @Query(value = "SELECT * FROM VW_SYNC_VISITANTE V WHERE V.CON_ID = :conId AND V.SYN_DATA > :data", nativeQuery = true)
    public Iterable<Visitante> findSyncByConId(@Param("conId") Long conId, @Param("data") Date data);
    
}
