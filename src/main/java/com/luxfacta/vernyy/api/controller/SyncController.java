package com.luxfacta.vernyy.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.model.ApartamentoMorador;
import com.luxfacta.vernyy.api.model.Area;
import com.luxfacta.vernyy.api.model.Condominio;
import com.luxfacta.vernyy.api.model.Funcionario;
import com.luxfacta.vernyy.api.model.Morador;
import com.luxfacta.vernyy.api.model.SyncDeletado;
import com.luxfacta.vernyy.api.model.SyncPickleFuncionario;
import com.luxfacta.vernyy.api.model.SyncPickleMorador;
import com.luxfacta.vernyy.api.model.SyncPickleVisitante;
import com.luxfacta.vernyy.api.model.Veiculo;
import com.luxfacta.vernyy.api.model.Visitante;
import com.luxfacta.vernyy.api.repository.AreaRepository;
import com.luxfacta.vernyy.api.repository.FuncionarioRepository;
import com.luxfacta.vernyy.api.repository.MoradorRepository;
import com.luxfacta.vernyy.api.repository.SyncDeletadoRepository;
import com.luxfacta.vernyy.api.repository.SyncPickleFuncionarioRepository;
import com.luxfacta.vernyy.api.repository.SyncPickleMoradorRepository;
import com.luxfacta.vernyy.api.repository.SyncPickleVisitanteRepository;
import com.luxfacta.vernyy.api.repository.VeiculoRepository;
import com.luxfacta.vernyy.api.repository.VisitanteRepository;
import com.luxfacta.vernyy.api.rest.RestDadosPickle;
import com.luxfacta.vernyy.api.rest.RestSyncCondominioApartamentoMorador;
import com.luxfacta.vernyy.api.rest.RestSyncCondominioArea;
import com.luxfacta.vernyy.api.rest.RestSyncCondominioFuncionario;
import com.luxfacta.vernyy.api.rest.RestSyncCondominioMorador;
import com.luxfacta.vernyy.api.rest.RestSyncCondominioVeiculo;
import com.luxfacta.vernyy.api.rest.RestSyncCondominioVisitante;
import com.luxfacta.vernyy.api.rest.RestSyncDeletado;
import com.luxfacta.vernyy.api.rest.RestSyncPickleFuncionario;
import com.luxfacta.vernyy.api.rest.RestSyncPickleMorador;
import com.luxfacta.vernyy.api.rest.RestSyncPickleVisitante;


@RestController
public class SyncController {

	
	
    @Autowired
    private Environment env;
    
	@Autowired
	private VisitanteRepository repositoryVisitante;

	
	@Autowired
	private MoradorRepository repositoryMorador;
	
	@Autowired
	private VeiculoRepository repositoryVeiculo;
	
	@Autowired
	private AreaRepository repositoryArea;

	
	@Autowired
	private FuncionarioRepository repositoryFuncionario;

	@Autowired
	private SyncPickleMoradorRepository repositorySyncPickleMorador;
	
	@Autowired
	private SyncPickleVisitanteRepository repositorySyncPickleVisitante;
	
	@Autowired
	private SyncPickleFuncionarioRepository repositorySyncPickleFuncionario;
	
	@Autowired
	private SyncDeletadoRepository repositoryDeletado;

	protected final Mapper mapper = new Mapper();

	private Logger logger = LoggerFactory.getLogger(SyncController.class);


