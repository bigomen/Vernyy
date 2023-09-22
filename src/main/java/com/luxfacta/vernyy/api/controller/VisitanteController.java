package com.luxfacta.vernyy.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.base.AuthenticatedController;


@RestController
public class VisitanteController extends AuthenticatedController {

	/*
	@Autowired
	private VisitanteRepository repository;
    
	@Transactional
	@PostMapping(value = {"admin/v1/visitante/novo","morador/v1/visitante/novo"})
	public ResponseEntity<Integer> novo(@RequestBody RestVisitante value) throws BusinessRuleException,BusinessSecurityException {
        Visitante dbo = (Visitante) mapper.copyToDbObject(value);
        repository.save(dbo);
        return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = {"admin/v1/visitante/atualiza","morador/v1/visitante/atualiza"})
	public ResponseEntity<Integer> atualiza(@RequestBody RestVisitante value) throws BusinessRuleException, NotFoundException, BusinessSecurityException {
        if (value.getId() != null) {
            Long id = Mapper.decryptId(value.getId());
            Optional<Visitante> dbo = repository.findById(id);
            if (dbo.isPresent()) {
                
                mapper.copyToDbObject(value, dbo.get());
                repository.save(dbo.get());

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@DeleteMapping(value = {"admin/v1/visitante/remove/{id}","morador/v1/visitante/remove/{id}"})
	public ResponseEntity<Integer> remove(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<Visitante> dbo = repository.findById(Mapper.decryptId(id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = {"admin/v1/visitante/recupera/{id}","morador/v1/visitante/recupera/{id}"})
	public ResponseEntity<RestVisitante> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<Visitante> dbo = repository.findById(Mapper.decryptId(id));
        if (dbo != null && dbo.isPresent()) {
            
            RestVisitante rest = (RestVisitante) mapper.copyToRestObject(dbo.get(), RestVisitante.class);
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = {"admin/v1/visitante/lista/{apartamento}", "morador/v1/visitante/lista/{apartamento}"})
	public ResponseEntity<List<RestVisitante>> lista(@PathVariable String apartamento) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<Visitante> itDbo = repository.findByAptId(Mapper.decryptId(apartamento));
        List<RestVisitante> restList =  (List<RestVisitante>) mapper.copyToRestObject(itDbo, RestVisitante.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}

	
	*/

}
