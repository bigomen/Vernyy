package com.luxfacta.vernyy.api.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.Area;

@Repository
public interface AreaRepository extends CrudRepository<Area,Long> {
   
    public List<Area> findByConIdOrderByDescricao(Long conId);

    @Query(value="from Area a where exists (select 1 from Condominio c join c.blocoList b join b.apartamentoList ap where ap.id=:aptId and c.id = a.conId) order by a.descricao")
    public List<Area> findByAptId(@Param("aptId") Long aptId);

    public Optional<Area> findByMacExternoOrMacInterno(@Param("macExterno") String macExterno, @Param("macExterno") String macInterno);

    @Query(value="from Area a where id=:areId and exists (select 1 from Condominio c join c.blocoList b join b.apartamentoList ap where ap.id=:aptId and c.id = a.conId) order by a.descricao")
    public Optional<Area> findByIdAndAptId(@Param("areId") Long areId, @Param("aptId") Long aptId);
    
    @Query(value = "select * from VW_SYNC_AREA V where V.CON_ID = :conId and V.SYN_DATA > :data ", nativeQuery = true)
    public Iterable<Area> findSyncByConId(@Param("conId") Long conId, @Param("data") Date data);
}
