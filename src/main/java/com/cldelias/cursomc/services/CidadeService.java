package com.cldelias.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cldelias.cursomc.domain.Cidade;
import com.cldelias.cursomc.domain.Estado;
import com.cldelias.cursomc.repositories.CidadeRepository;
import com.cldelias.cursomc.repositories.EstadoRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> findCidades(Integer estadoId) {
		return this.repo.findCidades(estadoId);
	}
	

}
