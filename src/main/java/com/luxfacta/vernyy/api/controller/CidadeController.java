package com.luxfacta.vernyy.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.model.Cidade;
import com.luxfacta.vernyy.api.model.Uf;
import com.luxfacta.vernyy.api.repository.CidadeRepository;
import com.luxfacta.vernyy.api.rest.RestCidade;


@RestController
public class CidadeController extends AuthenticatedController {

	@Autowired
	private CidadeRepository repository;
    
	@GetMapping(value = {"admin/v1/cidade/lista/{uf}","central/v1/cidade/lista/{uf}"})
	public ResponseEntity<List<RestCidade>> lista(@PathVariable String uf) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<Cidade> itDbo = repository.findByUfIdOrderByNome(Mapper.decryptId(Uf.class, uf));
        List<RestCidade> restList =  (List<RestCidade>) mapper.copyToRestObject(itDbo, RestCidade.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}
