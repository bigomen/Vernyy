package com.luxfacta.vernyy.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.model.Perfil;
import com.luxfacta.vernyy.api.repository.PerfilRepository;
import com.luxfacta.vernyy.api.rest.RestPerfil;


@RestController
public class PerfilController extends AuthenticatedController {

	@Autowired
	private PerfilRepository repository;
 
	@GetMapping(value = "admin/v1/perfil/lista")
	public ResponseEntity<List<RestPerfil>> lista() throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<Perfil> itDbo = repository.findAll();
        List<RestPerfil> restList =  (List<RestPerfil>) mapper.copyToRestObject(itDbo, RestPerfil.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}
