package com.financas.minhasfinancas;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.financas.minhasfinancas.model.entity.Usuario;
import com.financas.minhasfinancas.model.repository.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test") // aapplication-test.properties  
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repo; 
	
	// se incluirmos o acesso a base, estaremos fazendo um teste de integracao e não unitário 
	@Test
	public void verificaExisteMail() {
		// cenario - arrange 
		Usuario usu = Usuario.builder().nome("acsa").email("acsa@gmail.com").senha("senha").build();
		repo.save(usu);
		
		// acao - action 
		boolean  result = repo.existsByEmail("acsa@gmail.com");
		
		// verificacao - assert  
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void retornaFalseParaUmEmailNaoExistente() { 
		
		// cenario - arrange 
		repo.deleteAll();
		
		// acao - action 
		boolean  result = repo.existsByEmail("acsa@gmail.com");
		
		// verificacao - assert  
		Assertions.assertThat(result).isFalse();
		
		
	}
	

}
