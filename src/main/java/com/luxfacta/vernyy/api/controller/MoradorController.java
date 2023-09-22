package com.luxfacta.vernyy.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.luxfacta.vernyy.api.jwt.TokenAuthenticationService;
import com.luxfacta.vernyy.api.message.IBaseMessage;
import com.luxfacta.vernyy.api.message.SuccessMessage;
import com.luxfacta.vernyy.api.model.Apartamento;
import com.luxfacta.vernyy.api.model.ApartamentoMorador;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.model.Morador;
import com.luxfacta.vernyy.api.repository.ApartamentoMoradorRepository;
import com.luxfacta.vernyy.api.repository.MoradorRepository;
import com.luxfacta.vernyy.api.rest.RestApartamentoMorador;
import com.luxfacta.vernyy.api.rest.RestMorador;
import com.luxfacta.vernyy.api.rest.RestMoradorLogin;
import com.luxfacta.vernyy.api.shared.CryptoUtils;
import com.luxfacta.vernyy.api.shared.DateUtils;
import com.luxfacta.vernyy.api.shared.EmailUtils;
import com.luxfacta.vernyy.api.shared.ExternalAPI;
import com.luxfacta.vernyy.api.shared.MaskUtils;

import jakarta.validation.Valid;


@RestController
public class MoradorController extends AuthenticatedController {

	private Logger logger = LoggerFactory.getLogger(MoradorController.class);

	
	@Autowired
	private EmailUtils sendEmail;
	
	@Autowired
	private MoradorRepository repository;

	@Autowired
	private ApartamentoMoradorRepository repositoryAptoMorador;

	
	@Transactional
	@PostMapping(value = "/loginMorador")
	public ResponseEntity<RestMorador> login(@RequestBody RestMoradorLogin value) throws BusinessRuleException,BusinessSecurityException {
        Optional<Morador> dbo = repository.findByTelefoneCelularAndSenha(MaskUtils.removeMascara(value.getTelefoneCelular()), value.getSenha());
        if (dbo.isPresent()) {

            List<String> rolesGrupo = new ArrayList<>();
            rolesGrupo.add(Constantes.ROLE_MORADOR);
            	
            Long[] apartamentos = new Long[dbo.get().getApartamentoMoradorList().size()];
            Long[] condominios = new Long[dbo.get().getApartamentoMoradorList().size()];
            
            for (int i = 0; i < dbo.get().getApartamentoMoradorList().size(); i++) {
        		apartamentos[i] = dbo.get().getApartamentoMoradorList().get(i).getApartamento().getId();
        		condominios[i] = dbo.get().getApartamentoMoradorList().get(i).getApartamento().getBloco().getCondominio().getId();
            }

            TokenAuthenticationService tks = new TokenAuthenticationService(config);
            String tokenAuth = tks.generateToken(dbo.get().getCpf(), rolesGrupo, condominios, apartamentos, dbo.get().getNome(), dbo.get().getId(), Long.parseLong(config.getProperty("security.expiration_app")), config.getProperty("security.secret"));
            String tokenRefresh = tks.generateToken(dbo.get().getCpf(), rolesGrupo, condominios, apartamentos, dbo.get().getNome(), dbo.get().getId(), Long.parseLong(config.getProperty("security.refresh.expiration_app")), config.getProperty("security.secret"));

            tks.updateContext(dbo.get().getCpf(), rolesGrupo, condominios, apartamentos, dbo.get().getId());

            HttpHeaders header = new HttpHeaders();
            header.add(TokenAuthenticationService.HEADER_AUTHORIZATION, tokenAuth);
            header.add(TokenAuthenticationService.HEADER_REFRESH, tokenRefresh);

        	
            RestMorador rest = (RestMorador) mapper.copyToRestObject(dbo.get(), RestMorador.class);
            
            return new ResponseEntity<>(rest, header, HttpStatus.OK);

            
        }
		throw new  BusinessRuleException("Dados inválidos.");
		
	}
	
	
	@Transactional
	@GetMapping(value = "/forgotMorador/{telefone}")
	public ResponseEntity<IBaseMessage> forgot(@PathVariable String telefone) throws Exception {
		
        Optional<Morador> dbo = repository.findByTelefoneCelular(MaskUtils.removeMascara(telefone));
        if (dbo.isPresent() && dbo.get().getEmail() != null) {
        	
        	String dados = Mapper.encryptId(Morador.class, dbo.get().getId()) + "|" + new Date().getTime();
        	
        	EmailUtils.EmailMessage mensagem = sendEmail.novaMensagem();
        	mensagem.setSubject(config.getProperty("email.resetmorador.assunto"));

    		String corpo = config.getProperty("email.resetmorador.corpo");
            if (corpo != null) {
                corpo = corpo.replace("#morador#", dbo.get().getNome());
                corpo = corpo.replace("#token#", CryptoUtils.encrypt(dados));
            }
    		mensagem.setBody(corpo);

    		mensagem.setFrom(config.getProperty("email.resetmorador.emailOrigem"));
    		mensagem.setFromName(config.getProperty("email.resetmorador.nomeOrigem"));

    		mensagem.setToName(dbo.get().getNome());
    		mensagem.setTo(dbo.get().getEmail());
        	
    		sendEmail.sendMessage(mensagem);
        	
        	
        	return new ResponseEntity<>(new  SuccessMessage("Enviamos um email para você com as instruções para realizar o reset da sua senha.","Login"), HttpStatus.OK);
        }		
    	return new ResponseEntity<>(new  SuccessMessage("Seu email não está cadastrado. Solicite à central ou a outra pessoa que esteja vinculada ao seu apartamento que troque sua senha.","Login"), HttpStatus.OK);
		
		
	}

