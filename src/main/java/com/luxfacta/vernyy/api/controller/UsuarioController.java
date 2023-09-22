package com.luxfacta.vernyy.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.jwt.AccountCredentials;
import com.luxfacta.vernyy.api.message.IBaseMessage;
import com.luxfacta.vernyy.api.message.SuccessMessage;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.model.Usuario;
import com.luxfacta.vernyy.api.repository.CondominioRepository;
import com.luxfacta.vernyy.api.repository.UsuarioRepository;
import com.luxfacta.vernyy.api.rest.RestCondominio;
import com.luxfacta.vernyy.api.rest.RestUsuario;
import com.luxfacta.vernyy.api.shared.EmailUtils;
import com.luxfacta.vernyy.api.shared.SenhaUtils;


@RestController
public class UsuarioController extends AuthenticatedController {

	@Autowired
	private UsuarioRepository repository;

    
	@Autowired
	private EmailUtils sendEmail;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CondominioRepository condominioRepository;


	
	public boolean emailDisponivel(String email) {
        return repository.validarEmail(email);
	}

	@Transactional
	@PostMapping(value = "admin/v1/usuario/novo")
	public ResponseEntity<RestUsuario> novo(@RequestBody RestUsuario value) throws BusinessRuleException,BusinessSecurityException {
        if (!emailDisponivel(value.getEmail().toLowerCase())) {
            throw new BusinessRuleException("Email em uso por outro usuário");
        }
        Usuario usu = (Usuario) mapper.copyToDbObject(value);
        usu.setEmail(usu.getEmail().toLowerCase());
        usu.setDataCriacao(new Date());
		usu.setDataAtualizacao(usu.getDataCriacao());
        usu.setAtivo(Constantes.SIM);

        if(Objects.nonNull(value.getCondominioList())){
			List<Condominio> condominios = new ArrayList<>();
			mapper.setMaxLevel(1);
			for(RestCondominio rc : value.getCondominioList()){
				condominios.add((Condominio) mapper.copyToDbObject(rc));
			}
			usu.setCondominioList(condominios);
		}
 
        //usu.setUsuDataExclusao(null);
        repository.save(usu);
        _resetSenha(usu);
        return new ResponseEntity<>((RestUsuario) mapper.copyToRestObject(usu, RestUsuario.class),  HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = "admin/v1/usuario/atualiza")
	public ResponseEntity<Integer> atualiza(@RequestBody RestUsuario value) throws BusinessRuleException, NotFoundException, BusinessSecurityException {
        Long usuId = Mapper.decryptId(Usuario.class, value.getId());
        if (value.getId() != null) {
            Optional<Usuario> usu = repository.findById(usuId);
            if (usu.isPresent()) {
                mapper.copyToDbObject(value, usu.get());

				for(RestCondominio rc : value.getCondominioList()){
					if (rc == null) continue;
					boolean encontrou = false;
					for(Condominio c: usu.get().getCondominioList()){
						if(Objects.nonNull(rc.getId()) && Mapper.decryptId(Condominio.class,rc.getId()).longValue() == c.getId().longValue()){
							encontrou = true;
						}
					}
					if(!encontrou && rc.getId() != null){
						long id = Mapper.decryptId(Condominio.class, rc.getId());
						Optional<Condominio>  c = condominioRepository.findById(id);
						c.ifPresent(condominio -> usu.get().addCondominio(condominio));
					}
				}

				for(Condominio c : new HashSet<>(usu.get().getCondominioList())){
					boolean encontrou = false;
					for(RestCondominio rc : value.getCondominioList()){
						if (rc == null) continue;
						if(Objects.nonNull(rc.getId()) && Mapper.decryptId(Condominio.class, rc.getId()).longValue() == c.getId().longValue()){
							encontrou = true;
						}
					}
					if(!encontrou){
						usu.get().removeCondominio(c);
					}
				}

                repository.save(usu.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();

	}
	
	
	@Transactional
	@DeleteMapping(value = "admin/v1/usuario/remove/{id}")
	public ResponseEntity<Integer> remove(String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<Usuario> usu = repository.findById(Mapper.decryptId(Usuario.class, id));
            if (usu.isPresent()) {
                repository.delete(usu.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}

	
	@Transactional
	@GetMapping(value = "admin/v1/usuario/recupera/{id}")
	public ResponseEntity<RestUsuario> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<Usuario> usu = repository.findById(Mapper.decryptId(Usuario.class, id));
        if (usu.isPresent()) {
			mapper.setMaxLevel(2);
            RestUsuario rUsu = (RestUsuario) mapper.copyToRestObject(usu.get(), RestUsuario.class);

			//Provisorio... ate corrigir o mapper ao converter Listas
			if(Objects.nonNull(usu.get().getCondominioList())){
				rUsu.setCondominioList(mapper.copyToRestObject(usu.get().getCondominioList(), RestCondominio.class));
			}

            return new ResponseEntity<>(rUsu, HttpStatus.OK);
            
        }
		throw new NotFoundException();
	}


	@Transactional
	@GetMapping(value = "admin/v1/usuario/lista")
	public ResponseEntity<Iterable<RestUsuario>> lista() throws BusinessSecurityException {
		
        Iterable<Usuario> itUsu = repository.findAll();
        mapper.setMaxLevel(2);
        List<RestUsuario> itRestUsu =  (List<RestUsuario>) mapper.copyToRestObject(itUsu, RestUsuario.class);
        return new ResponseEntity<>(itRestUsu, HttpStatus.OK);
	}

	
	@Transactional
	@PostMapping(value = "/updatePassword")
	public ResponseEntity<IBaseMessage> alterarSenha(@RequestBody AccountCredentials value)  throws BusinessRuleException {
        Optional<Usuario> optUsu = repository.findById(super.getIdUsuario());
        if (optUsu.isPresent() && value.getPassword() != null
                && passwordEncoder.matches(value.getPassword(), optUsu.get().getSenha())) {

            Usuario usu = optUsu.get();


            if (!SenhaUtils.validaComplexidade(value.getNewPassword())) {
                throw new BusinessRuleException("Senha deve ter letras maiúsculas, minúsculas e números");
            }

            usu.setToken(null);
            usu.setTokenExpiracao(null);


            String senhaCrypto = passwordEncoder.encode(value.getNewPassword());
            usu.setSenha(senhaCrypto);
            repository.save(usu);

            return new ResponseEntity<>(new SuccessMessage("Senha atualizada com sucesso", null), HttpStatus.OK);
        }
        throw new BusinessRuleException("Senha inválida ou não informada");
	}

	private void _resetSenha(Usuario usu) throws BusinessRuleException {
			
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);
        usu.setToken(UUID.randomUUID().toString());
        usu.setTokenExpiracao(cal.getTime());
        repository.save(usu);

		try {
			EmailUtils.EmailMessage mensagem = sendEmail.novaMensagem();

			mensagem.setSubject(config.getProperty("email.resetsenha.assunto"));

			String corpo = config.getProperty("email.resetsenha.corpo");
            if (corpo != null) {
                corpo = corpo.replace("#usuario#", usu.getNome());
                corpo = corpo.replace("#token#", usu.getToken());
            }
			mensagem.setBody(corpo);

			mensagem.setFrom(config.getProperty("email.resetsenha.emailOrigem"));
			mensagem.setFromName(config.getProperty("email.resetsenha.nomeOrigem"));

			mensagem.setToName(usu.getNome());
			mensagem.setTo(usu.getEmail());

			sendEmail.sendMessage(mensagem);
		} catch (Exception e) {
			throw new BusinessRuleException("Erro ao enviar email");
		}

	}
	
}
