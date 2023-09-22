package com.luxfacta.vernyy.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.luxfacta.vernyy.api.model.Contato;
import com.luxfacta.vernyy.api.repository.ContatoRepository;
import com.luxfacta.vernyy.api.rest.RestContato;


@RestController
public class ContatoController extends AuthenticatedController {

	@Autowired
	private ContatoRepository repository;
    
	@Transactional
	@PostMapping(value = "admin/v1/contato/novo")
	public ResponseEntity<Integer> novo(@RequestBody RestContato value) throws BusinessRuleException,BusinessSecurityException {
        Contato dbo = (Contato) mapper.copyToDbObject(value);
        repository.save(dbo);
        return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = "admin/v1/contato/atualiza")
	public ResponseEntity<Integer> atualiza(@RequestBody RestContato value) throws BusinessRuleException, NotFoundException, BusinessSecurityException {
        if (value.getId() != null) {
            Long id = Mapper.decryptId(Contato.class, value.getId());
            Optional<Contato> dbo = repository.findById(id);
            if (dbo.isPresent()) {
                
                mapper.copyToDbObject(value, dbo.get());
                repository.save(dbo.get());

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@DeleteMapping(value = "admin/v1/contato/remove/{id}")
	public ResponseEntity<Integer> remove(String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<Contato> dbo = repository.findById(Mapper.decryptId(Contato.class, id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = "admin/v1/contato/recupera/{id}")
	public ResponseEntity<RestContato> recupera(String id) throws NotFoundException, BusinessSecurityException {
        Optional<Contato> dbo = repository.findById(Mapper.decryptId(Contato.class, id));
        if (dbo != null && dbo.isPresent()) {
            
            RestContato rest = (RestContato) mapper.copyToRestObject(dbo.get(), RestContato.class);
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = "admin/v1/contato/lista/{pagina}")
	public ResponseEntity<List<RestContato>> lista(@PathVariable int pagina) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        Pageable pageable = PageRequest.of(pagina, 25);
        
        Iterable<Contato> itDbo = repository.findAll(pageable);
        List<RestContato> restList =  (List<RestContato>) mapper.copyToRestObject(itDbo, RestContato.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}
