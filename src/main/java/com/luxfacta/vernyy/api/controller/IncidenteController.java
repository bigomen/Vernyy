package com.luxfacta.vernyy.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.model.AcaoTipoIncidente;
import com.luxfacta.vernyy.api.model.Apartamento;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.model.HistoricoIncidente;
import com.luxfacta.vernyy.api.model.Incidente;
import com.luxfacta.vernyy.api.push.FCMService;
import com.luxfacta.vernyy.api.repository.AcaoTipoIncidenteRepository;
import com.luxfacta.vernyy.api.repository.ApartamentoRepository;
import com.luxfacta.vernyy.api.repository.IncidenteRepository;
import com.luxfacta.vernyy.api.rest.RestArea;
import com.luxfacta.vernyy.api.rest.RestHistoricoIncidente;
import com.luxfacta.vernyy.api.rest.RestIncidente;


@RestController
public class IncidenteController extends AuthenticatedController {

	@Autowired
	private IncidenteRepository repository;

	@Autowired
	private AcaoTipoIncidenteRepository repositoryAcaoTipoIncidente;

	@Autowired
	private ApartamentoRepository repositoryApartamento;

	@Autowired
	private FCMService fcm;

    @Autowired
    private Environment env;

	@Transactional
	@PostMapping(value = {"morador/v1/incidente/novo","system/v1/incidente/novo" })
	public ResponseEntity<Integer> novo(@RequestBody RestIncidente value) throws BusinessSecurityException {
        Incidente dbo = (Incidente) mapper.copyToDbObject(value);
        dbo.setDataAbertura(new Date());
        dbo.setInsId(Constantes.PENDENTE);
        
        repository.save(dbo);
        return new ResponseEntity<>(HttpStatus.OK);
	}

	
	
	@Transactional
	@PutMapping(value = {"admin/v1/incidente/assume/{idIncidente}", "central/v1/incidente/assume/{idIncidente}"})
	public ResponseEntity<Integer> assume(@PathVariable String idIncidente) throws NotFoundException, BusinessSecurityException {

        Long id = Mapper.decryptId(Incidente.class, idIncidente);
        Optional<Incidente> dbo = repository.findById(id);
        if (dbo.isPresent()) {
        	
        	dbo.get().setUsuId(super.getIdUsuario());
            dbo.get().setInsId(Constantes.EM_ATENDIMENTO);
            
            repository.save(dbo.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@PutMapping(value = {"admin/v1/incidente/cancela/{idIncidente}", "central/v1/incidente/cancela/{idIncidente}"})
	public ResponseEntity<Integer> cancela(@PathVariable String idIncidente) throws NotFoundException, BusinessSecurityException {

        Long id = Mapper.decryptId(Incidente.class, idIncidente);
        Optional<Incidente> dbo = repository.findById(id);
        if (dbo.isPresent()) {
        	
        	dbo.get().setUsuId(super.getIdUsuario());
            dbo.get().setInsId(Constantes.CANCELADO);
            
            repository.save(dbo.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@Transactional
	@PutMapping(value = {"admin/v1/incidente/finaliza/{idIncidente}", "central/v1/incidente/finaliza/{idIncidente}"})
	public ResponseEntity<Integer> finaliza(@PathVariable String idIncidente) throws NotFoundException, BusinessSecurityException {

        Long id = Mapper.decryptId(Incidente.class, idIncidente);
        Optional<Incidente> dbo = repository.findById(id);
        if (dbo.isPresent()) {
        	
        	dbo.get().setUsuId(super.getIdUsuario());
            dbo.get().setInsId(Constantes.FINALIZADO);
            
            repository.save(dbo.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	
	@Transactional
	@PutMapping(value = {"admin/v1/incidente/executa", "central/v1/incidente/executa"})
	public ResponseEntity<Integer> executa(@RequestBody RestHistoricoIncidente value) throws NotFoundException, BusinessSecurityException {

        Long id = Mapper.decryptId(Incidente.class, value.getIncId());
        Optional<Incidente> dbo = repository.findById(id);

        Long idAcao = Mapper.decryptId(AcaoTipoIncidente.class, value.getItaId());
        Optional<AcaoTipoIncidente> dboAc = repositoryAcaoTipoIncidente.findById(idAcao);

        if (dbo.isPresent() && dboAc.isPresent()) {

        	HistoricoIncidente his = new HistoricoIncidente();
        	his.setAcao(dboAc.get().getAcao());
        	his.setIncId(dbo.get().getId());
        	his.setData(new Date());
        	his.setUseId(super.getIdUsuario());
        	his.setObservacao(value.getObservacao());
        	
            
            repository.save(dbo.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	
	@GetMapping(value = {"admin/v1/incidente/recupera/{id}", "central/v1/incidente/recupera/{id}", "morador/v1/area/recupera/{id}"})
	public ResponseEntity<RestIncidente> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<Incidente> dbo = repository.findById(Mapper.decryptId(Incidente.class, id));
        mapper.setMaxLevel(2);
        if (dbo.isPresent()) {
            
            RestIncidente rest = (RestIncidente) mapper.copyToRestObject(dbo.get(), RestArea.class);
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
            
        }
		throw new NotFoundException();
	}


	
	@GetMapping(value = {"admin/v1/incidente/lista/{status}/{condominio}","central/v1/incidente/lista/{status}/{condominio}"} )
	public ResponseEntity<List<RestIncidente>> lista(@PathVariable Long status, @PathVariable String condominio) throws BusinessSecurityException {
        mapper.setMaxLevel(2);
        
        Iterable<Incidente> itDbo = repository.findByConIdAndInsIdOrderByDataAberturaDesc(status,Mapper.decryptId(Condominio.class, condominio));
        
        List<RestIncidente> restList =  mapper.copyToRestObject(itDbo, RestIncidente.class);

        return new ResponseEntity<>(restList, HttpStatus.OK);
	}

	@GetMapping(value = "morador/v1/incidente/lista/{status}/{apartamento}")
	public ResponseEntity<List<RestIncidente>> listaPorApartamento(@PathVariable Long status, @PathVariable String apartamento) throws BusinessSecurityException, NotFoundException {
        mapper.setMaxLevel(2);
        
        
        Optional<Apartamento> optApt = repositoryApartamento.findById(Mapper.decryptId(Apartamento.class, apartamento));
        if (optApt.isPresent()) {
	        Iterable<Incidente> itDbo = repository.findByConIdAndInsIdOrderByDataAberturaDesc(status,optApt.get().getBloco().getConId());
	        List<RestIncidente> restList =  mapper.copyToRestObject(itDbo, RestIncidente.class);
	        return new ResponseEntity<>(restList, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	
	
	
	
}
