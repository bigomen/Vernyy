package com.luxfacta.vernyy.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.luxfacta.vernyy.api.model.ApartamentoFuncionario;
import com.luxfacta.vernyy.api.model.ApartamentoVisitante;
import com.luxfacta.vernyy.api.model.Area;
import com.luxfacta.vernyy.api.model.Morador;
import com.luxfacta.vernyy.api.repository.ApartamentoFuncionarioRepository;
import com.luxfacta.vernyy.api.repository.ApartamentoVisitanteRepository;
import com.luxfacta.vernyy.api.repository.AreaRepository;
import com.luxfacta.vernyy.api.repository.MoradorRepository;
import com.luxfacta.vernyy.api.shared.ImageUtils;

@RestController
public class DownloadImagemController {

	@Autowired
	private MoradorRepository repositoryMorador;

	@Autowired
	private ApartamentoVisitanteRepository repositoryApartamentoVisitante;

	@Autowired
	private ApartamentoFuncionarioRepository repositoryApartamentoFuncionario;

	@Autowired
	private AreaRepository repositoryArea;

	@GetMapping(
			value = "morador/v1/{entidade}/download/{largura}/{altura}/{id}.png", 
			produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] downlaod(@PathVariable String entidade, @PathVariable int largura, @PathVariable int altura, @PathVariable String id)  {
		
		String imagem = new String();
		
		try {

			if ("MORADOR".equalsIgnoreCase(entidade)) {
				Optional<Morador> oMor = repositoryMorador.findById(Mapper.decryptId(Morador.class, id));
				if (oMor.isPresent()) {
					imagem = oMor.get().getFotoFrente();
				}
			} else if ("VISITANTE".equalsIgnoreCase(entidade)) {
				Optional<ApartamentoVisitante> oVis = repositoryApartamentoVisitante.findById(Mapper.decryptId(ApartamentoVisitante.class, id));
				if (oVis.isPresent()) {
					imagem = oVis.get().getFoto();
				}
			} else if ("FUNCIONARIO".equalsIgnoreCase(entidade)) {
				Optional<ApartamentoFuncionario> oFun = repositoryApartamentoFuncionario.findById(Mapper.decryptId(ApartamentoFuncionario.class, id));
				if (oFun.isPresent()) {
					imagem = oFun.get().getFotoFrente();
				}
			} else if ("AREA".equalsIgnoreCase(entidade)) {
				Optional<Area> oAre = repositoryArea.findById(Mapper.decryptId(Area.class, id));
				if (oAre.isPresent()) {
					imagem = oAre.get().getFoto();
				}
			}			
			
			 byte[] retorno = ImageUtils.base64ToJPEG(imagem, largura, altura);
			 
			 return retorno;

		} catch (Exception ex) {
			return new byte[0];
		}
	}
	
	
	
}
