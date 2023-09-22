package com.luxfacta.vernyy.api.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.model.Apartamento;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.repository.ApartamentoRepository;
import com.luxfacta.vernyy.api.rest.RestApartamento;


@RestController
public class ApartamentoController extends AuthenticatedController {

	@Autowired
	private ApartamentoRepository repository;
    
	@Transactional
	@PostMapping(value = "admin/v1/apartamento/novo")
	public ResponseEntity<RestApartamento> novo(@RequestBody RestApartamento value) throws BusinessRuleException,BusinessSecurityException {
        Apartamento dbo = (Apartamento) mapper.copyToDbObject(value);
        dbo.setDataCriacao(new Date());
        dbo.setDataAtualizacao(dbo.getDataCriacao());
        repository.save(dbo);
        return new ResponseEntity<>((RestApartamento)mapper.copyToRestObject(dbo, RestApartamento.class),  HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = "admin/v1/apartamento/atualiza")
	public ResponseEntity<Integer> atualiza(@RequestBody RestApartamento value) throws BusinessRuleException, NotFoundException, BusinessSecurityException {
        if (value.getId() != null) {
            Long id = Mapper.decryptId(Apartamento.class, value.getId());
            Optional<Apartamento> dbo = repository.findById(id);
            if (dbo.isPresent()) {
                
                mapper.copyToDbObject(value, dbo.get());
                dbo.get().setDataAtualizacao(new Date());
                repository.save(dbo.get());

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@DeleteMapping(value = "admin/v1/apartamento/remove/{id}")
	public ResponseEntity<Integer> remove(@PathVariable String id) throws NotFoundException, BusinessSecurityException, BusinessRuleException {
        if (id != null) {
            Optional<Apartamento> dbo = repository.findById(Mapper.decryptId(Apartamento.class, id));
            if (dbo.isPresent()) {
            	
            	if (!dbo.get().getApartamentoMoradorList().isEmpty()) {
            		throw new BusinessRuleException("Existem moradores vinculados a este apartamento. Exclua os moradores antes de remover o apartamento");
            	}
            	
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = {"admin/v1/apartamento/recupera/{id}","central/v1/apartamento/recupera/{id}"})
	public ResponseEntity<RestApartamento> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<Apartamento> dbo = repository.findById(Mapper.decryptId(Apartamento.class, id));
        if (dbo != null && dbo.isPresent()) {
            
            RestApartamento rest = (RestApartamento) mapper.copyToRestObject(dbo.get(), RestApartamento.class);
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = {"admin/v1/apartamento/lista/{condominio}","central/v1/apartamento/lista/{condominio}"})
	public ResponseEntity<List<RestApartamento>> lista(@PathVariable String condominio) throws BusinessSecurityException {
        mapper.setMaxLevel(1);

        Iterable<Apartamento> itDbo = repository.findByConId(Mapper.decryptId(Condominio.class, condominio));
        List<RestApartamento> restList = new ArrayList<>();	
        for (Apartamento a : itDbo) {
        	RestApartamento ra = (RestApartamento) mapper.copyToRestObject(a, RestApartamento.class);
        	ra.setBlocoNome(a.getBloco().getDescricao());
        	ra.setCondominioNome(a.getBloco().getCondominio().getNome());
        	restList.add(ra);
        }
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}


	@GetMapping(value = "morador/v1/apartamento/lista")
	public ResponseEntity<List<RestApartamento>> lista() throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<Apartamento> itDbo = repository.findByResId(super.getIdUsuario());
        List<RestApartamento> restList = new ArrayList<>();	
        for (Apartamento a : itDbo) {
        	RestApartamento ra = (RestApartamento) mapper.copyToRestObject(a, RestApartamento.class);
        	ra.setBlocoNome(a.getBloco().getDescricao());
        	ra.setCondominioNome(a.getBloco().getCondominio().getNome());
        	restList.add(ra);
        }
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}

	
}
