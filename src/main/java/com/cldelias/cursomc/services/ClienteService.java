package com.cldelias.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cldelias.cursomc.domain.Categoria;
import com.cldelias.cursomc.domain.Cidade;
import com.cldelias.cursomc.domain.Cliente;
import com.cldelias.cursomc.domain.Endereco;
import com.cldelias.cursomc.domain.enums.TipoCliente;
import com.cldelias.cursomc.dto.ClienteDTO;
import com.cldelias.cursomc.dto.ClienteNewDTO;
import com.cldelias.cursomc.repositories.ClienteRepository;
import com.cldelias.cursomc.repositories.EnderecoRepository;
import com.cldelias.cursomc.services.exceptions.DataIntegrityException;
import com.cldelias.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = this.repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));		
	}
	
	public Cliente update(Cliente obj) {
		Cliente objNew = find(obj.getId());
		updateData(objNew, obj);
		return this.repo.save(objNew);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			this.repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao é possivel excluir porque ha entidades relacionadas");
		}
	}

	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = this.repo.save(obj);
		this.enderecoRepo.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public List<Cliente> findAll() {
		return this.repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return this.repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli =  new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfCpnj(), TipoCliente.toEnum(objDto.getTipo()));
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(),
				cli, new Cidade(objDto.getCidadeId(), null, null));
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	
	private void updateData(Cliente objNew, Cliente obj) {
		objNew.setNome(obj.getNome());
		objNew.setEmail(obj.getEmail());
	}

}
