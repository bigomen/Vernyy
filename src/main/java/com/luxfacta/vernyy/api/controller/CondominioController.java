package com.luxfacta.vernyy.api.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.model.Bloco;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.model.Uf;
import com.luxfacta.vernyy.api.repository.CondominioRepository;
import com.luxfacta.vernyy.api.rest.RestBloco;
import com.luxfacta.vernyy.api.rest.RestCidade;
import com.luxfacta.vernyy.api.rest.RestCondominio;
import com.luxfacta.vernyy.api.rest.RestUf;
import com.luxfacta.vernyy.api.shared.MaskUtils;

import jakarta.transaction.Transactional;


@RestController
public class CondominioController extends AuthenticatedController {

	@Autowired
	private CondominioRepository repository;


    @Transactional
	@PostMapping(value = "admin/v1/condominio/novo")
	public ResponseEntity<RestCondominio> novo(@RequestBody RestCondominio value) throws BusinessRuleException,BusinessSecurityException {
        Date agora = new Date();
        
        Condominio dbo = (Condominio) mapper.copyToDbObject(value);
        dbo.setDataCriacao(agora);
        dbo.setDataUltimaAtualizacao(agora);
        dbo.setCep(MaskUtils.removeMascara(dbo.getCep()));
        dbo.setAtivo(Constantes.SIM);
        dbo.setNotificacao(Constantes.NAO);
        
        for (Bloco b : dbo.getBlocoList()) {
        	b.setDataAtualizacao(agora);
    		b.setDataCriacao(agora);
    		b.setCondominio(dbo);
        }
        repository.save(dbo);
        return new ResponseEntity<>((RestCondominio) mapper.copyToRestObject(dbo, RestCondominio.class),  HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = "admin/v1/condominio/atualiza")
	public ResponseEntity<Integer> atualiza(@RequestBody RestCondominio value) throws BusinessRuleException, NotFoundException, BusinessSecurityException {

        if (value.getId() != null) {
            Long id = Mapper.decryptId(Condominio.class, value.getId());
            Optional<Condominio> dbo = repository.findById(id);
            
            Date agora = new Date();
            
            if (dbo.isPresent()) {
                mapper.copyToDbObject(value, dbo.get());
                for (Bloco b : new HashSet<Bloco>(dbo.get().getBlocoList())) {
                	boolean bEncontrou = false;	
                	
                	for (RestBloco rb : value.getBlocoList()) {
                		if (rb != null && rb.getId() != null &&  b.getId().equals(Mapper.decryptId(Bloco.class, rb.getId()))) {
                			mapper.copyToDbObject(rb, b);
                			bEncontrou = true;
                        	b.setDataAtualizacao(agora);
                			break;
                		}
                	}
                	
                	if (!bEncontrou) {
                		dbo.get().getBlocoList().remove(b);
                	}
                	
                }
                
            	for (RestBloco rb : value.getBlocoList()) {
                	if (rb != null && rb.getId() == null) {
                		Bloco b = (Bloco) mapper.copyToDbObject(rb);
                		b.setCondominio(dbo.get());
                		b.setDataAtualizacao(agora);
                		b.setDataCriacao(agora);
                		dbo.get().getBlocoList().add(b);
                	}
                	
                }
                
                
                dbo.get().setDataUltimaAtualizacao(agora);
                dbo.get().setCep(MaskUtils.removeMascara(dbo.get().getCep()));

                repository.save(dbo.get());

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@DeleteMapping(value = "admin/v1/condominio/remove/{id}")
	public ResponseEntity<Integer> remove(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<Condominio> dbo = repository.findById(Mapper.decryptId(Condominio.class, id));
            if (dbo.isPresent()) {
                
                dbo.get().setAtivo(Constantes.NAO);
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = "admin/v1/condominio/recupera/{id}")
	public ResponseEntity<RestCondominio> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<Condominio> dbo = repository.findById(Mapper.decryptId(Condominio.class, id));
        if (dbo != null && dbo.isPresent()) {
        	
            RestCondominio rest = (RestCondominio) mapper.copyToRestObject(dbo.get(), RestCondominio.class);
            rest.setUfId(Mapper.encryptId(Uf.class,  dbo.get().getCidade().getUf().getId()));
            rest.setBlocoList(mapper.copyToRestObject(dbo.get().getBlocoList(), RestBloco.class));
            rest.setToken(Mapper.encryptIdWithoutToken(Condominio.class, dbo.get().getId()));
            //rest.getCidade().setUf((RestUf)mapper.copyToRestObject(dbo.get().getCidade().getUf(), RestUf.class));
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = "admin/v1/condominio/lista")
	public ResponseEntity<List<RestCondominio>> lista() throws BusinessSecurityException, NotFoundException {
        
        Iterable<Condominio> itDbo = repository.findAll();
        List<RestCondominio> restList =  new ArrayList<>();
        for (Condominio c : itDbo) {
        	
        	RestCondominio rest = (RestCondominio) mapper.copyToRestObject(c, RestCondominio.class);
        	rest.setCidade( (RestCidade) mapper.copyToRestObject(c.getCidade(), RestCidade.class) );
        	rest.getCidade().setUf( (RestUf) mapper.copyToRestObject(c.getCidade().getUf(), RestUf.class) );
        	restList.add(rest);
        }
        
        
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}

	
	@GetMapping(value = "central/v1/condominio/lista")
	public ResponseEntity<List<RestCondominio>> listaCentral() throws BusinessSecurityException, NotFoundException {
        Iterable<Condominio> itDbo = repository.findByUsuario(super.getIdUsuario());
        List<RestCondominio> restList =  new ArrayList<>();
        for (Condominio c : itDbo) {
        	
        	RestCondominio rest = (RestCondominio) mapper.copyToRestObject(c, RestCondominio.class);
        	rest.setCidade( (RestCidade) mapper.copyToRestObject(c.getCidade(), RestCidade.class) );
        	rest.getCidade().setUf( (RestUf) mapper.copyToRestObject(c.getCidade().getUf(), RestUf.class) );
        	restList.add(rest);
        }
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}

}
