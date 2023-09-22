package com.luxfacta.vernyy.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.Condominio;


@Repository
public interface CondominioRepository extends CrudRepository<Condominio,Long> {
   
    @Query(value="from Condominio c where c.ativo=1")
    public Iterable<Condominio> findAll();

    
    @Query(value="from Condominio c join  c.usuarioList u  where u.id = :idUsuario and c.ativo=1")
    Iterable<Condominio> findByUsuario(@Param("idUsuario") Long idUsuario);
}
