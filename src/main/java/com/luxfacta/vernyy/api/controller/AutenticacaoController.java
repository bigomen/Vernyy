package com.luxfacta.vernyy.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.jwt.AccountCredentials;
import com.luxfacta.vernyy.api.jwt.TokenAuthenticationService;
import com.luxfacta.vernyy.api.message.ErrorMessage;
import com.luxfacta.vernyy.api.message.IBaseMessage;
import com.luxfacta.vernyy.api.message.SuccessMessage;
import com.luxfacta.vernyy.api.model.Usuario;
import com.luxfacta.vernyy.api.repository.UsuarioRepository;
import com.luxfacta.vernyy.api.rest.RestLogin;
import com.luxfacta.vernyy.api.shared.EmailUtils;
import com.luxfacta.vernyy.api.shared.SenhaUtils;


@RestController
public class AutenticacaoController {

    @Autowired
    private Environment env;
    
	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private EmailUtils sendEmail;

	@Autowired
	private PasswordEncoder passwordEncoder;

	protected final Mapper mapper = new Mapper();


	// REFRESH TOKEN
	@PostMapping(value = "/refresh")
	public ResponseEntity<Object> refresh() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional
	@GetMapping(value = "auth/v1/usuario/checaEmail/{email}")
	public ResponseEntity<IBaseMessage> checaEmail(@PathVariable String email) {
		
        if (emailDisponivel(email)) {
            return new ResponseEntity<>(new SuccessMessage("OK",null), HttpStatus.OK);
        }
		return new ResponseEntity<>(new ErrorMessage("Email já em uso por outro usuário"), HttpStatus.BAD_GATEWAY);
		
	}
	
	private boolean emailDisponivel(String email) {
        Optional<Usuario> usuValida = repository.findByEmail(email);
        return usuValida.isPresent();
	}


	@Transactional
	@PostMapping(value = "/forgot")
	public ResponseEntity<IBaseMessage> forgot(@RequestBody AccountCredentials value) {
		Optional<Usuario> usuIt = repository.findByEmail(value.getUsername());
        if (usuIt.isPresent()) {
            Usuario usu = usuIt.get();
			if (usu.getAtivo()== Constantes.SIM) {
				_resetSenha(usu);
			}
		}
		return new ResponseEntity<>(new SuccessMessage("Email de reset de senha enviado com sucesso",null), HttpStatus.OK);
	}


    
	@Transactional
	@PostMapping(value = "/reset/{uuid}")
	public ResponseEntity<IBaseMessage> resetSenha(@PathVariable String uuid, @RequestBody AccountCredentials value) throws BusinessRuleException {
        Optional<Usuario> optUsu = repository.findByToken(uuid);
        if (optUsu.isPresent() && value.getPassword() != null) {

            Usuario usu = optUsu.get();

            if (usu.getTokenExpiracao().getTime() < Calendar.getInstance().getTimeInMillis()) {
                usu.setToken(null);
                usu.setTokenExpiracao(null);
                repository.save(usu);
                throw new BusinessRuleException("Token expirado, solicite novamente a senha");
            }
            

            if (!SenhaUtils.validaComplexidade(value.getPassword())) {
                throw new BusinessRuleException("Senha deve ter letras maiúsculas, minúsculas e números");
            }

            usu.setToken(null);
            usu.setTokenExpiracao(null);
            String senhaCrypto = passwordEncoder.encode(value.getPassword());
            usu.setSenha(senhaCrypto);
            repository.save(usu);

            try {
                EmailUtils.EmailMessage mensagem = sendEmail.novaMensagem();
                mensagem.setSubject(env.getProperty("email.senhaAtualizada.assunto"));
                String corpo = env.getProperty("email.senhaAtualizada.corpo");
                if (corpo != null)
                    corpo = corpo.replace("#usuario#", usu.getNome());

                mensagem.setBody(corpo);
                mensagem.setFrom(env.getProperty("email.senhaAtualizada.emailOrigem"));
                mensagem.setFromName(env.getProperty("email.senhaAtualizada.nomeOrigem"));
                mensagem.setToName(usu.getNome());
                mensagem.setTo(usu.getEmail());
                sendEmail.sendMessage(mensagem);
            } catch (Exception e) {
            }

            return new ResponseEntity<>(new SuccessMessage("Senha atualizada com sucesso", null), HttpStatus.OK);
        }
        throw new BusinessRuleException("Token inválido ou senha não informada");
	}
    

    @Transactional(propagation = Propagation.REQUIRED)
	@PostMapping(value = "/login")
	public ResponseEntity<RestLogin> loginSenha(@RequestBody AccountCredentials value)  throws BusinessRuleException, BusinessSecurityException {
        Optional<Usuario> usuario = repository.findByEmail(value.getUsername());

        if (usuario.isPresent()) {
			Usuario usu = usuario.get();
            if (usu.getSenha() != null && usu.getAtivo() == Constantes.SIM && passwordEncoder.matches(value.getPassword(), usu.getSenha())) {
                
                List<String> rolesGrupo = new ArrayList<>();
                rolesGrupo.add(usu.getPerfil().getRole());

				Long[] condominios = new Long[0];;
				if(!usu.getCondominioList().isEmpty()){
					condominios = new Long[usu.getCondominioList().size()];
                    for (int i = 0; i < usu.getCondominioList().size(); i++) {
                        condominios[i] = usu.getCondominioList().get(i).getId();
                    }
				}
				
				Long[] apartamentos = new Long[0];
				
				TokenAuthenticationService tks = new TokenAuthenticationService(env);
				
                String tokenAuth = tks.generateToken(usu.getEmail(), rolesGrupo, condominios, apartamentos, usu.getNome(), usu.getId(), Long.parseLong(env.getProperty("security.expiration")), env.getProperty("security.secret"));
                String tokenRefresh = tks.generateToken(usu.getEmail(), rolesGrupo, condominios, apartamentos, usu.getNome(), usu.getId(), Long.parseLong(env.getProperty("security.refresh.expiration")), env.getProperty("security.secret"));
                	
                tks.updateContext(usu.getEmail(), rolesGrupo, condominios, apartamentos, usu.getId());
                
                HttpHeaders header = new HttpHeaders();
                header.add(TokenAuthenticationService.HEADER_AUTHORIZATION, tokenAuth);
                header.add(TokenAuthenticationService.HEADER_REFRESH, tokenRefresh);

				repository.updateLastAccess(usu.getId(), new Date(), value.getPush());
				
                return new ResponseEntity<>((RestLogin)mapper.copyToRestObject(usu, RestLogin.class), header, HttpStatus.OK);
        	}
            
        }
        throw new BusinessRuleException("Usuário/senha inválida");
    }
    
    
	
	private void _resetSenha(Usuario usu) {
			
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);
        usu.setToken(UUID.randomUUID().toString());
        usu.setTokenExpiracao(cal.getTime());
        repository.save(usu);

		EmailUtils.EmailMessage mensagem = sendEmail.novaMensagem();

		mensagem.setSubject(env.getProperty("email.resetsenha.assunto"));

		String corpo = env.getProperty("email.resetsenha.corpo");
        if (corpo != null) {
            corpo = corpo.replace("#usuario#", usu.getNome());
            corpo = corpo.replace("#token#", usu.getToken());
        }
		mensagem.setBody(corpo);

		mensagem.setFrom(env.getProperty("email.resetsenha.emailOrigem"));
		mensagem.setFromName(env.getProperty("email.resetsenha.nomeOrigem"));

		mensagem.setToName(usu.getNome());
		mensagem.setTo(usu.getEmail());

		sendEmail.sendMessage(mensagem);

	}

	

	
}