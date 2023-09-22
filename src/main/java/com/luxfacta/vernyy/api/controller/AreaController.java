package com.luxfacta.vernyy.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.luxfacta.vernyy.api.constants.Constantes;
import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.exception.NotFoundException;
import com.luxfacta.vernyy.api.model.Apartamento;
import com.luxfacta.vernyy.api.model.Area;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.model.Usuario;
import com.luxfacta.vernyy.api.push.FCMService;
import com.luxfacta.vernyy.api.push.Notification;
import com.luxfacta.vernyy.api.repository.AreaRepository;
import com.luxfacta.vernyy.api.repository.CondominioRepository;
import com.luxfacta.vernyy.api.rest.RestArea;
import com.luxfacta.vernyy.api.rest.RestCamera;
import com.luxfacta.vernyy.api.shared.CronUtils;
import com.luxfacta.vernyy.api.shared.ExternalAPI;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;


@RestController
public class AreaController extends AuthenticatedController {

	@Autowired
	private AreaRepository repository;

	
	@Autowired
	private CondominioRepository repositoryCondominio;

	@Autowired
	private FCMService fcm;

    @Autowired
    private Environment env;

	@Transactional
	@PostMapping(value = "admin/v1/area/novo")
	public ResponseEntity<Integer> novo(@RequestBody RestArea value) throws BusinessRuleException,BusinessSecurityException, IOException {
        Area dbo = (Area) mapper.copyToDbObject(value);
        dbo.setDataAtualizacao(new Date());
        dbo.setDataCriacao(dbo.getDataAtualizacao());
        
        dbo.ajustaImagens();
        
        repository.save(dbo);
        return new ResponseEntity<>(HttpStatus.OK);
	}

