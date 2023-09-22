package com.luxfacta.vernyy.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.ApartamentoFuncionario;



@Repository
public interface ApartamentoFuncionarioRepository extends CrudRepository<ApartamentoFuncionario,Long> {
   
    public Iterable<ApartamentoFuncionario> findByAptIdOrderByNome(Long aptId);
    
    @Query(value = "from ApartamentoFuncionario m join fetch m.apartamento a join fetch a.bloco b where b.conId = :conId  order by m.nome")
    public List<ApartamentoFuncionario> findByConId(@Param("conId") Long conId);

}
