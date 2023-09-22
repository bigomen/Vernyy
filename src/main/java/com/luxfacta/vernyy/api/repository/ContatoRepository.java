package com.luxfacta.vernyy.api.repository;

import com.luxfacta.vernyy.api.model.Contato;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface ContatoRepository extends CrudRepository<Contato,Long> {
   
    public Page<Contato> findAll(Pageable pageable);
}
