package com.luxfacta.vernyy.api.controller;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.model.Uf;
import com.luxfacta.vernyy.api.repository.UfRepository;
import com.luxfacta.vernyy.api.rest.RestUf;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UfController extends AuthenticatedController {

	@Autowired
	private UfRepository repository;
    
	@GetMapping(value = {"admin/v1/uf/lista","central/v1/uf/lista"})
	public ResponseEntity<List<RestUf>> lista() throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<Uf> itDbo = repository.findAllByOrderBySigla();
        List<RestUf> restList =  (List<RestUf>) mapper.copyToRestObject(itDbo, RestUf.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}
