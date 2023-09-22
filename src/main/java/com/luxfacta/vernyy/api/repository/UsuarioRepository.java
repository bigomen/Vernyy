package com.luxfacta.vernyy.api.repository;

import com.luxfacta.vernyy.api.model.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
   
    public Page<Usuario> findAll(Pageable pageable);
    
    Optional<Usuario> findByEmail(String email);

    @Modifying
    @Query("update Usuario u set u.dataUltimoAcesso = :data, u.pushToken = :token where u.id = :id")
    void updateLastAccess(Long id, Date data, String token);

    @Query("select CASE WHEN COUNT(u.id) > 0 THEN FALSE ELSE TRUE END from Usuario u where u.email = :email")
    Boolean validarEmail(String email);

    @Query("select new Usuario(u.id, u.nome, u.dataNascimento, u.cpf, u.email, u.telefoneCelular, u.dataContratacao, u.ativo, p.id, p.descricao) from Usuario u join u.perfil p")
    List<Usuario> listaUsuarios();
    
    Optional<Usuario> findByToken(String token);

}
