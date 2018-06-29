package com.cldelias.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cldelias.cursomc.domain.Categoria;
import com.cldelias.cursomc.repositories.CategoriaRepository;
import com.cldelias.cursomc.services.exceptions.DataIntegrityException;
import com.cldelias.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = this.repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));		
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return this.repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return this.repo.save(obj);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			this.repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao é possivel excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll() {
		return this.repo.findAll();
	}

}
