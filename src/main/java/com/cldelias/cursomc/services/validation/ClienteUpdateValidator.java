package com.cldelias.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.remote.server.HandlerMapper;
import org.springframework.web.servlet.HandlerMapping;

import com.cldelias.cursomc.domain.Cliente;
import com.cldelias.cursomc.domain.enums.TipoCliente;
import com.cldelias.cursomc.dto.ClienteDTO;
import com.cldelias.cursomc.dto.ClienteNewDTO;
import com.cldelias.cursomc.repositories.ClienteRepository;
import com.cldelias.cursomc.resources.exceptions.FieldMessage;
import com.cldelias.cursomc.services.validation.utils.BR;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}
	
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) this.request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		Cliente aux = this.clienteRepo.findByEmail(objDto.getEmail());
		if (aux != null && !uriId.equals(aux.getId())) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}