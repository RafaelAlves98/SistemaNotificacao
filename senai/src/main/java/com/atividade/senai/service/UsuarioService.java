package com.atividade.senai.service;

import com.atividade.senai.dto.UsuarioDTO;
import com.atividade.senai.entity.Usuario;
import com.atividade.senai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsById(dto.getCpf()) || usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF ou email já existe");
        }
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.getCpf());
        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuarioRepository.save(usuario);
    }

    public Usuario obterUsuario(String cpf) {
        return usuarioRepository.findById(cpf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public Usuario atualizarUsuario(String cpf, UsuarioDTO dto) {
        Usuario usuario = obterUsuario(cpf);
        if (!dto.getEmail().equals(usuario.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já existe");
        }
        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(String cpf) {
        if (!usuarioRepository.existsById(cpf)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        usuarioRepository.deleteById(cpf);
    }
}