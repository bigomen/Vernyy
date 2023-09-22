package com.luxfacta.vernyy.api.repository;

import com.luxfacta.vernyy.api.model.Cidade;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CidadeRepository extends CrudRepository<Cidade,Long> {
   
    public List<Cidade> findByUfIdOrderByNome(Long ufId);
}
