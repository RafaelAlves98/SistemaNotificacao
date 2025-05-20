package com.atividade.senai.repository;

import com.atividade.senai.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    boolean existsByEmail(String email);
}