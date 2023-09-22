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
import com.luxfacta.vernyy.api.model.Bloco;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.repository.BlocoRepository;
import com.luxfacta.vernyy.api.rest.RestBloco;


@RestController
public class BlocoController extends AuthenticatedController {

	@Autowired
	private BlocoRepository repository;

	@GetMapping(value = {"admin/v1/bloco/lista/{condominio}","central/v1/bloco/lista/{condominio}"})
	public ResponseEntity<List<RestBloco>> lista(@PathVariable String condominio) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<Bloco> itDbo = repository.findByConIdOrderByDescricao(Mapper.decryptId(Condominio.class, condominio));
        List<RestBloco> restList =  (List<RestBloco>) mapper.copyToRestObject(itDbo, RestBloco.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}
