package com.luxfacta.vernyy.api.repository;

import com.luxfacta.vernyy.api.model.Push;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface PushRepository extends CrudRepository<Push,Long> {
   
    public Page<Push> findAll(Pageable pageable);
}
