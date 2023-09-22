package com.luxfacta.vernyy.api.repository;

import com.luxfacta.vernyy.api.model.TipoTelefone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface TipoTelefoneRepository extends CrudRepository<TipoTelefone,Long> {
   
    public Page<TipoTelefone> findAll(Pageable pageable);
}
