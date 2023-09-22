package com.luxfacta.vernyy.api.controller;

import java.util.Date;
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
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.model.AgendamentoVisitante;
import com.luxfacta.vernyy.api.model.ApartamentoVisitante;
import com.luxfacta.vernyy.api.repository.AgendamentoVisitanteRepository;
import com.luxfacta.vernyy.api.rest.RestAgendamentoVisitante;

import jakarta.validation.Valid;


@RestController
public class AgendamentoVisitanteController extends AuthenticatedController {
	@Autowired
	private AgendamentoVisitanteRepository repository;
    
	@Transactional
	@PostMapping(value = {"admin/v1/agendamentovisitante/novo", "morador/v1/agendamentovisitante/novo"})
	public ResponseEntity<RestAgendamentoVisitante> novo(@Valid @RequestBody RestAgendamentoVisitante value) throws BusinessRuleException,BusinessSecurityException {
        AgendamentoVisitante dbo = (AgendamentoVisitante) mapper.copyToDbObject(value);
        dbo.setDataCriacao(new Date());
        repository.save(dbo);
        return new ResponseEntity<>((RestAgendamentoVisitante) mapper.copyToRestObject(dbo, RestAgendamentoVisitante.class),  HttpStatus.OK);
	}

	
	@Transactional
	@DeleteMapping(value = {"admin/v1/agendamentovisitante/remove/{id}","morador/v1/agendamentovisitante/remove/{id}"})
	public ResponseEntity<Integer> remove(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<AgendamentoVisitante> dbo = repository.findById(Mapper.decryptId(AgendamentoVisitante.class, id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	

	@GetMapping(value = {"admin/v1/agendamentovisitante/lista/{visitante_apartamento}","morador/v1/agendamentovisitante/lista/{visitante_apartamento}"})
	public ResponseEntity<List<RestAgendamentoVisitante>> lista(@PathVariable String visitante_apartamento) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
         
        Iterable<AgendamentoVisitante> itDbo = repository.findByVapId(Mapper.decryptId(ApartamentoVisitante.class, visitante_apartamento));
        List<RestAgendamentoVisitante> restList =  (List<RestAgendamentoVisitante>) mapper.copyToRestObject(itDbo, RestAgendamentoVisitante.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}

	@GetMapping(value = {"admin/v1/agendamentovisitante/historico/{visitante_apartamento}","morador/v1/agendamentovisitante/historico/{visitante_apartamento}"})
	public ResponseEntity<List<RestAgendamentoVisitante>> listaHistorico(@PathVariable String visitante_apartamento) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<AgendamentoVisitante> itDbo = repository.findByVapIdLast(Mapper.decryptId(ApartamentoVisitante.class, visitante_apartamento));
        List<RestAgendamentoVisitante> restList =  (List<RestAgendamentoVisitante>) mapper.copyToRestObject(itDbo, RestAgendamentoVisitante.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}

}
