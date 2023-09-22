package com.luxfacta.vernyy.api.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.Funcionario;



@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario,Long> {
	public Optional<Funcionario> findByCpf(String cpf);
   
    public Page<Funcionario> findAll(Pageable pageable);
    
    @Query(value="from Funcionario v join fetch v.apartamentoFuncionarioList av where av.aptId = :aptId")
    public Iterable<Funcionario> findByAptId(@Param("aptId") Long aptId);


    @Query(value="from Funcionario v join fetch v.apartamentoFuncionarioList av where v.senha = :senha and av.telefoneCelular = :telefone")
    public Optional<Funcionario> findByTelefoneCelularAndSenha(@Param("telefone") String telefone, @Param("senha") String senha);

    
    @Query(value="from Funcionario v  "
    		+ "where "
    		+ "	exists (select 1 from Condominio c"
    		+ "		join c.blocoList b"
    		+ "		join b.apartamentoList a"
    		+ "		join c.blocoList b1"
    		+ "		join b1.apartamentoList a1"
    		+ "		join a1.apartamentoFuncionarioList va where b1.conId = c.id AND a.id=:aptId and va.empId = v.id) and v.cpf=:cpf"
    		+ "")
    public Optional<Funcionario> findByCpfAndAptId(@Param("cpf") String cpf, @Param("aptId") Long aptid);
    
    @Query(value = "select * from VW_SYNC_FUNCIONARIO V where V.CON_ID = :conId and V.SYN_DATA > :data ", nativeQuery = true)
    public Iterable<Funcionario> findSyncByConId(@Param("conId") Long conId, @Param("data") Date data);
}
