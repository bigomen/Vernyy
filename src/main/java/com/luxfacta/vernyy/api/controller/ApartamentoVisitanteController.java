package com.luxfacta.vernyy.api.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.model.Apartamento;
import com.luxfacta.vernyy.api.model.ApartamentoVisitante;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.model.Visitante;
import com.luxfacta.vernyy.api.repository.ApartamentoRepository;
import com.luxfacta.vernyy.api.repository.ApartamentoVisitanteRepository;
import com.luxfacta.vernyy.api.repository.VisitanteRepository;
import com.luxfacta.vernyy.api.rest.RestApartamentoVisitante;
import com.luxfacta.vernyy.api.shared.ExternalAPI;
import com.luxfacta.vernyy.api.shared.MaskUtils;

import jakarta.validation.Valid;


@RestController
public class ApartamentoVisitanteController extends AuthenticatedController {

	private Logger logger = LoggerFactory.getLogger(ApartamentoVisitanteController.class);

	
	@Autowired
	private ApartamentoVisitanteRepository repository;

	@Autowired
	private VisitanteRepository repositoryVisitante;
	
	@Autowired
	private ApartamentoRepository repositoryApartamento;

	
	@Transactional
	@PostMapping(value = {"admin/v1/apartamentovisitante/novo","morador/v1/apartamentovisitante/novo","central/v1/apartamentovisitante/novo"})
	public ResponseEntity<RestApartamentoVisitante> novo(@Valid @RequestBody RestApartamentoVisitante value) throws BusinessRuleException,BusinessSecurityException, IOException {
        Long aptId = Mapper.decryptId(Apartamento.class, value.getAptId());

        super.checkApartamento(aptId);
		

        Visitante vis = null;
        
        
        if (value.getCpf() != null) {
        	Optional<Visitante> optVis = repositoryVisitante.findByCpfAndAptId(MaskUtils.removeMascara(value.getCpf()), aptId );
        	if (optVis.isPresent()) {
        		vis = optVis.get();
        		
        	}
        }
        
        if (vis == null) {
        	String[] fotos = new String[] {value.getFoto()};
        	
        	// pegar id condominio
        	Optional<Apartamento> aptVis = repositoryApartamento.findById(Mapper.decryptId(Apartamento.class, value.getAptId()));
        	
        	if (aptVis.isPresent()) {
        		Long conId = aptVis.get().getBloco().getConId();
        		Map<String, Object> dados = new HashMap<>();
        		
        		dados.put("fotos", fotos);
        		dados.put("condominio_id", conId);
        		dados.put("tipo", "visitante");
        		
            	String resposta = ExternalAPI.sendDataResponse(config.getProperty("api.valida.pessoa"), dados);

            	if (!resposta.isEmpty()) {
            		ObjectMapper mapp = new ObjectMapper();
            		JsonNode json = mapp.readTree(resposta);
            		if (json.get("id").isInt()) {
            			Optional<Visitante> optVis = repositoryVisitante.findById(json.get("id").asLong());
            			if (optVis.isPresent()) {
            				vis = optVis.get();
            			}
            		}
            	}
        	}
        }
        
        ApartamentoVisitante dbo = (ApartamentoVisitante) mapper.copyToDbObject(value);
        
        Boolean insereApt = false;
        
        // Cria um novo visitante caso nao tenha encontrado nenhum por CPF ou por Reconhecimento Facial
        if (vis == null) {
        	vis = new Visitante();
        	vis.setCpf(MaskUtils.removeMascara(value.getCpf()));
        	vis.setDataCriacao(new Date());
        	vis.setMapeado(Constantes.NAO);
        	vis.setListaBloqueio(Constantes.NAO);
        	vis.getApartamentoVisitanteList().add(dbo);
        	
        } else {
    		for (ApartamentoVisitante av : vis.getApartamentoVisitanteList()) {
    			if (av.getAptId().equals(aptId)) {
    				throw new BusinessRuleException("Visitante já se encontra cadastrado para esse apartamento");
    			}
    		}
    		insereApt = true;
        }
        
		if (value.getFoto() != null) {
			dbo.setFoto(value.getFoto());
		}

        dbo.setVisitante(vis);
        dbo.setAptId(aptId);
        
        dbo.ajustaImagens();
        
        if (insereApt) {
        	vis.getApartamentoVisitanteList().add(dbo);
        }
        
    	repositoryVisitante.save(vis);
    	
    	enviaTreinamento(new String[] {dbo.getFoto()}, vis.getId(), "visitante");
        return new ResponseEntity<>((RestApartamentoVisitante) mapper.copyToRestObject(dbo, RestApartamentoVisitante.class),  HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = {"admin/v1/apartamentovisitante/atualiza","morador/v1/apartamentovisitante/atualiza","central/v1/apartamentovisitante/atualiza"})
	public ResponseEntity<Integer> atualiza(@Valid @RequestBody RestApartamentoVisitante value) throws BusinessRuleException, NotFoundException, BusinessSecurityException, IOException {
        if (value.getId() != null) {
            Long id = Mapper.decryptId(ApartamentoVisitante.class, value.getId());
            Optional<ApartamentoVisitante> dbo = repository.findById(id);
            if (dbo.isPresent()) {
            	dbo.get().getFoto();
                
                mapper.copyToDbObject(value, dbo.get());
                dbo.get().setAptId(dbo.get().getApartamento().getId());

        		if (value.getFoto() != null) {
    				dbo.get().setFoto(value.getFoto());
        		}

        		dbo.get().ajustaImagens();
        		
                repository.save(dbo.get());
                
                enviaTreinamento(new String[] {dbo.get().getFoto()}, dbo.get().getId(), "visitante");

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@DeleteMapping(value = {"admin/v1/apartamentovisitante/remove/{id}","morador/v1/apartamentovisitante/remove/{id}","central/v1/apartamentovisitante/remove/{id}"})
	public ResponseEntity<Integer> remove(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<ApartamentoVisitante> dbo = repository.findById(Mapper.decryptId(ApartamentoVisitante.class, id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = {"admin/v1/apartamentovisitante/recupera/{id}","central/v1/apartamentovisitante/recupera/{id}"})
	public ResponseEntity<RestApartamentoVisitante> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<ApartamentoVisitante> dbo = repository.findById(Mapper.decryptId(ApartamentoVisitante.class, id));
        if (dbo != null && dbo.isPresent()) {
            mapper.setMaxLevel(1);

            dbo.get().getFoto();

            RestApartamentoVisitante rest = (RestApartamentoVisitante) mapper.copyToRestObject(dbo.get(), RestApartamentoVisitante.class);
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = {"morador/v1/apartamentovisitante/recupera/{id}"})
	public ResponseEntity<RestApartamentoVisitante> recuperaMorador(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<ApartamentoVisitante> dbo = repository.findById(Mapper.decryptId(ApartamentoVisitante.class, id));
        if (dbo != null && dbo.isPresent()) {
            mapper.setMaxLevel(1);

            dbo.get().getFoto();
            
            RestApartamentoVisitante rest = (RestApartamentoVisitante) mapper.copyToRestObject(dbo.get(), RestApartamentoVisitante.class);
            rest.setCpf(MaskUtils.removeMascara(dbo.get().getVisitante().getCpf()));
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	
	
	@GetMapping(value = {"admin/v1/apartamentovisitante/lista/{apartamento}","morador/v1/apartamentovisitante/lista/{apartamento}","central/v1/apartamentovisitante/lista/{apartamento}"})
	public ResponseEntity<List<RestApartamentoVisitante>> lista(@PathVariable String apartamento) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<ApartamentoVisitante> itDbo = repository.findByAptIdOrderByNome(Mapper.decryptId(Apartamento.class, apartamento));
        List<RestApartamentoVisitante> restList =  (List<RestApartamentoVisitante>) mapper.copyToRestObject(itDbo, RestApartamentoVisitante.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	@GetMapping(value = {"admin/v1/visitante/retreina/{condominio}"})
	public ResponseEntity<String> retreina(@PathVariable String condominio) throws BusinessSecurityException, BusinessRuleException {
		List<ApartamentoVisitante> lstVis = repository.findByConId(Mapper.decryptId(Condominio.class,condominio));
		
		for (ApartamentoVisitante v : lstVis) {
			enviaTreinamento(new String[] {v.getFoto()}, v.getVisId(), "visitante");
			v.setFoto(null);
		}
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	private void enviaTreinamento(String[] fotos, Long id, String tipo) throws BusinessRuleException {
		
		logger.debug("Enviando visitante para treinamento " + id);

		
		Map<String, Object> dados = new HashMap<>();
		dados.put("id", id);
		dados.put("tipo", tipo);
		dados.put("fotos", fotos);
		
		int retorno = ExternalAPI.sendData(config.getProperty("api.treinamento.url"), dados);
		if (retorno != HttpStatus.OK.value()) {
			throw new BusinessRuleException("Treinamento nao realizado");
		}
	}
	
}
