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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.model.Apartamento;
import com.luxfacta.vernyy.api.model.Veiculo;
import com.luxfacta.vernyy.api.repository.VeiculoRepository;
import com.luxfacta.vernyy.api.rest.RestVeiculo;

import jakarta.validation.Valid;


@RestController
public class VeiculoController extends AuthenticatedController {

	@Autowired
	private VeiculoRepository repository;
    
	@Transactional
	@PostMapping(value = {"admin/v1/veiculo/novo","morador/v1/veiculo/novo"})
	public ResponseEntity<RestVeiculo> novo(@Valid @RequestBody RestVeiculo value) throws BusinessRuleException,BusinessSecurityException {
		
		checkApartamento(Mapper.decryptId(Apartamento.class, value.getAptId()));

        Veiculo dbo = (Veiculo) mapper.copyToDbObject(value);
        dbo.setDataCriacao(new Date());
        dbo.setDataAtualizacao(dbo.getDataCriacao());
        repository.save(dbo);

        return new ResponseEntity<>((RestVeiculo) mapper.copyToRestObject(dbo, RestVeiculo.class),  HttpStatus.OK);

	}

	@Transactional
	@PutMapping(value = {"admin/v1/veiculo/atualiza","morador/v1/veiculo/atualiza"})
	public ResponseEntity<Integer> atualiza(@Valid @RequestBody RestVeiculo value) throws BusinessRuleException, NotFoundException, BusinessSecurityException {
		checkApartamento(Mapper.decryptId(Apartamento.class, value.getAptId()));

		if (value.getId() != null) {
            Long id = Mapper.decryptId(Veiculo.class, value.getId());
                    
            Optional<Veiculo> dbo = repository.findById(id);
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
	@DeleteMapping(value = {"admin/v1/veiculo/remove/{id}","morador/v1/veiculo/remove/{id}"})
	public ResponseEntity<Integer> remove(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<Veiculo> dbo = repository.findById(Mapper.decryptId(Veiculo.class, id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = {"admin/v1/veiculo/recupera/{id}","morador/v1/veiculo/recupera/{id}"} )
	public ResponseEntity<RestVeiculo> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<Veiculo> dbo = repository.findById(Mapper.decryptId(Veiculo.class, id));
        if (dbo != null && dbo.isPresent()) {
            
            RestVeiculo rest = (RestVeiculo) mapper.copyToRestObject(dbo.get(), RestVeiculo.class);
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = {"admin/v1/veiculo/lista/{apartamento}", "central/v1/veiculo/lista/{apartamento}", "morador/v1/veiculo/lista/{apartamento}"})
	public ResponseEntity<List<RestVeiculo>> lista(@PathVariable String apartamento) throws BusinessSecurityException, BusinessRuleException {
		
		checkApartamento(Mapper.decryptId(Apartamento.class, apartamento));

        mapper.setMaxLevel(1);
        
        Iterable<Veiculo> itDbo = repository.findByAptIdOrderByModelo(Mapper.decryptId(Apartamento.class, apartamento));
        List<RestVeiculo> restList =  (List<RestVeiculo>) mapper.copyToRestObject(itDbo, RestVeiculo.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	

	
}
