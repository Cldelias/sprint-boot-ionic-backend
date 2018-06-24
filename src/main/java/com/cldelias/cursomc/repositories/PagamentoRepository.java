package com.cldelias.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cldelias.cursomc.domain.Pagamento;

@Repository
public interface PagamentoRepository  extends JpaRepository<Pagamento, Integer>{

}
