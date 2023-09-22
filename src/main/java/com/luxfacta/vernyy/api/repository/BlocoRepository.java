package com.luxfacta.vernyy.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.Bloco;



@Repository
public interface BlocoRepository extends CrudRepository<Bloco,Long> {
   
    public Iterable<Bloco> findByConIdOrderByDescricao(Long conId);


}