	@Transactional
	@PostMapping(value = "/primeiroAcesso")
	public ResponseEntity<RestMoradorLogin> primeiroAcesso(@RequestBody RestMoradorLogin value) throws BusinessRuleException,BusinessSecurityException,BusinessRuleException {
		
        Optional<Morador> dbo = repository.findPrimeiroAcesso(MaskUtils.removeMascara(value.getCep()), value.getApartamento().trim(), MaskUtils.removeMascara(value.getCpf()));
        if (dbo.isPresent() && DateUtils.isEqual(dbo.get().getDataNascimento(), value.getDataNascimento())) {

        	if (dbo.get().getSenha() != null)
        		throw new BusinessRuleException("Morador já realizou o primeiro acesso. Favor entrar em contato com a central ou solicitar uma pessoa do mesmo apartamento que altere sua senha de acesso.");
        	
        	RestMoradorLogin rest = (RestMoradorLogin) mapper.copyToRestObject(dbo.get(), RestMoradorLogin.class);
            return new ResponseEntity<>(rest, HttpStatus.OK);

            
        }
		throw new BusinessRuleException("Dados inválidos.");
	}

	
	@Transactional
	@PostMapping(value = "/definirSenha")
	public ResponseEntity<RestMorador> definirSenha(@RequestBody RestMoradorLogin value) throws NotFoundException,BusinessSecurityException,BusinessRuleException, IOException {
        Optional<Morador> dbo = repository.findById(Mapper.decryptId(Morador.class, value.getId()));
        if (dbo.isPresent()) {

        	if (value.getSenha() == null)
        		throw new BusinessRuleException("Senha deve ser informada");

        	if (value.getSenhaPanico() == null)
        		throw new BusinessRuleException("Senha de pânico deve ser informada");

        	if (value.getSenha().equals(value.getSenhaPanico()))
        		throw new BusinessRuleException("A senha de pânico deve ser diferente da senha de acesso");

        	
            Optional<Morador> dboTelefone = repository.findByTelefoneCelular(MaskUtils.removeMascara(value.getTelefoneCelular()));
        	if (dboTelefone.isPresent() && dboTelefone.get().getTelefoneCelular().equals(MaskUtils.removeMascara(value.getTelefoneCelular())) && !dbo.get().getId().equals(dboTelefone.get().getId()))
        		throw new BusinessRuleException("Esse número de celular já está em uso");


        	dbo.get().setSenha(value.getSenha());
        	dbo.get().setSenhaPanico(value.getSenhaPanico());
        	dbo.get().setTelefoneCelular(MaskUtils.removeMascara(value.getTelefoneCelular()));
        	
        	dbo.get().setFotoFrente(value.getFotoFrente());
        	dbo.get().setFotoEsquerda(value.getFotoEsquerda());
        	dbo.get().setFotoDireita(value.getFotoDireita());
        	dbo.get().setFotoAbaixo(value.getFotoAbaixo());
        	dbo.get().setFotoAcima(value.getFotoAcima());

        	dbo.get().ajustaImagens();
        	
        	repository.save(dbo.get());
        	enviaTreinamento(new String[] {dbo.get().getFotoFrente(),dbo.get().getFotoEsquerda(),dbo.get().getFotoDireita(),dbo.get().getFotoAbaixo(), dbo.get().getFotoAcima()}, dbo.get().getId(), "morador");
        	
        	value.setTelefoneCelular(MaskUtils.removeMascara(dbo.get().getTelefoneCelular()));
        	
        	return login(value);
        	
            
        }
		throw new NotFoundException();
	}

	
	@Transactional
	@PostMapping(value = "/redefinirSenha")
	public ResponseEntity<SuccessMessage> redefinirSenha(@RequestBody RestMoradorLogin value) throws Exception {
		
		String token = CryptoUtils.decrypt(value.getToken());

		long timeout = 15 * 60 * 1000; // 15 minutos
		
		if (Long.parseLong(token.split("\\|")[1]) + timeout < new Date().getTime() )
			throw new BusinessRuleException("Token expirado. Solicite outro email de reset de senha no aplicativo");
		
        Optional<Morador> dbo = repository.findById(Mapper.decryptId(Morador.class, token.split("\\|")[0]));
        if (dbo.isPresent()) {

        	if (value.getSenha() == null)
        		throw new BusinessRuleException("Senha deve ser informada");

        	if (value.getSenhaPanico() == null)
        		throw new BusinessRuleException("Senha de pânico deve ser informada");

        	if (value.getSenha().equals(value.getSenhaPanico()))
        		throw new BusinessRuleException("A senha de pânico deve ser diferente da senha de acesso");

        	repository.save(dbo.get());
        	
        	return new ResponseEntity<>(new SuccessMessage("Senha atualizada com sucesso", "OK"), HttpStatus.OK);
        	
            
        }
		throw new NotFoundException();
	}
	
