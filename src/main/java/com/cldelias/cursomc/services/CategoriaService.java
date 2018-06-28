package com.cldelias.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cldelias.cursomc.domain.Categoria;
import com.cldelias.cursomc.repositories.CategoriaRepository;
import com.cldelias.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = this.repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));		
	}

	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return this.repo.save(obj);
	}
}
