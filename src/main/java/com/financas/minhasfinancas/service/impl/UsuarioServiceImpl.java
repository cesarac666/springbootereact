package com.financas.minhasfinancas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
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
