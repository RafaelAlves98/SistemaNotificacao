package com.atividade.senai.controller;

import com.atividade.senai.dto.NotificacaoDTO;
import com.atividade.senai.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {
    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping("/{usuarioCpf}")
    public ResponseEntity<List<NotificacaoDTO>> listar(@PathVariable String usuarioCpf,
                                                       @RequestParam(required = false) String status) {
        return ResponseEntity.ok(notificacaoService.listarNotificacoes(usuarioCpf, status));
    }

    @PutMapping("/{usuarioCpf}/{id}")
    public ResponseEntity<Void> atualizarStatus(@PathVariable String usuarioCpf,
                                                @PathVariable Integer id,
                                                @RequestBody NotificacaoDTO dto) {
        notificacaoService.atualizarStatusNotificacao(id, usuarioCpf, dto.getStatus());
        return ResponseEntity.ok().build();
    }
}