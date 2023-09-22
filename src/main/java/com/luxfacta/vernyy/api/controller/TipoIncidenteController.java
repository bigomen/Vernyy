package com.luxfacta.vernyy.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.luxfacta.vernyy.api.model.TipoIncidente;
import com.luxfacta.vernyy.api.repository.TipoIncidenteRepository;
import com.luxfacta.vernyy.api.rest.RestCondominio;
import com.luxfacta.vernyy.api.rest.RestTipoIncidente;

import jakarta.transaction.Transactional;


@RestController
public class TipoIncidenteController extends AuthenticatedController {

	@Autowired
	private TipoIncidenteRepository repository;
    
	 @Transactional
		@PostMapping(value = "admin/v1/tipo-incidente/novo")
		public ResponseEntity<RestTipoIncidente> novo(@RequestBody RestTipoIncidente value) throws BusinessRuleException,BusinessSecurityException {
	        
	        TipoIncidente dbo = (TipoIncidente) mapper.copyToDbObject(value);
	        repository.save(dbo);
	        
	        return new ResponseEntity<>((RestTipoIncidente) mapper.copyToRestObject(dbo, RestCondominio.class),  HttpStatus.OK);
		}

		@Transactional
		@PutMapping(value = "admin/v1/tipo-incidente/atualiza")
		public ResponseEntity<Integer> atualiza(@RequestBody RestTipoIncidente value) throws BusinessRuleException, NotFoundException, BusinessSecurityException {

	        if (value.getId() != null) {
	            Long id = Mapper.decryptId(TipoIncidente.class, value.getId());
	            Optional<TipoIncidente> dbo = repository.findById(id);
	            
	            if (dbo.isPresent()) {
	                mapper.copyToDbObject(value, dbo.get());
	                repository.save(dbo.get());

	                return new ResponseEntity<>(HttpStatus.OK);
	            }
	        }
			throw new NotFoundException();
		}
		
		@Transactional
		@DeleteMapping(value = "admin/v1/tipo-incidente/remove/{id}")
		public ResponseEntity<Integer> remove(@PathVariable String id) throws NotFoundException, BusinessSecurityException, BusinessRuleException {
	        if (id != null) {
	            Optional<TipoIncidente> dbo = repository.findById(Mapper.decryptId(TipoIncidente.class, id));
	            if (dbo.isPresent() ) {
	                
	            	if (dbo.get().getIncidenteList().isEmpty()) {
	            		throw new BusinessRuleException("Não é possível remover um tipo de incidente vinculado a incidentes registrados");
	            	}

	            	
	            	repository.delete(dbo.get());
	                return new ResponseEntity<>(HttpStatus.OK);
	            }
	        }
			throw new NotFoundException();
		}
		
		@GetMapping(value = "admin/v1/tipo-incidente/recupera/{id}")
		public ResponseEntity<RestTipoIncidente> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
	        Optional<TipoIncidente> dbo = repository.findById(Mapper.decryptId(TipoIncidente.class, id));
	        if (dbo != null && dbo.isPresent()) {
	        	
	        	RestTipoIncidente rest = (RestTipoIncidente) mapper.copyToRestObject(dbo.get(), RestTipoIncidente.class);
	            
	            return new ResponseEntity<>(rest, HttpStatus.OK);
	        }
			throw new NotFoundException();
		}
	
	
	@GetMapping(value = "admin/v1/tipo-incidente/lista")
	public ResponseEntity<List<RestTipoIncidente>> lista() throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<TipoIncidente> itDbo = repository.findAll();
        List<RestTipoIncidente> restList =  mapper.copyToRestObject(itDbo, RestTipoIncidente.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}
