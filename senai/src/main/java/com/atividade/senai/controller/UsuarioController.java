package com.atividade.senai.controller;

import com.atividade.senai.dto.UsuarioDTO;
import com.atividade.senai.entity.Usuario;
import com.atividade.senai.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody UsuarioDTO dto) {
        return new ResponseEntity<>(usuarioService.criarUsuario(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Usuario> obter(@PathVariable String cpf) {
        return ResponseEntity.ok(usuarioService.obterUsuario(cpf));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Usuario> atualizar(@PathVariable String cpf, @Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(cpf, dto));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {
        usuarioService.deletarUsuario(cpf);
        return ResponseEntity.ok().build();
    }
}