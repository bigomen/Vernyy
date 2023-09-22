package com.luxfacta.vernyy.api.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luxfacta.vernyy.api.model.Morador;



@Repository
public interface MoradorRepository extends CrudRepository<Morador,Long> {
   
    public Page<Morador> findAll(Pageable pageable);

    public Optional<Morador> findByTelefoneCelularAndSenha(String telefone, String senha);

    public Optional<Morador> findByTelefoneCelular(String telefone);

    @Query(value = "from Morador m join fetch m.apartamentoMoradorList am join fetch am.apartamento a join fetch a.bloco b where b.conId = :conId  order by m.nome")
    public List<Morador> findByConId(@Param("conId") Long conId);
    
    @Query(value = "from Morador m join fetch m.apartamentoMoradorList am join fetch am.apartamento a join fetch a.bloco b join fetch b.condominio c where c.cep=:cep and upper(a.numero) = upper(:numero) and m.cpf=:cpf")
    public Optional<Morador> findPrimeiroAcesso(@Param("cep") String cep, @Param("numero") String numero, @Param("cpf") String cpf);

    @Query(value = "from Morador m join fetch m.apartamentoMoradorList am where am.aptId=:aptId order by m.nome")
    public Iterable<Morador> findByAptId(@Param("aptId") Long aptId);

    public Optional<Morador> findByCpf(String cpf);
    
    @Query(value = "select * from VW_SYNC_MORADOR V where V.CON_ID = :conId and V.SYN_DATA > :data ", nativeQuery = true)
    public Iterable<Morador> findSyncByConId(@Param("conId") Long conId, @Param("data") Date data);

    
}
