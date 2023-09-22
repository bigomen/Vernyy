package com.luxfacta.vernyy.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.model.AgendamentoArea;
import com.luxfacta.vernyy.api.model.Area;
import com.luxfacta.vernyy.api.repository.AreaAgendamentoRepository;
import com.luxfacta.vernyy.api.rest.RestAgendamentoArea;


@RestController
public class AgendamentoAreaController extends AuthenticatedController {

	@Autowired
	private AreaAgendamentoRepository repository;
    
	@Transactional
	@PostMapping(value = {"admin/v1/agendamentoarea/novo","morador/v1/agendamentoarea/novo"})
	public ResponseEntity<RestAgendamentoArea> novo(@RequestBody RestAgendamentoArea value) throws BusinessRuleException,BusinessSecurityException {
        AgendamentoArea dbo = (AgendamentoArea) mapper.copyToDbObject(value);
        
        List<AgendamentoArea> check = repository.findDisponibilidade(dbo.getAreId(), dbo.getDataInicio(), dbo.getDataFim());
        if (!check.isEmpty()) {
        	throw new BusinessRuleException("Área já se encontra agendada para o período informado");
        }
        
        repository.save(dbo);
        return new ResponseEntity<>((RestAgendamentoArea) mapper.copyToRestObject(dbo, RestAgendamentoArea.class),  HttpStatus.OK);
	}

	
	@Transactional
	@DeleteMapping(value = {"admin/v1/agendamentoarea/remove/{id}","morador/v1/agendamentoarea/remove/{id}"})
	public ResponseEntity<Integer> remove(@PathVariable String id) throws NotFoundException, BusinessSecurityException, BusinessRuleException {
        if (id != null) {
            Optional<AgendamentoArea> dbo = repository.findById(Mapper.decryptId(AgendamentoArea.class, id));
            if (dbo.isPresent()) {
            	super.checkApartamento(dbo.get().getApartamento().getId()); 
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	

	@GetMapping(value = {"admin/v1/agendamentoarea/lista/{area}","morador/v1/agendamentoarea/lista/{area}"})
	public ResponseEntity<List<RestAgendamentoArea>> lista(@PathVariable String area) throws BusinessSecurityException, BusinessRuleException {
        mapper.setMaxLevel(1);
        
        Iterable<AgendamentoArea> itDbo = repository.findByAreId(Mapper.decryptId(Area.class, area));
        
        List<RestAgendamentoArea> restList =  new ArrayList<>();
        for (AgendamentoArea a : itDbo) {
        	RestAgendamentoArea ra = (RestAgendamentoArea)mapper.copyToRestObject(a, RestAgendamentoArea.class);
        	try {
        		super.checkApartamento(a.getApartamento().getId());
        		ra.setPodeExcluir(Constantes.SIM);
        	} catch (Exception e) {
        		ra.setPodeExcluir(Constantes.NAO);
        	}
        	
        	restList.add(ra);
        }
        
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}


	
	@GetMapping(value = {"admin/v1/agendamentoarea/historico/{area}","morador/v1/agendamentoarea/historico/{area}"})
	public ResponseEntity<List<RestAgendamentoArea>> listaHistorico(@PathVariable String area) throws BusinessSecurityException, BusinessRuleException {
        mapper.setMaxLevel(1);
        
        Iterable<AgendamentoArea> itDbo = repository.findByAreIdLast(Mapper.decryptId(Area.class, area));
        
        List<RestAgendamentoArea> restList =  new ArrayList<>();
        for (AgendamentoArea a : itDbo) {
        	RestAgendamentoArea ra = (RestAgendamentoArea)mapper.copyToRestObject(a, RestAgendamentoArea.class);
    		ra.setPodeExcluir(Constantes.NAO);
        	
        	restList.add(ra);
        }
        
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}

    
}
