package com.financas.minhasfinancas.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financas.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	
	Optional<Usuario> findByEmail(String email); 
	
	Optional<Usuario> findByEmailAndNome(String em, String nom);
	
	boolean existsByEmail(String email); 
	

}
