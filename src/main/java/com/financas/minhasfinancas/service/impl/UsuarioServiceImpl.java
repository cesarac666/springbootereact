package com.financas.minhasfinancas.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financas.minhasfinancas.exception.AutenticacaoException;
import com.financas.minhasfinancas.exception.RegraNegocioException;
import com.financas.minhasfinancas.model.entity.Usuario;
import com.financas.minhasfinancas.model.repository.UsuarioRepository;
import com.financas.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl  implements UsuarioService {

	@Autowired
	private UsuarioRepository repository; 
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository; 
	}
	
	@Override
	public Usuario autenticar(String email, String senha) {
		
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new AutenticacaoException("Usuario não existe"); 
		} else {
			if(!usuario.get().getSenha().equals(senha)) {
				throw new AutenticacaoException("Senha inválida para o email informado."); 
			}
		}
		
		return usuario.get();
	}

	@Override
	@Transactional // SpringTransaction = vai criar uma transacao no nivel de banco e comitar 
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = this.repository.existsByEmail(email);
		
		try {
			if (existe) {
				throw new RegraNegocioException("Email já cadastrado"); 
			}
			
		} catch (Exception e) {
			throw  e; 
		}
		
	}

	
	
}
