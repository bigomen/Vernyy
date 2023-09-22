package com.luxfacta.vernyy.api.controller;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.model.TipoArea;
import com.luxfacta.vernyy.api.repository.TipoAreaRepository;
import com.luxfacta.vernyy.api.rest.RestTipoArea;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TipoAreaController extends AuthenticatedController {

	@Autowired
	private TipoAreaRepository repository;
    

	@GetMapping(value = "admin/v1/tipoarea/lista")
	public ResponseEntity<List<RestTipoArea>> lista() throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<TipoArea> itDbo = repository.findAll();
        List<RestTipoArea> restList =  (List<RestTipoArea>) mapper.copyToRestObject(itDbo, RestTipoArea.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}
