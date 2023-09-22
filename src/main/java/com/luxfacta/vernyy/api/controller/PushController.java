package com.luxfacta.vernyy.api.controller;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.model.Push;
import com.luxfacta.vernyy.api.repository.PushRepository;
import com.luxfacta.vernyy.api.rest.RestPush;
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


@RestController
public class PushController extends AuthenticatedController {

	@Autowired
	private PushRepository repository;
    
	@Transactional
	@PostMapping(value = "admin/v1/push/novo")
	public ResponseEntity<Integer> novo(@RequestBody RestPush value) throws BusinessRuleException,BusinessSecurityException {
        Push dbo = (Push) mapper.copyToDbObject(value);
        repository.save(dbo);
        return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = "admin/v1/push/atualiza")
	public ResponseEntity<Integer> atualiza(@RequestBody RestPush value) throws BusinessRuleException, NotFoundException, BusinessSecurityException {
        if (value.getId() != null) {
            Long id = Mapper.decryptId(Push.class, value.getId());
            Optional<Push> dbo = repository.findById(id);
            if (dbo.isPresent()) {
                
                mapper.copyToDbObject(value, dbo.get());
                repository.save(dbo.get());

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@DeleteMapping(value = "admin/v1/push/remove/{id}")
	public ResponseEntity<Integer> remove(String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<Push> dbo = repository.findById(Mapper.decryptId(Push.class, id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = "admin/v1/push/recupera/{id}")
	public ResponseEntity<RestPush> recupera(String id) throws NotFoundException, BusinessSecurityException {
        Optional<Push> dbo = repository.findById(Mapper.decryptId(Push.class, id));
        if (dbo != null && dbo.isPresent()) {
            
            RestPush rest = (RestPush) mapper.copyToRestObject(dbo.get(), RestPush.class);
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = "admin/v1/push/lista/{pagina}")
	public ResponseEntity<List<RestPush>> lista(@PathVariable int pagina) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        Pageable pageable = PageRequest.of(pagina, 25);
        
        Iterable<Push> itDbo = repository.findAll(pageable);
        List<RestPush> restList =  (List<RestPush>) mapper.copyToRestObject(itDbo, RestPush.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}
