package com.luxfacta.vernyy.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.Apartamento;



@Repository
public interface ApartamentoRepository extends CrudRepository<Apartamento,Long> {

    Page<Apartamento> findAll(Pageable pageable);

    @Query(value="from Apartamento a join fetch bloco b where b.conId=:conId")
    public Iterable<Apartamento> findByConId(@Param("conId") Long conId);
    
    @Query(value="from Apartamento a join fetch a.apartamentoMoradorList am join fetch am.morador m where m.id = :resId")
    public Iterable<Apartamento> findByResId(@Param("resId") Long resId);
    
    public Optional<Apartamento> findById(@Param("id") Long id);
    
}