	@Transactional
	@PostMapping(value = "morador/v1/morador/alterarSenha")
	public ResponseEntity<RestMoradorLogin> alterarSenha(@RequestBody RestMoradorLogin value) throws NotFoundException,BusinessSecurityException,BusinessRuleException, IOException {
        Optional<Morador> dbo = repository.findById(Mapper.decryptId(Morador.class, value.getId()));
        if (dbo.isPresent()) {

        	if (value.getSenha() == null)
        		throw new BusinessRuleException("Senha deve ser informada");

        	if (value.getSenhaPanico() == null)
        		throw new BusinessRuleException("Senha de pânico deve ser informada");

        	if (value.getSenha().equals(value.getSenhaPanico()))
        		throw new BusinessRuleException("A senha de pânico deve ser diferente da senha de acesso");


        	dbo.get().setSenha(value.getSenha());
        	dbo.get().setSenhaPanico(value.getSenhaPanico());
        	
        	dbo.get().getFotoAbaixo();
        	dbo.get().getFotoAcima();
        	dbo.get().getFotoEsquerda();
        	dbo.get().getFotoDireita();
        	dbo.get().getFotoFrente();

        	repository.save(dbo.get());
        	
        	return new ResponseEntity<>(value,HttpStatus.OK);
            
        }
		throw new NotFoundException();
	}

	
	@Transactional
	@PostMapping(value = {"morador/v1/morador/novo"})
	public ResponseEntity<RestMorador> novoMorador(@Valid @RequestBody RestMorador value) throws BusinessRuleException,BusinessSecurityException, IOException {
		
		super.checkApartamento(Mapper.decryptId(Apartamento.class, value.getAptId()));
		
		Optional<Morador> optMor = repository.findByCpf(MaskUtils.removeMascara(value.getCpf()));
		Morador mor = null;
		
		if (optMor.isPresent()) {
			mor = optMor.get();
			mor.setDataAtualizacao(new Date());
			
			for (ApartamentoMorador am : mor.getApartamentoMoradorList()) {
				if (am.getAptId().equals(Mapper.decryptId(Apartamento.class, value.getAptId()))) {
					throw new BusinessRuleException("Morador já está vinculado a esse apartamento");
				}
			}
			
		} else {
			mor = (Morador) mapper.copyToDbObject(value);
			mor.setDataAtualizacao(new Date());
			mor.setDataCriacao(mor.getDataAtualizacao());
			mor.setCpf(MaskUtils.removeMascara(value.getCpf()));
			mor.setTelefoneCelular(MaskUtils.removeMascara(value.getTelefoneCelular()));
			mor.setMapeado(false);
		}

		// Se eh o primeiro morador desse apartamento, define como propr
		boolean proprietario = value.getProprietario();
		if (repositoryAptoMorador.countByAptId(Mapper.decryptId(Apartamento.class, value.getAptId())) == 0) {
			proprietario = Constantes.SIM;
		}
		
		
		ApartamentoMorador aptMor = new ApartamentoMorador();
		aptMor.setMorador(mor);
		aptMor.setDataCriacao(new Date());
		aptMor.setProprietario(proprietario);
		aptMor.setDataAtualizacao(aptMor.getDataCriacao());
		aptMor.setAptId(Mapper.decryptId(Apartamento.class, value.getAptId()));
		mor.getApartamentoMoradorList().add(aptMor);
		
        if (mor.getSenha() != null && mor.getSenha().equals(mor.getSenhaPanico())) {
        	throw new BusinessRuleException("A senha de pânico deve ser diferente da senha de acesso");
        }

        mor.ajustaImagens();
        
        
        repository.save(mor);
    	enviaTreinamento(new String[] {mor.getFotoFrente(),mor.getFotoEsquerda(),mor.getFotoDireita(),mor.getFotoAbaixo(),mor.getFotoAcima()},mor.getId(), "morador");

        return new ResponseEntity<>((RestMorador) mapper.copyToRestObject(mor, RestMorador.class),  HttpStatus.OK);
	}

	
	@Transactional
	@PostMapping(value = {"admin/v1/morador/novo", "central/v1/morador/novo"})
	public ResponseEntity<RestMorador> novo(@Valid @RequestBody RestMorador value) throws BusinessRuleException,BusinessSecurityException, IOException {

		for (RestApartamentoMorador ram : new HashSet<RestApartamentoMorador>(value.getListApartamentosCondominio())) {
			if (ram.getAptId() == null) {
				value.getListApartamentosCondominio().remove(ram);
			}
		}
		
		if(value.getListApartamentosCondominio() == null || value.getListApartamentosCondominio().isEmpty())
			throw new BusinessRuleException("O morador deve possuir ao menos um apartamento");
		
		
		Optional<Morador> optMor = repository.findByCpf(MaskUtils.removeMascara(value.getCpf()));
		Morador mor = null;
		if (optMor.isPresent()) {
			mor = optMor.get();
			mor.setDataAtualizacao(new Date());
			mor.setDataNascimento(value.getDataNascimento());
			mor.setEmail(value.getEmail());
			mor.setNome(value.getNome());
			mor.setTelefoneCelular(MaskUtils.removeMascara(value.getTelefoneCelular()));
		} else {
			mor = (Morador) mapper.copyToDbObject(value);
			mor.setDataAtualizacao(new Date());
			mor.setDataCriacao(mor.getDataAtualizacao());
			mor.setCpf(MaskUtils.removeMascara(value.getCpf()));
			mor.setTelefoneCelular(MaskUtils.removeMascara(value.getTelefoneCelular()));
			mor.setMapeado(false);
		}
		
        // Sincroniza Aptos do condominio
    	Long condId = Mapper.decryptId(Condominio.class, value.getConId());
    	List<ApartamentoMorador> listAptoMorador = this.repositoryAptoMorador.findByConIdAndMorId(condId, mor.getId());
    	
    	for (ApartamentoMorador am : new HashSet<ApartamentoMorador>(listAptoMorador)) {
    		boolean encontrou = false;
    		for (RestApartamentoMorador ram : value.getListApartamentosCondominio()) {
        		if (!am.getApartamento().getBloco().getConId().equals(condId)) { // Se eh de outro condominio, nao faz nada com o apartamento
        			encontrou = true;
        			break;
        		} else if (ram != null && ram.getId() == null) { // Se eh novo apartamento nao faz nada
        			encontrou = true;
        			break;
        		} else if (ram != null && am.getId().equals(Mapper.decryptId(ApartamentoMorador.class, ram.getId()))) {
        			encontrou = true;
        			am.setProprietario(ram.getProprietario());
        			if (am.getProprietario() == null) am.setProprietario(false);
        			am.setDataAtualizacao(new Date());
        			break;
        		}
    		}
    		if (!encontrou) {
    			mor.getApartamentoMoradorList().remove(am);
    			repositoryAptoMorador.delete(am);
    		}
    	}
    	
		for (RestApartamentoMorador ram : value.getListApartamentosCondominio()) {
    		if (ram != null && ram.getId() == null) { // Se eh novo apartamento adiciona
    			ApartamentoMorador am = (ApartamentoMorador)mapper.copyToDbObject(ram);
    			am.setDataAtualizacao(new Date());
    			am.setDataCriacao(am.getDataAtualizacao());
    			am.setMorador(mor);
    			mor.getApartamentoMoradorList().add(am);
    		}
		}
		
		
		if (value.getFotos() != null) {
			if (value.getFotos().length > 0) 
        		mor.setFotoFrente(value.getFotos()[0]);
			if (value.getFotos().length > 1) 
				mor.setFotoEsquerda(value.getFotos()[1]);
			if (value.getFotos().length > 2) 
				mor.setFotoDireita(value.getFotos()[2]);
			if (value.getFotos().length > 3) 
				mor.setFotoAcima(value.getFotos()[3]);
			if (value.getFotos().length > 4) 
				mor.setFotoAbaixo(value.getFotos()[4]);
		}

		mor.ajustaImagens();
		
        repository.save(mor);
    	enviaTreinamento(new String[] {mor.getFotoFrente(),mor.getFotoEsquerda(),mor.getFotoDireita(),mor.getFotoAbaixo(), mor.getFotoAcima()}, mor.getId(), "morador");

    	return new ResponseEntity<>((RestMorador) mapper.copyToRestObject(mor, RestMorador.class),  HttpStatus.OK);
	}

	
	@Transactional
	@PutMapping(value = {"morador/v1/morador/atualiza"})
	public ResponseEntity<Integer> atualizaMorador(@Valid @RequestBody RestMorador value) throws BusinessRuleException, NotFoundException, BusinessSecurityException, IOException {

		super.checkApartamento(Mapper.decryptId(Apartamento.class, value.getAptId()));


		if (value.getId() != null) {
            Long id = Mapper.decryptId(Morador.class, value.getId());
            Optional<Morador> dbo = repository.findById(id);
            
            if (dbo.isPresent()) {

            	String senha = dbo.get().getSenha();
            	String senhaPanico = dbo.get().getSenhaPanico();
            	
            	dbo.get().getFotoAbaixo();
            	dbo.get().getFotoAcima();
            	dbo.get().getFotoEsquerda();
            	dbo.get().getFotoDireita();
            	dbo.get().getFotoFrente();
                
                mapper.copyToDbObject(value, dbo.get());
                dbo.get().setDataAtualizacao(new Date());
                
                if (dbo.get().getSenha() == null)
                	dbo.get().setSenha(senha);

                if (dbo.get().getSenhaPanico() == null)
                	dbo.get().setSenhaPanico(senhaPanico);

                if (dbo.get().getSenha() != null && dbo.get().getSenha().equals(dbo.get().getSenhaPanico())) {
                	throw new BusinessRuleException("A senha de pânico deve ser diferente da senha de acesso");
                }
                
                
    			dbo.get().setTelefoneCelular(MaskUtils.removeMascara(value.getTelefoneCelular()));

    			for (ApartamentoMorador am : dbo.get().getApartamentoMoradorList()) {
    				if (am.getAptId().equals(Mapper.decryptId(Apartamento.class, value.getAptId()))) {
    					am.setProprietario(value.getProprietario());

						boolean temUmProprietario = false;
    					for (ApartamentoMorador amTodos : am.getApartamento().getApartamentoMoradorList()) {
    						
    	    				if (amTodos.getProprietario() == Constantes.SIM) {
    	    					temUmProprietario = true;
    	    					break;
    	    				}
    	    			}
    					
    					if (!temUmProprietario)
    						throw new BusinessRuleException("Apartamento deve ter ao menos um proprietário");
    					
    					break;
    				
    				}
    			}


    			dbo.get().ajustaImagens();
    			
                repository.save(dbo.get());
            	enviaTreinamento(new String[] {dbo.get().getFotoFrente(),dbo.get().getFotoEsquerda(),dbo.get().getFotoDireita(),dbo.get().getFotoAbaixo(), dbo.get().getFotoAcima()}, dbo.get().getId(), "morador");

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}

	@Transactional
	@PutMapping(value = {"admin/v1/morador/atualiza","central/v1/morador/atualiza"})
	public ResponseEntity<Integer> atualiza(@Valid @RequestBody RestMorador value) throws BusinessRuleException, NotFoundException, BusinessSecurityException, IOException {



		if (value.getId() != null) {

			for (RestApartamentoMorador ram : new HashSet<RestApartamentoMorador>(value.getListApartamentosCondominio())) {
				if (ram.getAptId() == null) {
					value.getListApartamentosCondominio().remove(ram);
				}
			}

			if(value.getListApartamentosCondominio() == null || value.getListApartamentosCondominio().isEmpty())
				throw new BusinessRuleException("O morador deve possuir ao menos um apartamento");

			Long id = Mapper.decryptId(Morador.class, value.getId());
            Optional<Morador> dbo = repository.findById(id);
            if (dbo.isPresent()) {
            	
            	String senha = dbo.get().getSenha();
            	String senhaPanico = dbo.get().getSenhaPanico();
                
                mapper.copyToDbObject(value, dbo.get());
                dbo.get().setDataAtualizacao(new Date());
                
                if (dbo.get().getSenha() == null)
                	dbo.get().setSenha(senha);

                if (dbo.get().getSenhaPanico() == null)
                	dbo.get().setSenhaPanico(senhaPanico);
    			
                if (dbo.get().getSenha() != null && dbo.get().getSenha().equals(dbo.get().getSenhaPanico())) {
                	throw new BusinessRuleException("A senha de pânico deve ser diferente da senha de acesso");
                }

                dbo.get().setTelefoneCelular(MaskUtils.removeMascara(value.getTelefoneCelular()));

                // Sincroniza Aptos do condominio
            	Long condId = Mapper.decryptId(Condominio.class, value.getConId());
            	List<ApartamentoMorador> listAptoMorador = this.repositoryAptoMorador.findByConIdAndMorId(condId, dbo.get().getId());

            	for (ApartamentoMorador am : new HashSet<ApartamentoMorador>(listAptoMorador)) {
            		boolean encontrou = false;
            		for (RestApartamentoMorador ram : value.getListApartamentosCondominio()) {
	            		if (!am.getApartamento().getBloco().getConId().equals(condId)) { // Se eh de outro condominio, nao faz nada com o apartamento
	            			encontrou = true;
	            			break;
	            		} else if (ram != null && ram.getId() == null) { // Se eh novo apartamento nao faz nada
	            			encontrou = true;
	            			break;
	            		} else if (ram != null && am.getId().equals(Mapper.decryptId(ApartamentoMorador.class, ram.getId()))) {
	            			encontrou = true;
	            			am.setProprietario(ram.getProprietario());
	            			if (am.getProprietario() == null) am.setProprietario(false);
	            			am.setDataAtualizacao(new Date());
	            			break;
	            		}
            		}
            		if (!encontrou) {
            			dbo.get().getApartamentoMoradorList().remove(am);
            			repositoryAptoMorador.delete(am);
            		}
            	}

            	
        		for (RestApartamentoMorador ram : value.getListApartamentosCondominio()) {
            		if (ram != null && ram.getId() == null && ram.getAptId() != null) { // Se eh novo apartamento adiciona
            			ApartamentoMorador am = (ApartamentoMorador)mapper.copyToDbObject(ram);
            			am.setDataAtualizacao(new Date());
            			am.setDataCriacao(am.getDataAtualizacao());
            			am.setMorador(dbo.get());
            			dbo.get().getApartamentoMoradorList().add(am);
            			
            		}
        		}

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
            	
                repository.save(dbo.get());
            	enviaTreinamento(new String[] {dbo.get().getFotoFrente(),dbo.get().getFotoEsquerda(),dbo.get().getFotoDireita(),dbo.get().getFotoAbaixo(), dbo.get().getFotoAcima()}, dbo.get().getId(), "morador");

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}

	
	@Transactional
	@DeleteMapping(value = {"morador/v1/morador/remove/{apartamento}/{id}"})
	public ResponseEntity<Integer> removeMorador(@PathVariable String apartamento, @PathVariable String id) throws NotFoundException, BusinessSecurityException, BusinessRuleException {

		super.checkApartamento(Mapper.decryptId(Apartamento.class, apartamento));

		
		if (id != null) {
            Optional<Morador> dbo = repository.findById(Mapper.decryptId(Morador.class, id));
            if (dbo.isPresent()) {
            	
            	if (dbo.get().getId() == super.getIdUsuario() && super.hasPermission(Constantes.ROLE_MORADOR)) {
            		throw new BusinessRuleException("O morador não pode excluir a si mesmo");
            	} 
            	
    			for (ApartamentoMorador am : dbo.get().getApartamentoMoradorList()) {
    				if (am.getAptId().equals(Mapper.decryptId(Apartamento.class, apartamento))) {

						int quantidadeProprietario = 0;
    					for (ApartamentoMorador amTodos : am.getApartamento().getApartamentoMoradorList()) {
    						
    	    				if (amTodos.getProprietario().booleanValue() == Constantes.SIM) {
    	    					quantidadeProprietario++;
    	    				}
    	    			}
    					
    					if (quantidadeProprietario == 1 && am.getProprietario().booleanValue() == Constantes.SIM)
    						throw new BusinessRuleException("Apartamento deve ter ao menos um proprietário");
    					
    					
    					am.getApartamento().getApartamentoMoradorList().remove(am);
    					am.getMorador().getApartamentoMoradorList().remove(am);
    					am.setAptId(null);
    					am.setMorador(null);
    	                repositoryAptoMorador.delete(am);
    	                return new ResponseEntity<>(HttpStatus.OK);
    				}
    			}
            }
        }
		throw new NotFoundException();
	}

	@Transactional
	@DeleteMapping(value = {"admin/v1/morador/remove/{condominio}/{id}","central/v1/morador/remove/{condominio}/{id}"})
	public ResponseEntity<Integer> remove(@PathVariable String condominio, @PathVariable String id) throws NotFoundException, BusinessSecurityException, BusinessRuleException {

		
		if (id != null) {
            Optional<Morador> dbo = repository.findById(Mapper.decryptId(Morador.class, id));
            if (dbo.isPresent()) {
            	if (dbo.get().getId() == super.getIdUsuario() && super.hasPermission(Constantes.ROLE_MORADOR)) {
            		throw new BusinessRuleException("O morador não pode excluir a si mesmo");
            	} 
            	
    			for (ApartamentoMorador am : new HashSet<ApartamentoMorador>(dbo.get().getApartamentoMoradorList())) {
    				if (am.getApartamento().getBloco().getCondominio().getId().equals(Mapper.decryptId(Condominio.class, condominio))) {
    					
    					am.getApartamento().getApartamentoMoradorList().remove(am);
    					am.getMorador().getApartamentoMoradorList().remove(am);
    					am.setAptId(null);
    					am.setMorador(null);
    	                repositoryAptoMorador.delete(am);
    	                
    				}
    			}
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
		throw new NotFoundException();
	}

	
	@GetMapping(value = {"morador/v1/morador/recupera/{apartamento}/{id}"})
	public ResponseEntity<RestMorador> recuperaMorador(@PathVariable String apartamento, @PathVariable String id) throws NotFoundException, BusinessSecurityException, BusinessRuleException {
		
		super.checkApartamento(Mapper.decryptId(Apartamento.class, apartamento));
		
		Optional<Morador> dbo = repository.findById(Mapper.decryptId(Morador.class, id));
        if (dbo != null && dbo.isPresent()) {

        	dbo.get().getFotoFrente();
        	dbo.get().getFotoDireita();
        	dbo.get().getFotoEsquerda();
        	dbo.get().getFotoAbaixo();
        	dbo.get().getFotoAcima();

            RestMorador rest = (RestMorador) mapper.copyToRestObject(dbo.get(), RestMorador.class);

            
            if (!dbo.get().getId().equals(super.getIdUsuario())) {
            	rest.setPermiteExclusao(Constantes.SIM);
            } else {
            	rest.setPermiteExclusao(Constantes.NAO);
            }
            
			for (ApartamentoMorador am : dbo.get().getApartamentoMoradorList()) {
				if (am.getAptId().equals(Mapper.decryptId(Apartamento.class, apartamento))) {
					rest.setProprietario(am.getProprietario());
					break;
				}
			}
            
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = {"morador/v1/morador/recuperaDados/{apartamento}"})
	public ResponseEntity<RestMorador> recuperaDadosMorador(@PathVariable String apartamento) throws NotFoundException, BusinessSecurityException, BusinessRuleException {
		
		super.checkApartamento(Mapper.decryptId(Apartamento.class, apartamento));
		
		Optional<Morador> dbo = repository.findById(super.getIdUsuario());
        if (dbo.isPresent()) {

            RestMorador rest = (RestMorador) mapper.copyToRestObject(dbo.get(), RestMorador.class);

            
            if (!dbo.get().getId().equals(super.getIdUsuario())) {
            	rest.setPermiteExclusao(Constantes.SIM);
            } else {
            	rest.setPermiteExclusao(Constantes.NAO);
            }
            
			for (ApartamentoMorador am : dbo.get().getApartamentoMoradorList()) {
				if (am.getAptId().equals(Mapper.decryptId(Apartamento.class, apartamento))) {
					rest.setProprietario(am.getProprietario());
					break;
				}
			}
            
            
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}
	
	
	@GetMapping(value = {"admin/v1/morador/recupera/{condominio}/{id}","central/v1/morador/recupera/{condominio}/{id}"})
	public ResponseEntity<RestMorador> recupera(@PathVariable String condominio, @PathVariable String id) throws NotFoundException, BusinessSecurityException, BusinessRuleException {
		
		
		Optional<Morador> dbo = repository.findById(Mapper.decryptId(Morador.class, id));
        if (dbo != null && dbo.isPresent()) {
            
        	RestMorador rest = (RestMorador) mapper.copyToRestObject(dbo.get(), RestMorador.class);
        	Iterable<ApartamentoMorador> listAptoMorador = this.repositoryAptoMorador.findByConIdAndMorId(Mapper.decryptId(Condominio.class, condominio), dbo.get().getId());
        	rest.setListApartamentosCondominio(mapper.copyToRestObject(listAptoMorador,RestApartamentoMorador.class));
        	rest.setConId(condominio);
        	
        	String[] fotos = new String[5];
        	rest.setFotoFrente(null);
        	rest.setFotoDireita(null);
        	rest.setFotoEsquerda(null);
        	rest.setFotoAbaixo(null);
        	rest.setFotoAcima(null);
        	
        	fotos[0] = dbo.get().getFotoFrente();
        	fotos[1] = dbo.get().getFotoEsquerda();
        	fotos[2] = dbo.get().getFotoDireita();
        	fotos[3] = dbo.get().getFotoAcima();
        	fotos[4] = dbo.get().getFotoAbaixo();
        	
        	rest.setFotos(fotos);
        	
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
		throw new NotFoundException();
	}

	@GetMapping(value = {"admin/v1/morador/retreina/{condominio}"})
	public ResponseEntity<String> retreina(@PathVariable String condominio)  {
		
		Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
					retreinaThread(Mapper.decryptId(Condominio.class, condominio));
				} catch (BusinessSecurityException e) {
					logger.debug(e.getMessage(), e);
				};
            }
        };

        Thread th = new Thread(runnable);
        th.start();
		
        
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}


	private void retreinaThread(Long idCondominio) {
		List<Morador> lstMor = repository.findByConId(idCondominio);
		
		
		for (Morador m : lstMor) {
			try {
				enviaTreinamento(new String[] {m.getFotoFrente(),m.getFotoEsquerda(),m.getFotoDireita(),m.getFotoAbaixo(), m.getFotoAcima()}, m.getId(), "morador");
	        	m.setFotoFrente(null);
				m.setFotoAbaixo(null);
				m.setFotoAcima(null);
				m.setFotoDireita(null);
				m.setFotoEsquerda(null);
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
			}
		}
		
	}
	
	
	@GetMapping(value = {"morador/v1/morador/lista/{apartamento}"})
	public ResponseEntity<List<RestMorador>> listaMorador(@PathVariable String apartamento) throws BusinessSecurityException, BusinessRuleException, IOException {

		checkApartamento(Mapper.decryptId(Apartamento.class, apartamento));

		mapper.setMaxLevel(1);
        
        Iterable<ApartamentoMorador> itDbo = repositoryAptoMorador.findByAptId(Mapper.decryptId(Apartamento.class, apartamento));
        
        List<RestMorador> restList = new ArrayList<>();
        for (ApartamentoMorador aptMor : itDbo) {
        	
        	RestMorador restMor = (RestMorador) mapper.copyToRestObject(aptMor.getMorador(),RestMorador.class);
        	restMor.setProprietario(aptMor.getProprietario());
        	restMor.setFotoAbaixo(null);
        	restMor.setFotoAcima(null);
        	restMor.setFotoFrente(null);
        	restMor.setFotoEsquerda(null);
        	restMor.setFotoDireita(null);
        	
        	restList.add(restMor);
        }
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}

	@GetMapping(value = {"admin/v1/morador/lista/{condominio}","central/v1/morador/lista/{condominio}"})
	public ResponseEntity<List<RestMorador>> lista(@PathVariable String condominio) throws BusinessSecurityException, BusinessRuleException {

		mapper.setMaxLevel(1);
        
        Iterable<Morador> itDbo = repository.findByConId(Mapper.decryptId(Condominio.class, condominio));
        
        List<RestMorador> restList = new ArrayList<>();
        for (Morador mor : itDbo) {
        	RestMorador restMor = (RestMorador) mapper.copyToRestObject(mor,RestMorador.class);
        	restMor.setFotoAbaixo(null);
        	restMor.setFotoAcima(null);
        	restMor.setFotoFrente(null);
        	restMor.setFotoEsquerda(null);
        	restMor.setFotoDireita(null);
        	
        	restList.add(restMor);
        }
        
        return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	
	
	private void enviaTreinamento(String[] fotos, Long id, String tipo) throws BusinessRuleException {
		
		logger.debug("Enviando morador para treinamento " + id);
		
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


