package com.financas.minhasfinancas;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
// import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.financas.minhasfinancas.model.entity.Usuario;
import com.financas.minhasfinancas.model.repository.UsuarioRepository;

// @SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test") // aapplication-test.properties  
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repo; 
	
	@Autowired
	TestEntityManager em; 
	
	// se incluirmos o acesso a base, estaremos fazendo um teste de integracao e não unitário 
	@Test
	public void verificaExisteMail() {
		// cenario - arrange 
		Usuario usu = criaUsuario();
		// repo.save(usu);
		em.persist(usu);
		
		// acao - action 
		boolean  result = repo.existsByEmail("acsa@gmail.com");
		
		// verificacao - assert  
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void retornaFalseParaUmEmailNaoExistente() { 
		// acao - action 
		boolean  result = repo.existsByEmail("acsa@gmail.com");
		
		// verificacao - assert  
		Assertions.assertThat(result).isFalse();
	}
	
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() { 
		// cenario - arrange 
		Usuario usu = criaUsuario();
		
		// acao - action 
		Usuario usuarioSalvo = repo.save(usu); 
		
		// verificacao - assert  
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	@Test
	public void buscarUsuarioExistentePorEmail() { 
		// cenario - arrange 
		Usuario usu = criaUsuario();
		em.persist(usu);
		
		// acao - action 
		Optional<Usuario> usuarioSalvo = repo.findByEmail(usu.getEmail());
		
		// verificacao - assert  
		// Assertions.assertThat(usuarioSalvo.get().getId()).isNotNull(); 
		// OU ASSIM:  
		Assertions.assertThat(usuarioSalvo.isPresent()).isTrue();
		
	}
	
	private static Usuario criaUsuario() {
		return Usuario.builder().nome("acsa").email("acsa@gmail.com").senha("senha").build();
	}

	

}
