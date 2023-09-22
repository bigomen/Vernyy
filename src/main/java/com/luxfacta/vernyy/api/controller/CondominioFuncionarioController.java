package com.luxfacta.vernyy.api.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxfacta.vernyy.api.base.AuthenticatedController;
import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.model.CondominioFuncionario;
import com.luxfacta.vernyy.api.model.Funcionario;
import com.luxfacta.vernyy.api.repository.AreaRepository;
import com.luxfacta.vernyy.api.repository.CondominioFuncionarioRepository;
import com.luxfacta.vernyy.api.repository.CondominioRepository;
import com.luxfacta.vernyy.api.repository.FuncionarioRepository;
import com.luxfacta.vernyy.api.rest.RestCondominioFuncionario;
import com.luxfacta.vernyy.api.shared.ExternalAPI;
import com.luxfacta.vernyy.api.shared.MaskUtils;


@RestController
public class CondominioFuncionarioController extends AuthenticatedController {

	@Autowired
	private CondominioFuncionarioRepository repository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private CondominioRepository condominioRepository;


    @Transactional
	@PostMapping(value = "admin/v1/condominiofuncionario/novo")
	public ResponseEntity<Integer> novo(@RequestBody RestCondominioFuncionario value) throws BusinessRuleException,BusinessSecurityException, IOException {
        Long conId = Mapper.decryptId(Condominio.class, value.getConId());

        Funcionario fun = null;

        if (value.getCpf() != null) {
        	Optional<Funcionario> optFun= funcionarioRepository.findByCpf(MaskUtils.removeMascara(value.getCpf()));
        	if (optFun.isPresent()) {
        		fun = optFun.get();
        		
        	}
        }

        if(fun == null){
        	
        	Optional<Condominio> aptFunc = condominioRepository.findById(Mapper.decryptId(Condominio.class,  value.getConId()));
        	
        	if (aptFunc.isPresent()) {
        		Map<String, Object> dados = new HashMap<>();
        		
        		dados.put("fotos", value.getFotos());
        		dados.put("condominio_id", conId);
        		dados.put("tipo", "funcionario");
        		
        		String resposta = ExternalAPI.sendDataResponse(config.getProperty("api.valida.pessoa"), dados);
        		
        		if (resposta != null && !resposta.isEmpty()) {
        			ObjectMapper mapp = new ObjectMapper();
        			JsonNode json = mapp.readTree(resposta);
        			if (json.get("id").isInt()) {
        				Optional<Funcionario> optFunc = funcionarioRepository.findById(json.get("id").asLong());
        				if (optFunc.isPresent()) {
        					fun = optFunc.get();
        				}
        			}
        		}
        	}
        }

        CondominioFuncionario dbo = (CondominioFuncionario) mapper.copyToDbObject(value);
        

        if(fun == null){
            fun = new Funcionario();
            fun.setCpf(MaskUtils.removeMascara(value.getCpf()));
            fun.setDataCriacao(new Date());
            fun.setDataAtualizacao(fun.getDataCriacao());
            fun.setMapeado(Constantes.NAO);
            fun.setListaBloqueio(Constantes.NAO);
            fun.getCondominioFuncionarioList().add(dbo);
            
        } else {
    		for (CondominioFuncionario af : fun.getCondominioFuncionarioList()) {
    			if (af.getConId().equals(conId)) {
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
        dbo.setConId(conId);
        
        /*
        if(value.getAreaList() != null){
            List<Area> areas = new ArrayList<>();
            for(RestArea a : value.getAreaList()){
                areas.add((Area) mapper.copyToDbObject(a));
            }
            dbo.setAreaList(areas);
        }
        */
        
        dbo.ajustaImagens();
        
    	fun.getCondominioFuncionarioList().add(dbo);
        
        funcionarioRepository.save(fun);
        enviaTreinamento(new String[] {dbo.getFotoAbaixo(), dbo.getFotoAcima(), dbo.getFotoDireita(), dbo.getFotoEsquerda(), dbo.getFotoFrente()}, fun.getId(), "funcionario");
        return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@PutMapping(value = "admin/v1/condominiofuncionario/atualiza")
	public ResponseEntity<Integer> atualiza(@RequestBody RestCondominioFuncionario value) throws BusinessRuleException, NotFoundException, BusinessSecurityException, IOException {
        if (value.getId() != null) {
            Long id = Mapper.decryptId(CondominioFuncionario.class, value.getId());
            Optional<CondominioFuncionario> dbo = repository.findById(id);
            if (dbo.isPresent()) {
                String oldCpf = dbo.get().getFuncionario().getCpf();
            	
                dbo.get().getFotoAbaixo();
            	dbo.get().getFotoAcima();
            	dbo.get().getFotoEsquerda();
            	dbo.get().getFotoDireita();
            	dbo.get().getFotoFrente();
                
                mapper.copyToDbObject(value, dbo.get());
                dbo.get().setConId(dbo.get().getCondominio().getId());
                
                
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
                
                
                dbo.get().getFuncionario().setCpf(oldCpf);
                
                dbo.get().ajustaImagens();
                
                repository.save(dbo.get());
                
                enviaTreinamento(new String[] {dbo.get().getFotoAbaixo(), dbo.get().getFotoAcima(), dbo.get().getFotoDireita(), dbo.get().getFotoEsquerda(), dbo.get().getFotoFrente()}, dbo.get().getId(), "funcionario");

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@DeleteMapping(value = "admin/v1/condominiofuncionario/remove/{id}")
	public ResponseEntity<Integer> remove(String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<CondominioFuncionario> dbo = repository.findById(Mapper.decryptId(CondominioFuncionario.class, id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = "admin/v1/condominiofuncionario/recupera/{id}")
	public ResponseEntity<RestCondominioFuncionario> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<CondominioFuncionario> dbo = repository.findById(Mapper.decryptId(CondominioFuncionario.class, id));
        if (dbo.isPresent()) {
            
            RestCondominioFuncionario rest = (RestCondominioFuncionario) mapper.copyToRestObject(dbo.get(), RestCondominioFuncionario.class);
            
        	String[] fotos = new String[5];
        	
        	fotos[0] = dbo.get().getFotoFrente();
        	fotos[1] = dbo.get().getFotoDireita();
        	fotos[2] = dbo.get().getFotoEsquerda();
        	fotos[3] = dbo.get().getFotoAbaixo();
        	fotos[4] = dbo.get().getFotoAcima();
        	
        	rest.setFotos(fotos);
            
            rest.setCpf(dbo.get().getFuncionario().getCpf());
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = "admin/v1/condominiofuncionario/lista/{condominio}")
	public ResponseEntity<List<RestCondominioFuncionario>> lista(@PathVariable String condominio) throws BusinessSecurityException {
        mapper.setMaxLevel(1);
        
        Iterable<CondominioFuncionario> itDbo = repository.findByConId(Mapper.decryptId(Condominio.class, condominio));
        List<RestCondominioFuncionario> restList =  mapper.copyToRestObject(itDbo, RestCondominioFuncionario.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	@GetMapping(value = {"admin/v1/funcionario_condominio/retreina/{condominio}"})
	public ResponseEntity<String> retreina(@PathVariable String condominio) throws BusinessSecurityException, BusinessRuleException {
		List<CondominioFuncionario> lstFun = repository.findByConId(Mapper.decryptId(Condominio.class,  condominio));
		
		for (CondominioFuncionario c: lstFun) {
			enviaTreinamento(new String[] {c.getFotoAbaixo(), c.getFotoAcima(), c.getFotoDireita(), c.getFotoEsquerda(), c.getFotoFrente()}, c.getEmpId(), "funcionario");
			c.setFotoAbaixo(null);
			c.setFotoAcima(null);
			c.setFotoDireita(null);
			c.setFotoEsquerda(null);
			c.setFotoFrente(null);
		}
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	private void enviaTreinamento(String[] fotos, Long id, String tipo) throws BusinessRuleException {
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
