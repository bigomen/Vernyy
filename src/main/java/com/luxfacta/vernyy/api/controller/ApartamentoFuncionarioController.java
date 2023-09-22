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
import com.luxfacta.vernyy.api.model.ApartamentoFuncionario;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.model.Funcionario;
import com.luxfacta.vernyy.api.repository.ApartamentoFuncionarioRepository;
import com.luxfacta.vernyy.api.repository.ApartamentoRepository;
import com.luxfacta.vernyy.api.repository.FuncionarioRepository;
import com.luxfacta.vernyy.api.rest.RestApartamentoFuncionario;
import com.luxfacta.vernyy.api.shared.ExternalAPI;
import com.luxfacta.vernyy.api.shared.MaskUtils;

import jakarta.validation.Valid;


@RestController
public class ApartamentoFuncionarioController extends AuthenticatedController {

	private Logger logger = LoggerFactory.getLogger(ApartamentoFuncionarioController.class);

	
	@Autowired
	private ApartamentoFuncionarioRepository repository;

	@Autowired
	private FuncionarioRepository repositoryFuncionario;
	
	@Autowired
	private ApartamentoRepository repositoryApartamento;

	
	@Transactional
	@PostMapping(value = {"central/v1/apartamentofuncionario/novo", "admin/v1/apartamentofuncionario/novo","morador/v1/apartamentofuncionario/novo"})
	public ResponseEntity<RestApartamentoFuncionario> novo(@Valid @RequestBody RestApartamentoFuncionario value) throws BusinessRuleException,BusinessSecurityException, IOException {
        Long aptId = Mapper.decryptId(Apartamento.class, value.getAptId());

        super.checkApartamento(aptId);

        Funcionario fun = null;
        
        if (value.getCpf() != null) {
        	Optional<Funcionario> optFun= repositoryFuncionario.findByCpfAndAptId(MaskUtils.removeMascara(value.getCpf()), aptId );
        	if (optFun.isPresent()) {
        		fun = optFun.get();
        		
        	}
        }
        
        if (fun == null) {
        	Optional<Apartamento> aptFunc = repositoryApartamento.findById(Mapper.decryptId(Apartamento.class,  value.getAptId()));
        	
        	if (aptFunc.isPresent()) {
        		Long conId = aptFunc.get().getBloco().getConId();
        		Map<String, Object> dados = new HashMap<>();
        		
        		dados.put("fotos", value.getFotos());
        		dados.put("condominio_id", conId);
        		dados.put("tipo", "funcionario");
        		
        		String resposta = ExternalAPI.sendDataResponse(config.getProperty("api.valida.pessoa"), dados);
        		
        		if (!resposta.isEmpty()) {
        			ObjectMapper mapp = new ObjectMapper();
        			JsonNode json = mapp.readTree(resposta);
        			if (json.get("id").isInt()) {
        				Optional<Funcionario> optFunc = repositoryFuncionario.findById(json.get("id").asLong());
        				if (optFunc.isPresent()) {
        					fun = optFunc.get();
        				}
        			}
        		}
        	}
        }
        
        ApartamentoFuncionario dbo = (ApartamentoFuncionario) mapper.copyToDbObject(value);
        
        // Cria um novo funcionario caso nao tenha encontrado nenhum por CPF ou por Reconhecimento Facial
        if (fun == null) {
        	fun = new Funcionario();
        	fun.setCpf(MaskUtils.removeMascara(value.getCpf()));
        	fun.setDataCriacao(new Date());
        	fun.setDataAtualizacao(fun.getDataCriacao());
        	fun.setMapeado(Constantes.NAO);
        	fun.setListaBloqueio(Constantes.NAO);
        	fun.getApartamentoFuncionarioList().add(dbo);
        	fun.setSenha(value.getSenha());
        	fun.setSenhaPanico(value.getSenhaPanico());
        	
        	
            if (fun.getSenha() != null && fun.getSenha().equals(fun.getSenhaPanico())) {
            	throw new BusinessRuleException("A senha de pânico deve ser diferente da senha de acesso");
            }

        	
        } else {
    		for (ApartamentoFuncionario af : fun.getApartamentoFuncionarioList()) {
    			if (af.getAptId().equals(aptId)) {
    				throw new BusinessRuleException("Funcionário já se encontra cadastrado para esse apartamento");
    			}
    		}
        }
        
		if (value.getFotos() != null) {
			if (value.getFotos().length > 0) 
				dbo.setFotoFrente(value.getFotos()[0]);
			if (value.getFotos().length > 1) 
				dbo.setFotoEsquerda(value.getFotos()[1]);
			if (value.getFotos().length > 2) 
				dbo.setFotoDireita(value.getFotos()[2]);
			if (value.getFotos().length > 3) 
				dbo.setFotoAcima(value.getFotos()[3]);
			if (value.getFotos().length > 4) 
				dbo.setFotoAbaixo(value.getFotos()[4]);
		}

        
        dbo.setFuncionario(fun);
        dbo.setAptId(aptId);
        
        dbo.ajustaImagens();
        
    	fun.getApartamentoFuncionarioList().add(dbo);
        
    	repositoryFuncionario.save(fun);
    	enviaTreinamento(new String[] {dbo.getFotoAbaixo(), dbo.getFotoAcima(), dbo.getFotoDireita(), dbo.getFotoEsquerda(), dbo.getFotoFrente()}, fun.getId(), "funcionario");
        return new ResponseEntity<>((RestApartamentoFuncionario) mapper.copyToRestObject(dbo, RestApartamentoFuncionario.class),  HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = {"central/v1/apartamentofuncionario/atualiza", "admin/v1/apartamentofuncionario/atualiza","morador/v1/apartamentofuncionario/atualiza"})
	public ResponseEntity<Integer> atualiza(@Valid @RequestBody RestApartamentoFuncionario value) throws BusinessRuleException, NotFoundException, BusinessSecurityException, IOException {
        if (value.getId() != null && value.getAptId() != null) {
            Long id = Mapper.decryptId(ApartamentoFuncionario.class, value.getId());
            Optional<ApartamentoFuncionario> dbo = repository.findById(id);
            if (dbo.isPresent()) {
            	
            	String oldCpf = dbo.get().getFuncionario().getCpf();
            	
            	dbo.get().getFotoAbaixo();
            	dbo.get().getFotoAcima();
            	dbo.get().getFotoEsquerda();
            	dbo.get().getFotoDireita();
            	dbo.get().getFotoFrente();
            	
                mapper.copyToDbObject(value, dbo.get());
                dbo.get().setAptId(dbo.get().getApartamento().getId());
                
        		if (value.getFotos() != null) {
        			if (value.getFotos().length > 0) 
        				dbo.get().setFotoFrente(value.getFotos()[0]);
        			if (value.getFotos().length > 1) 
        				dbo.get().setFotoEsquerda(value.getFotos()[1]);
        			if (value.getFotos().length > 2) 
        				dbo.get().setFotoDireita(value.getFotos()[2]);
        			if (value.getFotos().length > 3) 
        				dbo.get().setFotoAcima(value.getFotos()[3]);
        			if (value.getFotos().length > 4) 
        				dbo.get().setFotoAbaixo(value.getFotos()[4]);
        		}
        		
        		Funcionario fun = dbo.get().getFuncionario();
        		fun.setCpf(oldCpf);
        		//fun.setCpf(MaskUtils.removeMascara(value.getCpf()));
        		
        		if (value.getSenha() != null)
        			fun.setSenha(value.getSenha());
        		
        		if (value.getSenhaPanico() != null)
        			fun.setSenhaPanico(value.getSenhaPanico());

                if (fun.getSenha() != null && fun.getSenha().equals(fun.getSenhaPanico())) {
                	throw new BusinessRuleException("A senha de pânico deve ser diferente da senha de acesso");
                }
                
                dbo.get().ajustaImagens();
                
                repository.save(dbo.get());
                
                enviaTreinamento(new String[] {dbo.get().getFotoAbaixo(), dbo.get().getFotoAcima(), dbo.get().getFotoDireita(), dbo.get().getFotoEsquerda(), dbo.get().getFotoFrente()}, dbo.get().getId(), "funcionario");
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@DeleteMapping(value = {"central/v1/apartamentofuncionario/remove/{id}", "admin/v1/apartamentofuncionario/remove/{id}","morador/v1/apartamentofuncionario/remove/{id}"})
	public ResponseEntity<Integer> remove(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<ApartamentoFuncionario> dbo = repository.findById(Mapper.decryptId(ApartamentoFuncionario.class, id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = {"central/v1/apartamentofuncionario/recupera/{id}", "admin/v1/apartamentofuncionario/recupera/{id}"})
	public ResponseEntity<RestApartamentoFuncionario> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<ApartamentoFuncionario> dbo = repository.findById(Mapper.decryptId(ApartamentoFuncionario.class, id));
        if (dbo != null && dbo.isPresent()) {
            mapper.setMaxLevel(1);
            
            RestApartamentoFuncionario rest = (RestApartamentoFuncionario) mapper.copyToRestObject(dbo.get(), RestApartamentoFuncionario.class);

        	String[] fotos = new String[5];
        	
        	fotos[0] = dbo.get().getFotoFrente();
        	fotos[1] = dbo.get().getFotoDireita();
        	fotos[2] = dbo.get().getFotoEsquerda();
        	fotos[3] = dbo.get().getFotoAbaixo();
        	fotos[4] = dbo.get().getFotoAcima();
        	
        	rest.setFotos(fotos);
        	
        	rest.setCpf(MaskUtils.removeMascara(dbo.get().getFuncionario().getCpf()));

            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	
	@GetMapping(value = {"morador/v1/apartamentofuncionario/recupera/{id}"})
	public ResponseEntity<RestApartamentoFuncionario> recuperaMorador(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<ApartamentoFuncionario> dbo = repository.findById(Mapper.decryptId(ApartamentoFuncionario.class, id));
        if (dbo != null && dbo.isPresent()) {
            mapper.setMaxLevel(1);

        	dbo.get().getFotoFrente();
        	dbo.get().getFotoDireita();
        	dbo.get().getFotoEsquerda();
        	dbo.get().getFotoAbaixo();
        	dbo.get().getFotoAcima();

            RestApartamentoFuncionario rest = (RestApartamentoFuncionario) mapper.copyToRestObject(dbo.get(), RestApartamentoFuncionario.class);
        	
        	
        	rest.setCpf(MaskUtils.removeMascara(dbo.get().getFuncionario().getCpf()));

            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	
	@GetMapping(value = {"admin/v1/apartamentofuncionario/lista/{apartamento}", "admin/v1/apartamentofuncionario/lista/{apartamento}","morador/v1/apartamentofuncionario/lista/{apartamento}"})
	public ResponseEntity<List<RestApartamentoFuncionario>> lista(@PathVariable String apartamento) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<ApartamentoFuncionario> itDbo = repository.findByAptIdOrderByNome(Mapper.decryptId(Apartamento.class, apartamento));
        List<RestApartamentoFuncionario> restList =  (List<RestApartamentoFuncionario>) mapper.copyToRestObject(itDbo, RestApartamentoFuncionario.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}


	@GetMapping(value = {"admin/v1/funcionario/retreina/{condominio}"})
	public ResponseEntity<String> retreina(@PathVariable String condominio) throws BusinessSecurityException, BusinessRuleException {
		List<ApartamentoFuncionario> lstFun = repository.findByConId(Mapper.decryptId(Condominio.class,condominio));
		
		for (ApartamentoFuncionario v : lstFun) {
			enviaTreinamento(new String[] {v.getFotoAbaixo(), v.getFotoAcima(), v.getFotoDireita(), v.getFotoEsquerda(), v.getFotoFrente()}, v.getEmpId(), "funcionario");
			v.setFotoAbaixo(null);
			v.setFotoAcima(null);
			v.setFotoDireita(null);
			v.setFotoEsquerda(null);
			v.setFotoFrente(null);
		}
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	private void enviaTreinamento(String[] fotos, Long id, String tipo) throws BusinessRuleException {

		logger.debug("Enviando funcionario para treinamento " + id);

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
