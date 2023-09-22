package com.luxfacta.vernyy.api.repository;

import com.luxfacta.vernyy.api.model.TipoArea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface TipoAreaRepository extends CrudRepository<TipoArea,Long> {
   
    public Page<TipoArea> findAll(Pageable pageable);
}
