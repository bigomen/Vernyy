package com.luxfacta.vernyy.api.controller;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.model.TipoTelefone;
import com.luxfacta.vernyy.api.repository.TipoTelefoneRepository;
import com.luxfacta.vernyy.api.rest.RestTipoTelefone;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TipoTelefoneController extends AuthenticatedController {

	@Autowired
	private TipoTelefoneRepository repository;
    

	@GetMapping(value = "admin/v1/tipotelefone/lista")
	public ResponseEntity<List<RestTipoTelefone>> lista() throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<TipoTelefone> itDbo = repository.findAll();
        List<RestTipoTelefone> restList =  (List<RestTipoTelefone>) mapper.copyToRestObject(itDbo, RestTipoTelefone.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}
