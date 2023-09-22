package com.luxfacta.vernyy.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import com.luxfacta.vernyy.api.model.Camera;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.repository.CameraRepository;
import com.luxfacta.vernyy.api.rest.RestCamera;


@RestController
public class CameraController extends AuthenticatedController {

    @Autowired
    private Environment env;
    
	@Autowired
	private CameraRepository repository;
    
	@Transactional
	@PostMapping(value = {"admin/v1/camera/novo","central/v1/camera/novo"})
	public ResponseEntity<Integer> novo(@RequestBody RestCamera value) throws BusinessRuleException,BusinessSecurityException {
        
        Camera dbo = (Camera) mapper.copyToDbObject(value);
        
        dbo.setDataAtualizacao(new Date());
        dbo.setDataCriacao(dbo.getDataAtualizacao());
        
        repository.save(dbo);
        return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = {"admin/v1/camera/atualiza","central/v1/camera/atualiza"})
	public ResponseEntity<Integer> atualiza(@RequestBody RestCamera value) throws BusinessRuleException, NotFoundException, BusinessSecurityException {


        if (value.getId() != null) {
            Long id = Mapper.decryptId(Camera.class, value.getId());
            Optional<Camera> dbo = repository.findById(id);
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
	@DeleteMapping(value = {"admin/v1/camera/remove/{id}","central/v1/camera/remove/{id}"})
	public ResponseEntity<Integer> remove(String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<Camera> dbo = repository.findById(Mapper.decryptId(Camera.class, id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = {"admin/v1/camera/recupera/{id}","central/v1/camera/recupera/{id}"})
	public ResponseEntity<RestCamera> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<Camera> dbo = repository.findById(Mapper.decryptId(Camera.class, id));
        if (dbo != null && dbo.isPresent()) {
            
            RestCamera rest = (RestCamera) mapper.copyToRestObject(dbo.get(), RestCamera.class);
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = "admin/v1/camera/lista/{condominio}")
	public ResponseEntity<List<RestCamera>> lista(@PathVariable String condominio) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<Camera> itDbo = repository.findByConIdByOrderByDescricao(Mapper.decryptId(Condominio.class, condominio));
        List<RestCamera> restList =  mapper.copyToRestObject(itDbo, RestCamera.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	@GetMapping(value = "system/v1/camera/video-feed/{id}")
	public ResponseEntity<String> videoFeed(@PathVariable String id) throws BusinessSecurityException {
        
        Optional<Camera> optCam = repository.findById(Mapper.decryptId(Camera.class, id));
        if (optCam.isPresent()) {
        	return new ResponseEntity<>(optCam.get().getAutenticacao(), HttpStatus.OK);
        }
        
    	return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}
	
	
	
	
}
