package com.cldelias.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.cldelias.cursomc.security.UserSS;

public class UserService {
	
	public static UserSS authenticate() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
