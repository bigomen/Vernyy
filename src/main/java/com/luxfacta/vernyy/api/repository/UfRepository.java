package com.luxfacta.vernyy.api.repository;

import com.luxfacta.vernyy.api.model.Uf;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface UfRepository extends CrudRepository<Uf,Long> {
   
    public Page<Uf> findAll(Pageable pageable);
    public Iterable<Uf> findAllByOrderBySigla();
}
