package com.cldelias.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cldelias.cursomc.domain.ItemPedido;
import com.cldelias.cursomc.domain.PagamentoComBoleto;
import com.cldelias.cursomc.domain.PagamentoComCartao;
import com.cldelias.cursomc.domain.Pedido;
import com.cldelias.cursomc.domain.enums.EstadoPagamento;
import com.cldelias.cursomc.repositories.ItemPedidoRepository;
import com.cldelias.cursomc.repositories.PagamentoRepository;
import com.cldelias.cursomc.repositories.PedidoRepository;
import com.cldelias.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepo;
	
	@Autowired
	private ProdutoService prdService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	
	@Autowired
	private ClienteService clienteService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = this.repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));		
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(this.clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepo.save(obj.getPagamento());
		for (ItemPedido ite : obj.getItens()) {
			ite.setDesconto(0.00);
			ite.setProduto(prdService.find(ite.getProduto().getId()));
			ite.setPreco(ite.getProduto().getPreco());
			ite.setPedido(obj);
		}
		itemPedidoRepo.saveAll(obj.getItens());
		
		return obj;
	}

}
