package com.luxfacta.vernyy.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.base.AuthenticatedController;


@RestController
public class ApartamentoMoradorController extends AuthenticatedController {

	/*
	@Autowired
	private ApartamentoMoradorRepository repository;
    
	@Transactional
	@PostMapping(value = {"admin/v1/apartamentomorador/novo","morador/v1/apartamentomorador/novo"})
	public ResponseEntity<Integer> novo(@RequestBody RestApartamentoMorador value) throws BusinessRuleException,BusinessSecurityException {
        ApartamentoMorador dbo = (ApartamentoMorador) mapper.copyToDbObject(value);
        repository.save(dbo);
        return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = {"admin/v1/apartamentomorador/atualiza","morador/v1/apartamentomorador/atualiza"})
	public ResponseEntity<Integer> atualiza(@RequestBody RestApartamentoMorador value) throws BusinessRuleException, NotFoundException, BusinessSecurityException {
        if (value.getId() != null) {
            Long id = Mapper.decryptId(value.getId());
            Optional<ApartamentoMorador> dbo = repository.findById(id);
            if (dbo.isPresent()) {
                
                mapper.copyToDbObject(value, dbo.get());
                repository.save(dbo.get());

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@DeleteMapping(value = {"admin/v1/apartamentomorador/remove/{id}","morador/v1/apartamentomorador/remove/{id}"})
	public ResponseEntity<Integer> remove(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<ApartamentoMorador> dbo = repository.findById(Mapper.decryptId(id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = {"admin/v1/apartamentomorador/recupera/{id}","morador/v1/apartamentomorador/recupera/{id}"})
	public ResponseEntity<RestApartamentoMorador> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<ApartamentoMorador> dbo = repository.findById(Mapper.decryptId(id));
        if (dbo != null && dbo.isPresent()) {
            
            RestApartamentoMorador rest = (RestApartamentoMorador) mapper.copyToRestObject(dbo.get(), RestApartamentoMorador.class);
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = {"admin/v1/apartamentomorador/lista/{apartamento}","morador/v1/apartamentomorador/lista/{apartamento}"})
	public ResponseEntity<List<RestApartamentoMorador>> lista(@PathVariable String apartamento) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<ApartamentoMorador> itDbo = repository.findByAptId(Mapper.decryptId(apartamento));
        List<RestApartamentoMorador> restList =  (List<RestApartamentoMorador>) mapper.copyToRestObject(itDbo, RestApartamentoMorador.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	*/
}
