package com.financas.minhasfinancas;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.financas.minhasfinancas.exception.AutenticacaoException;
import com.financas.minhasfinancas.exception.RegraNegocioException;
import com.financas.minhasfinancas.model.entity.Usuario;
// import com.financas.minhasfinancas.model.entity.Usuario;
import com.financas.minhasfinancas.model.repository.UsuarioRepository;
import com.financas.minhasfinancas.service.UsuarioService;
import com.financas.minhasfinancas.service.impl.UsuarioServiceImpl;

// @SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test") 
// @DataJpaTest
public class UsuarioServiceTest {
 
	// @Autowired
	// UsuarioService usuService; 
	@SpyBean
	UsuarioServiceImpl usuService; 
	
	
	// @Autowired
	@MockBean
	UsuarioRepository usuRepo; 
	
	@Before
	public void init() {
		// Assim :  usuRepo = Mockito.mock(UsuarioRepository.class); OU usando anotacao @MockBean
		// Assim:   usuService = Mockito.spy(UsuarioService.class); OU usando a anotacao @SpyBean 
		
		// e quando usamos @SpyBean, temos que retirar esta implementação (será)
		// usuService = new UsuarioServiceImpl(usuRepo);
		// **** Alem disso temos que anotar a impl e nao a interface !!!! 
	}
	
	@Test(expected=Test.None.class) // eu espero que o método action não lance nenhuma exceção
	public void testarEmailValido() {
		// A-rrange
		// usuRepo.deleteAll();
		Mockito.when(usuRepo.existsByEmail(Mockito.anyString())).thenReturn(Boolean.FALSE);
		
		// A-ction 
		usuService.validarEmail("acsa@google.com");
		
		// A-ssert já é o expected... 
	}
	
	
	@Test(expected = RegraNegocioException.class)
	public void testarEmailJaCadastrado() {
		// A-rrange
		// Usuario usu = Usuario.builder().nome("acsa").email("acsa@gmail.com").senha("senha").build();
		// usuRepo.save(usu);
		// Estamos isolando o teste de Service, eliminando o Repository
		Mockito.when(usuRepo.existsByEmail(Mockito.anyString())).thenReturn(Boolean.TRUE);
		
		// A-ction 
		usuService.validarEmail("acsa@gmail.com");
		
		// A-ssert já é o expected... 
	}
	
	@Test(expected = Test.None.class)
	public void testaUsuarioAutenticadoComSucesso() {
		// cenario 
		String email ="mail@teste.com"; 
		String senha = "senha"; 
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
		Mockito.when(usuRepo.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		// acao
		Usuario  result = usuService.autenticar(email, senha);
		
		// verificacao 
		Assertions.assertThat(result).isNotNull();
		
	}
	
	@Test(expected = AutenticacaoException.class)
	public void testaUsuarioAutenticadoComINSucesso() {
		// cenario 
		String email ="mail22@teste.com"; 
		String senha = "senha"; 
		
		// acao
		Usuario  result = usuService.autenticar(email, senha);
		
		// verificacao 
		Assertions.assertThat(result).isNotNull();
		
	}

	// Modo 1: @Test(expected = AutenticacaoException.class)
	// Modo 2: 
	@Test
	public void testaUsuarioAutenticadoComSenhaInvalida() {
		// cenario 
		String email ="mail@teste.com"; 
		String senha = "senha"; 
		String msgSenhaInvaliva = "Senha inválida para o email informado.";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
		Mockito.when(usuRepo.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		// acao
		//Assim, MODO 1
		//Usuario  result = usuService.autenticar(email, "123");
		
		// verificacao 
		// Assim, MODO 1 
		// Assertions.assertThat(result).isNotNull();
		
		// OU assim, MODO 2
		Throwable excep = Assertions.catchThrowable(() ->  usuService.autenticar(email, "123"));
		Assertions.assertThat(excep)
			.isInstanceOf(AutenticacaoException.class)
			.hasMessage(msgSenhaInvaliva);
		
	}
	
	@Test(expected = Test.None.class)
	public void validaSalvarUsuarioComSucesso() {
		// Arrange 
		// Mocamos a classe e depois chamamos o método: 
		Mockito.doNothing().when(usuService).validarEmail(Mockito.anyString());
		Usuario usu = Usuario.builder()
				.id(1L)
				.email("emial@email.com")
				.nome("nome")
				.senha("senha")
				.build();
		Mockito.when(usuRepo.save(Mockito.any(Usuario.class))).thenReturn(usu);
		
		// Action 
		// Como eu simulei o método save entao independente do que eu passar, vai reornar usu
		Usuario usuSalvo = usuService.salvarUsuario(new Usuario());
		
		// Assert 
		Assertions.assertThat(usuSalvo).isNotNull();
		Assertions.assertThat(usuSalvo.getId()).isEqualTo(usu.getId());
		Assertions.assertThat(usuSalvo.getEmail()).isEqualTo(usu.getEmail());
		// ... 
				
	}
	
	
	@Test(expected = RegraNegocioException.class)
	public void validaSalvarUsuarioComEmailJaExistente() {
		// Arrange 
		Usuario usu = Usuario.builder().email("emial@email.com").build();
		//mockamos que ele lance a excecao quando eu validar este email 
		Mockito.doThrow(RegraNegocioException.class).when(usuService).validarEmail(usu.getEmail());
		
		
		// Action 
		usuService.salvarUsuario(usu);
		
		// Assert 
		// verifica: "espero que não tenha chamada ao metodo save da classe usuRepo. 
		// se não houve chamada, é porque a exceção que forçamos foi capturada e impediu o save 
		Mockito.verify(usuRepo,  Mockito.never()).save(usu);  
				
	}

	
}
