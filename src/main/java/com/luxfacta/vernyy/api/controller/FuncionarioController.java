package com.luxfacta.vernyy.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.jwt.TokenAuthenticationService;
import com.luxfacta.vernyy.api.model.Funcionario;
import com.luxfacta.vernyy.api.repository.FuncionarioRepository;
import com.luxfacta.vernyy.api.rest.RestFuncionario;
import com.luxfacta.vernyy.api.rest.RestMoradorLogin;


@RestController
public class FuncionarioController extends AuthenticatedController {

	@Autowired
	private FuncionarioRepository repository;

	
	
	@Transactional
	@PostMapping(value = "/loginFuncionario")
	public ResponseEntity<RestFuncionario> login(@RequestBody RestMoradorLogin value) throws NotFoundException,BusinessSecurityException {
        Optional<Funcionario> dbo = repository.findByTelefoneCelularAndSenha(value.getTelefoneCelular(), value.getSenha());
        
        if (dbo != null && dbo.isPresent()) {

            List<String> rolesGrupo = new ArrayList<>();
            rolesGrupo.add(Constantes.ROLE_FUNCIONARIO);
            	

            TokenAuthenticationService tks = new TokenAuthenticationService(config);
            String tokenAuth = tks.generateToken("FUN" + dbo.get().getId(), rolesGrupo, null, null, null, dbo.get().getId(), Long.parseLong(config.getProperty("security.expiration_app")), config.getProperty("security.secret"));
            String tokenRefresh = tks.generateToken("FUN" + dbo.get().getId(), rolesGrupo, null, null, null, dbo.get().getId(), Long.parseLong(config.getProperty("security.refresh.expiration_app")), config.getProperty("security.secret"));

            tks.updateContext("FUN" + dbo.get().getId(), rolesGrupo, null, null, dbo.get().getId());

            HttpHeaders header = new HttpHeaders();
            header.add(TokenAuthenticationService.HEADER_AUTHORIZATION, tokenAuth);
            header.add(TokenAuthenticationService.HEADER_REFRESH, tokenRefresh);

        	
            RestFuncionario rest = (RestFuncionario) mapper.copyToRestObject(dbo.get(), RestFuncionario.class);
            
            return new ResponseEntity<>(rest, header, HttpStatus.OK);
        }
        
		throw new NotFoundException();
		
	}

    @GetMapping(value = "admin/v1/funcionario/lista")
    public ResponseEntity<List<RestFuncionario>> lista() throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        List<Funcionario> funcionarios = (List<Funcionario>) repository.findAll();
        List<RestFuncionario> restFuncionarios = mapper.copyToRestObject(funcionarios, RestFuncionario.class);
        return new ResponseEntity<>(restFuncionarios, HttpStatus.OK);
    }
	
}
