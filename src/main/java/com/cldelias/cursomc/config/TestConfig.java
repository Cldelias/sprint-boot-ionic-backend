package com.cldelias.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cldelias.cursomc.services.DBService;
import com.cldelias.cursomc.services.EmailService;
import com.cldelias.cursomc.services.MockEmailService;

@Configuration
@Profile("dev")
public class TestConfig {

	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		this.dbService.instatiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
