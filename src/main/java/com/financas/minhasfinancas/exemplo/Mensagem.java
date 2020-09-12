package com.financas.minhasfinancas.exemplo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Mensagem {

	@Value("${application.name}")
	private String appName; 
	
	
}
