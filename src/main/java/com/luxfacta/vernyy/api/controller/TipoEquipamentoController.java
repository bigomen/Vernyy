package com.luxfacta.vernyy.api.controller;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.model.TipoEquipamento;
import com.luxfacta.vernyy.api.repository.TipoEquipamentoRepository;
import com.luxfacta.vernyy.api.rest.RestTipoEquipamento;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TipoEquipamentoController extends AuthenticatedController {

	@Autowired
	private TipoEquipamentoRepository repository;
    

	@GetMapping(value = "admin/v1/tipoequipamento/lista")
	public ResponseEntity<List<RestTipoEquipamento>> lista() throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<TipoEquipamento> itDbo = repository.findAll();
        List<RestTipoEquipamento> restList =  (List<RestTipoEquipamento>) mapper.copyToRestObject(itDbo, RestTipoEquipamento.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}