	private String ajustaCron(String cron) {
		if (cron != null && !cron.contains(" ") && cron.length() == 4) {
			return cron.substring(0,2) + " " + cron.substring(2,4);
		}
		return cron;
	}
	
	
	@Transactional
	@PutMapping(value = "admin/v1/area/atualiza")
	public ResponseEntity<Integer> atualiza(@RequestBody RestArea value) throws BusinessRuleException, NotFoundException, BusinessSecurityException, IOException {

        if (value.getId() != null) {
            Long id = Mapper.decryptId(Area.class, value.getId());
            Optional<Area> dbo = repository.findById(id);
            if (dbo.isPresent()) {
            	
            	dbo.get().getFoto();
            	
            	Long conId = dbo.get().getConId();
                mapper.copyToDbObject(value, dbo.get());
                dbo.get().setConId(conId);
                dbo.get().setDataAtualizacao(new Date());
                
                dbo.get().setCronFim1(ajustaCron(dbo.get().getCronFim1()));
                dbo.get().setCronFim2(ajustaCron(dbo.get().getCronFim2()));
                dbo.get().setCronFim3(ajustaCron(dbo.get().getCronFim3()));
                
                dbo.get().ajustaImagens();
                
                repository.save(dbo.get());

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@DeleteMapping(value = "admin/v1/area/remove/{id}")
	public ResponseEntity<Integer> remove(String id) throws NotFoundException, BusinessSecurityException {
        if (id != null) {
            Optional<Area> dbo = repository.findById(Mapper.decryptId(Area.class, id));
            if (dbo.isPresent()) {
                
                repository.delete(dbo.get());
                
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}
	
	@GetMapping(value = {"admin/v1/area/recupera/{id}", "central/v1/area/recupera/{id}"})
	public ResponseEntity<RestArea> recupera(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<Area> dbo = repository.findById(Mapper.decryptId(Area.class, id));
        mapper.setMaxLevel(2);
        if (dbo.isPresent()) {
            
            RestArea rest = (RestArea) mapper.copyToRestObject(dbo.get(), RestArea.class);
            
            List<String> horariosDisponiveis = new ArrayList<>();
            if (dbo.get().getCronInicio1() != null) {
            	horariosDisponiveis.add(CronUtils.toText(dbo.get().getCronInicio1(), dbo.get().getCronFim1()));
            }
            if (dbo.get().getCronInicio2() != null) {
            	horariosDisponiveis.add(CronUtils.toText(dbo.get().getCronInicio2(), dbo.get().getCronFim2()));
            }
            if (dbo.get().getCronInicio3() != null) {
            	horariosDisponiveis.add(CronUtils.toText(dbo.get().getCronInicio3(), dbo.get().getCronFim3()));
            }
            
            rest.setDisponibilidades(horariosDisponiveis);
            rest.setFoto(dbo.get().getFoto());
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = {"morador/v1/area/recupera/{id}"})
	public ResponseEntity<RestArea> recuperaMorador(@PathVariable String id) throws NotFoundException, BusinessSecurityException {
        Optional<Area> dbo = repository.findById(Mapper.decryptId(Area.class, id));
        mapper.setMaxLevel(2);
        if (dbo != null && dbo.isPresent()) {
            
            RestArea rest = (RestArea) mapper.copyToRestObject(dbo.get(), RestArea.class);
            
            List<String> horariosDisponiveis = new ArrayList<>();
            if (dbo.get().getCronInicio1() != null) {
            	horariosDisponiveis.add(CronUtils.toText(dbo.get().getCronInicio1(), dbo.get().getCronFim1()));
            }
            if (dbo.get().getCronInicio2() != null) {
            	horariosDisponiveis.add(CronUtils.toText(dbo.get().getCronInicio2(), dbo.get().getCronFim2()));
            }
            if (dbo.get().getCronInicio3() != null) {
            	horariosDisponiveis.add(CronUtils.toText(dbo.get().getCronInicio3(), dbo.get().getCronFim3()));
            }
            
            rest.setIpCondominio(dbo.get().getCondominio().getIpExterno());
            
            List<RestCamera> listCam = mapper.copyToRestObject(dbo.get().getCameraList(), RestCamera.class);
            rest.setCameras(listCam);
            
            rest.setDisponibilidades(horariosDisponiveis);
            rest.setFoto(dbo.get().getFoto());
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	
	@GetMapping(value = "admin/v1/area/lista/{condominio}")
	public ResponseEntity<List<RestArea>> lista(@PathVariable String condominio) throws BusinessSecurityException {
        mapper.setMaxLevel(2);
        
        Iterable<Area> itDbo = repository.findByConIdOrderByDescricao(Mapper.decryptId(Condominio.class, condominio));
        List<RestArea> restList =  mapper.copyToRestObject(itDbo, RestArea.class);
        for (RestArea a : restList )
        	a.setFoto(null);
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}

	@GetMapping(value = "morador/v1/area/lista/{apartamento}")
	public ResponseEntity<List<RestArea>> listaPorApartamento(@PathVariable String apartamento) throws BusinessSecurityException, NotFoundException {
        mapper.setMaxLevel(2);
        
        
        List<Area> itDbo = repository.findByAptId(Mapper.decryptId(Apartamento.class, apartamento));
        List<RestArea> restList =  (List<RestArea>) mapper.copyToRestObject(itDbo, RestArea.class);
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
        	
	}

	@GetMapping(value = "morador/v1/area/solicitaAbertura/{token}/{apartamento}/{area}/{tipo}")
	public void solicitaAbertura(@PathVariable String token,  @PathVariable String apartamento, @PathVariable String area, @PathVariable String tipo) throws BusinessRuleException, BusinessSecurityException {
		super.checkApartamento(Mapper.decryptId(Apartamento.class, apartamento));
		
        Optional<Area> optAr = repository.findByIdAndAptId(Mapper.decryptId(Area.class, area), Mapper.decryptId(Apartamento.class, apartamento));
        if (optAr.isPresent()) {
    		Map<String, Object> dados = new HashMap<>();
    		dados.put("id", area);
    		dados.put("tipo", tipo);
    		dados.put("token", token);
    		
    		String ipCondominio = optAr.get().getCondominio().getIpExterno();
            
    		String retorno = ExternalAPI.getData(ipCondominio + ":8111/porta/" + token + "/" + area + "/" + tipo);
    		if (!"OK".equals(retorno)) {
    			throw new BusinessRuleException("Erro ao solicitar abertura de porta");
    		}
        }
	}
	
	@GetMapping(value = "system/v1/area/aciona/{id}/{tipo}")
	public ResponseEntity<Map<String,String>> acionaPorta(@PathVariable String id, @PathVariable String tipo) throws BusinessSecurityException {
        
        Optional<Area> optArea = repository.findById(Mapper.decryptId(Area.class, id));
        if (optArea.isPresent()) {
        	String urlAbertura = null;
        	String urlFechamento = null;
        	if (Constantes.PORTA_INTERNA.equals(tipo)) {
        		urlAbertura = optArea.get().getUrlInternoAbrir();
        		urlFechamento = optArea.get().getUrlInternoFechar();
        	} else if (Constantes.PORTA_EXTERNA.equals(tipo)) {
        		urlAbertura = optArea.get().getUrlExternoAbrir();
        		urlFechamento = optArea.get().getUrlExternoFechar();
        	}

    		Map<String, String> dados = new HashMap<>();
    		dados.put("abre", urlAbertura);
    		dados.put("fecha", urlFechamento);

        	return new ResponseEntity<>(dados, HttpStatus.OK);
        }
        
    	return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}

	
	@GetMapping(value = "publico/v1/area/tablet-config/{id}")
	public ResponseEntity<Map<String,String>> recuperaConfig(@PathVariable String id) throws BusinessSecurityException {
        
        Optional<Area> optArea = repository.findByMacExternoOrMacInterno(id,id);
        if (optArea.isPresent()) {
        	
        	String modo = null;
        	String areaId = null;
        	String ip = null;

        	Area ar = optArea.get();
    		ip = ar.getCondominio().getIpInterno();
    		areaId = Mapper.encryptIdWithoutToken(Area.class, ar.getId());

    		if (ar.getMacExterno() != null && ar.getMacExterno().equals(id)) {
        		modo = "E";
        	} else if (ar.getMacInterno() != null && ar.getMacInterno().equals(id)) {
        		modo = "I";
        	}

    		Map<String, String> dados = new HashMap<>();
    		dados.put("modo", modo);
    		dados.put("id", areaId);
    		dados.put("ip", ip);

        	return new ResponseEntity<>(dados, HttpStatus.OK);
        }
        
    	return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}
	
	
	@GetMapping(value = "publico/v1/area/tablet-chamada/{idTablet}")
	public ResponseEntity<Integer> chamada(@PathVariable String idTablet) throws BusinessSecurityException {
        
        Optional<Area> optArea = repository.findByMacExternoOrMacInterno(idTablet,idTablet);
        if (optArea.isPresent()) {

        	Area ar = optArea.get();

        	Condominio con = optArea.get().getCondominio();
        	con.setNotificacao(Constantes.SIM);
        	repositoryCondominio.save(con);
        	
        	List<Usuario> usuList = ar.getCondominio().getUsuarioList();
    		
    		for (Usuario u : usuList) {
    			
    			if (u.getPushToken() != null) {
            		Notification not = new Notification(
            				"JITSI", 
            				"Chamada de video do tablet:" + idTablet, 
            				u.getPushToken());
    				fcm.sendNotification(not);
    			}
    		}

        	return new ResponseEntity<>(HttpStatus.OK);
        }
        
    	return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}
	
	
	@GetMapping(value = {"admin/v1/area/token-chamada", "central/v1/area/token-chamada"})
	public ResponseEntity<Map<String,String>>  generateToken()
    {
        try {

            Map<String, Map<String,String>> context = new HashMap<>();
            Map<String, String> user = new HashMap<>();
            user.put("name", super.getLoginUsuario());
            user.put("email", super.getLoginUsuario());
            context.put("user", user);

            JWSSigner signer = new MACSigner(env.getProperty("jitsi.segredo"));

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(super.getLoginUsuario())
                    .expirationTime(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000))
                    .notBeforeTime(new Date())
                    .claim("aud", "jitsi")
                    .claim("iss", env.getProperty("jitsi.chave"))
                    .claim("room", "*")
                    .claim("context", context)
                    .claim("sub", env.getProperty("jitsi.url"))
                    .build();

            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT).build();

            JWSObject jwsObject = new JWSObject(header, new Payload(claims.toJSONObject()));


            jwsObject.sign(signer);
            
            Map<String,String> retorno = new HashMap<>();
            retorno.put("token", jwsObject.serialize());
            retorno.put("url", env.getProperty("jitsi.url"));
            return  new ResponseEntity<>(retorno, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
}
