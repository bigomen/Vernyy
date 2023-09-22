package com.luxfacta.vernyy.api.repository;

import com.luxfacta.vernyy.api.model.Perfil;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface PerfilRepository extends CrudRepository<Perfil,Long> {
   
    public Page<Perfil> findAll(Pageable pageable);
}
