package com.luxfacta.vernyy.api.repository;

import com.luxfacta.vernyy.api.model.CondominioFuncionario;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CondominioFuncionarioRepository extends CrudRepository<CondominioFuncionario,Long> {
   
    public List<CondominioFuncionario> findByConId(Long conId);

    @Query("select CASE WHEN count(c.id) > 0 THEN true ELSE false END from CondominioFuncionario c join c.areaList a join c.funcionario f where " +
            "a.id = :areaId and f.id = :funcionarioId")
    Boolean validarFuncionarioArea(Long areaId, Long funcionarioId);
}
