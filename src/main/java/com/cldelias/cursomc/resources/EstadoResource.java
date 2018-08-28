package com.cldelias.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cldelias.cursomc.domain.Cidade;
import com.cldelias.cursomc.domain.Estado;
import com.cldelias.cursomc.dto.CidadeDTO;
import com.cldelias.cursomc.dto.EstadoDTO;
import com.cldelias.cursomc.services.CidadeService;
import com.cldelias.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;

	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = this.service.findAll();
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj.getId(), obj.getNome())).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
			
	@RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
		List<Cidade> list = this.cidadeService.findCidades(estadoId);
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj.getId(), obj.getNome())).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

}