	@PostMapping(value = "/sync/visitante/pickle")
	public ResponseEntity<Object> visitante(@RequestBody RestDadosPickle value) {

		logger.debug("Recebeu treinamento visitante " + value.getId());

		Optional<Visitante> optVis = repositoryVisitante.findById(value.getId());
		if (optVis.isPresent()) {
			
			Visitante vis = optVis.get();
			vis.setPickle(value.getPickle());
			repositoryVisitante.save(vis);
			
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/sync/morador/pickle")
	public ResponseEntity<Object> morador(@RequestBody RestDadosPickle value) {
		
		logger.debug("Recebeu treinamento morador " + value.getId());
		
		
		Optional<Morador> optVis = repositoryMorador.findById(value.getId());
		if (optVis.isPresent()) {
			
			Morador vis = optVis.get();
			vis.setPickle(value.getPickle());
			repositoryMorador.save(vis);
			
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@PostMapping(value = "/sync/funcionario/pickle")
	public ResponseEntity<Object> funcionario(@RequestBody RestDadosPickle value) {

		logger.debug("Recebeu treinamento funcionario " + value.getId());

		Optional<Funcionario> optVis = repositoryFuncionario.findById(value.getId());
		if (optVis.isPresent()) {
			
			Funcionario vis = optVis.get();
			vis.setPickle(value.getPickle());
			repositoryFuncionario.save(vis);
			
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	@GetMapping(value = "/sync/condominio/morador/{id}/{data}")
	public ResponseEntity<List<RestSyncCondominioMorador>> condominioMorador(@PathVariable String id, @PathVariable Long data) throws BusinessSecurityException {
		mapper.setMaxLevel(1);
		
		Long condId = Mapper.decryptId(Condominio.class, id);
		Date dt = new Date(data); 

		Iterable<Morador> lstMorador = repositoryMorador.findSyncByConId(condId, dt);
		
		
		List<RestSyncCondominioMorador> restList =  new ArrayList<>();

		mapper.setMaxLevel(3);
		for (Morador a : lstMorador) {
			
			List<ApartamentoMorador> amList = new ArrayList<>();
			for (ApartamentoMorador am : a.getApartamentoMoradorList()) {
				am.getApartamento().getBloco();
				if (am.getApartamento().getBloco().getCondominio().getId().longValue() == condId.longValue()) {
					amList.add(am);
				}
			}
			
			mapper.setMaxLevel(1);
			RestSyncCondominioMorador ra = (RestSyncCondominioMorador)mapper.copyToRestObject(a, RestSyncCondominioMorador.class);
			mapper.setMaxLevel(4);
			for (ApartamentoMorador am : amList) {
				ra.getApartamentoMoradorList().add((RestSyncCondominioApartamentoMorador) mapper.copyToRestObject(am, RestSyncCondominioApartamentoMorador.class));
			}
			restList.add(ra);
		}
		return new ResponseEntity<>(restList, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/sync/condominio/inicio")
	public ResponseEntity<Object> inicioSync() {
		Long dt = System.currentTimeMillis();
		return new ResponseEntity<>(dt, HttpStatus.OK);
	}
	
	@GetMapping(value = "/sync/condominio/veiculo/{id}/{data}")
	public ResponseEntity<List<RestSyncCondominioVeiculo>> condominioVeiculo(@PathVariable String id, @PathVariable Long data) throws BusinessSecurityException {
		mapper.setMaxLevel(1);
		Long condId = Mapper.decryptId(Condominio.class, id);
		Date dt = new Date(data);
		
		Iterable<Veiculo> lstVeiculo = repositoryVeiculo.findSyncByConId(condId, dt);
		
		List<RestSyncCondominioVeiculo> restList = new ArrayList<>();
		
		for (Veiculo a: lstVeiculo) {
			mapper.setMaxLevel(4);
			a.getApartamento().getBloco();
			RestSyncCondominioVeiculo ra = (RestSyncCondominioVeiculo)mapper.copyToRestObject(a, RestSyncCondominioVeiculo.class);
			restList.add(ra);
		}
		
		return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	@GetMapping(value = "sync/condominio/visitante/{id}/{data}")
	public ResponseEntity<List<RestSyncCondominioVisitante>> condominioVisitante(@PathVariable String id, @PathVariable Long data) throws BusinessSecurityException {
		mapper.setMaxLevel(1);
		Long condId = Mapper.decryptId(Condominio.class, id);
		Date dt = new Date(data);
		
		Iterable<Visitante> lstVisitante = repositoryVisitante.findSyncByConId(condId, dt);
		
		List<RestSyncCondominioVisitante> restList = new ArrayList<>();
		
		for (Visitante a: lstVisitante) {
			mapper.setMaxLevel(5);
			a.getApartamentoVisitanteList();
			RestSyncCondominioVisitante ra = (RestSyncCondominioVisitante)mapper.copyToRestObject(a, RestSyncCondominioVisitante.class);
			restList.add(ra);
		}
		
		return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	@GetMapping(value = "sync/condominio/area/{id}/{data}")
	public ResponseEntity<List<RestSyncCondominioArea>> condominioArea(@PathVariable String id, @PathVariable Long data) throws BusinessSecurityException {
		mapper.setMaxLevel(2);
		Long conId = Mapper.decryptId(Condominio.class, id);
		Date dt = new Date(data);
		
		Iterable<Area> lstArea = repositoryArea.findSyncByConId(conId, dt);
		
		List<RestSyncCondominioArea> restList = new ArrayList<>();
		
		for (Area a: lstArea) {
			mapper.setMaxLevel(5);
			a.getAreaAgendamentoList();
			a.getTipoArea().getDescricao();
			a.getTipoEquipamento().getDescricao();
			a.getCameraList();
			a.getCondominio().getNome();
			RestSyncCondominioArea ra = (RestSyncCondominioArea)mapper.copyToRestObject(a, RestSyncCondominioArea.class);
			restList.add(ra);
		}
		return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	@GetMapping(value = "sync/condominio/funcionario/{id}/{data}")
	public ResponseEntity<List<RestSyncCondominioFuncionario>> condominioFuncionario(@PathVariable String id, @PathVariable Long data) throws BusinessSecurityException {
		mapper.setMaxLevel(2);
		Long conId = Mapper.decryptId(Condominio.class, id);
		Date dt = new Date(data);
		
		Iterable<Funcionario> lstFuncionario = repositoryFuncionario.findSyncByConId(conId, dt);
		
		List<RestSyncCondominioFuncionario> restList = new ArrayList<>();
		
		for (Funcionario a: lstFuncionario) {
			mapper.setMaxLevel(5);
			a.getApartamentoFuncionarioList();
			a.getCondominioFuncionarioList();
			RestSyncCondominioFuncionario ra = (RestSyncCondominioFuncionario)mapper.copyToRestObject(a, RestSyncCondominioFuncionario.class);
			restList.add(ra);
		}
		return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	@GetMapping(value = "sync/condominio/pickle/morador/{id}/{data}")
	public ResponseEntity<List<RestSyncPickleMorador>> condominioMoradorPickle(@PathVariable String id, @PathVariable Long data) throws BusinessSecurityException {
		mapper.setMaxLevel(1);
		Long conId = Mapper.decryptId(Condominio.class, id);
		Date dt = new Date(data);
		
		Iterable<SyncPickleMorador> lstMorador = repositorySyncPickleMorador.findByConId(conId, dt);
		
		List<RestSyncPickleMorador> restList = new ArrayList<>();
		
		for (SyncPickleMorador a: lstMorador) {
			a.getPickle();
			RestSyncPickleMorador ra = (RestSyncPickleMorador)mapper.copyToRestObject(a, RestSyncPickleMorador.class);
			restList.add(ra);
		}
		return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	@GetMapping(value = "sync/condominio/pickle/visitante/{id}/{data}")
	public ResponseEntity<List<RestSyncPickleVisitante>> condominioVisitantePickle(@PathVariable String id, @PathVariable Long data) throws BusinessSecurityException {
		mapper.setMaxLevel(1);
		Long conId = Mapper.decryptId(Condominio.class, id);
		Date dt = new Date(data);
		
		Iterable<SyncPickleVisitante> lstVisitante = repositorySyncPickleVisitante.findByConId(conId, dt);
		
		List<RestSyncPickleVisitante> restList = new ArrayList<>();
		
		for (SyncPickleVisitante a: lstVisitante) {
			a.getPickle();
			RestSyncPickleVisitante ra = (RestSyncPickleVisitante)mapper.copyToRestObject(a, RestSyncPickleVisitante.class);
			restList.add(ra);
		}
		return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	@GetMapping(value = "sync/condominio/pickle/funcionario/{id}/{data}")
	public ResponseEntity<List<RestSyncPickleFuncionario>> condominioFuncionarioPickle(@PathVariable String id, @PathVariable Long data) throws BusinessSecurityException {
		mapper.setMaxLevel(1);
		Long conId = Mapper.decryptId(Condominio.class, id);
		Date dt = new Date(data);
		
		Iterable<SyncPickleFuncionario> lstFuncionario = repositorySyncPickleFuncionario.findByConId(conId, dt);
		
		List<RestSyncPickleFuncionario> restList = new ArrayList<>();
		
		for (SyncPickleFuncionario a: lstFuncionario) {
			a.getPickle();
			RestSyncPickleFuncionario ra = (RestSyncPickleFuncionario)mapper.copyToRestObject(a, RestSyncPickleFuncionario.class);
			restList.add(ra);
		}
		return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
	@GetMapping(value = "sync/condominio/deletado/{data}")
	public ResponseEntity<List<RestSyncDeletado>> condominioDeletado(@PathVariable Long data) throws BusinessSecurityException {
		mapper.setMaxLevel(1);
		Date dt = new Date(data);
		
		Iterable<SyncDeletado> lstDeletado = repositoryDeletado.findByData(dt);
		
		List<RestSyncDeletado> restList = new ArrayList<>();
		
		for (SyncDeletado a: lstDeletado) {
			RestSyncDeletado ra = (RestSyncDeletado)mapper.copyToRestObject(a, RestSyncDeletado.class);
			restList.add(ra);
		}
		return new ResponseEntity<>(restList, HttpStatus.OK);
	}
	
}