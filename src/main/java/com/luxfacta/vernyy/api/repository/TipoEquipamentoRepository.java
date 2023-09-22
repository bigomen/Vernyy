package com.luxfacta.vernyy.api.repository;

import com.luxfacta.vernyy.api.model.TipoEquipamento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface TipoEquipamentoRepository extends CrudRepository<TipoEquipamento,Long> {
   
    public Page<TipoEquipamento> findAll(Pageable pageable);
}
